package com.zsl.order.service;

import com.zsl.RequestAndResponse.DecrCommodityRequest;
import com.zsl.RequestAndResponse.FindCommodityRequest;
import com.zsl.RequestAndResponse.FindCommodityResponse;
import com.zsl.SelectService;
import com.zsl.UpdateService;
import com.zsl.account.RequestAndResponse.DecrMoneyRequest;
import com.zsl.account.UserAccountService;
import com.zsl.account.entitys.UserAccount;
import com.zsl.entitys.Commodity;
import com.zsl.order.entitys.MyMessage;
import com.zsl.order.entitys.OrderLog;
import com.zsl.order.utils.HighPerformanceZkLock;
import com.zsl.utils.JedisUtil;
import com.zsl.utils.UUIDUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import com.zsl.order.constant.*;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/24
 * Time: 10:32
 * Description: No Description
 */
@Service
@Slf4j
public class MainService {
    @Reference
    private SelectService select;

    @Reference
    private UserAccountService accountService;

    @Reference
    private UpdateService updateService;

    @Autowired
    private OtherService otherService;
    /**
     * 抢购商品方法
     * @param commodityId 商品 id
     * @param commodityCount 需要购买的商品数量
     * @param account 购买账户
     * @return 如果抢购成功返回true，抢购失败返回false
     * @throws Exception
     */
    public boolean getCommodity(Integer commodityId, Integer commodityCount, String account) throws Exception {
        Jedis jedis = JedisUtil.getJedis();
        int stock; // 商品现有库存
        if (!jedis.exists(OrderConstant.STOCK_KEY_PREFIX+commodityId)) {
            // 如果 redis 中没有商品库存信息，就在数据库查询，然后存储到 redis
            FindCommodityRequest request = new FindCommodityRequest();
            request.setId(commodityId);
            FindCommodityResponse response = select.findCommodityById(request);
            Commodity commodity = response.getCommodity();
            if (commodity == null) {
                log.error("商品 id =  {} 不存在", commodityId);
                throw new Exception("商品不存在");
            }
            stock = commodity.getStock();
            jedis.set(OrderConstant.STOCK_KEY_PREFIX+commodityId, String.valueOf(stock));
            jedis.expire(OrderConstant.STOCK_KEY_PREFIX+commodityId, OrderConstant.STOCK_EXPIRE_TIME);
        } else {
            stock = Integer.parseInt(jedis.get(OrderConstant.STOCK_KEY_PREFIX+commodityId));
        }
        // 判断是否已经下过单了,幂等性检查
        if (jedis.exists(OrderConstant.SUCCESS_KEY_PREFIX+account+":"+commodityId)) {
            return false;
        }
        if(stock >= commodityCount) {
            // 库存足够，获取 zookeeper 分布式锁，redis 减库存
            HighPerformanceZkLock lock = new HighPerformanceZkLock(commodityId);
            if (lock.getLock()) {
                // 获取到锁，然后进行库存操作,还要再判断一次库存是否足够，防止因为获取锁阻塞时间过长，其他先获取到锁的线程减库存造成的库存不够
                if(Integer.parseInt(jedis.get(OrderConstant.STOCK_KEY_PREFIX+commodityId)) >= commodityCount) {
                    jedis.decrBy(OrderConstant.STOCK_KEY_PREFIX+commodityId, commodityCount);
                    jedis.set(OrderConstant.SUCCESS_KEY_PREFIX+account+":"+commodityId, String.valueOf(commodityCount));
                    jedis.expire(OrderConstant.SUCCESS_KEY_PREFIX+account+":"+commodityId, OrderConstant.SUCCESS_EXPIRE_TIME);
                } else {
                    // 进入锁过后发现库存不够
                    return false;
                }
                lock.releaseLock(); // 释放锁
            }
        } else {
            // 库存不够
            return false;
        }
        return true;
    }

    /**
     * 执行真正的下单操作，扣钱，扣库存
     * @param myMessage
     * @return
     */
    @GlobalTransactional(timeoutMills = 300000, name = "seata-demo-business")
    public boolean realOrder(MyMessage myMessage) throws Exception {
        System.out.println("start realOrder");
        // 扣库存
        DecrCommodityRequest commodityRequest = new DecrCommodityRequest();
        commodityRequest.setId(myMessage.getCommodityId());
        commodityRequest.setStock(myMessage.getCount());
        updateService.decrCommodity(commodityRequest);
        // 扣钱
        DecrMoneyRequest moneyRequest = new DecrMoneyRequest();
        UserAccount userAccount = new UserAccount();
        userAccount.setAccount(myMessage.getUserId());
        userAccount.setMoney(myMessage.getPrice());
        moneyRequest.setUserAccount(userAccount);
        accountService.decrMoney(moneyRequest);
        // 生成订单
        OrderLog orderLog = new OrderLog();
        orderLog.setUserId(myMessage.getUserId());
        orderLog.setCommodityId(myMessage.getCommodityId());
        orderLog.setCount(myMessage.getCount());
        orderLog.setAmount(myMessage.getPrice());
        orderLog.setOrderNo(UUIDUtil.getUUID());
        otherService.createOrder(orderLog);
        return true;
    }
}
