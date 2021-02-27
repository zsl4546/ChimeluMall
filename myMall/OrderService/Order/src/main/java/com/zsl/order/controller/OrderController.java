package com.zsl.order.controller;

import com.alibaba.fastjson.JSON;
import com.zsl.order.constant.OrderConstant;
import com.zsl.order.entitys.MyMessage;
import com.zsl.order.entitys.OrderLog;
import com.zsl.order.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/24
 * Time: 10:31
 * Description: 下单业务
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private MainService mainService;

    @Autowired
    private TransactionMQProducer transactionMQProducer;

    @GetMapping("/buy")
    public void createOrder(OrderLog orderLog) throws Exception {
        boolean flag =  mainService.getCommodity(orderLog.getCommodityId(), orderLog.getCount(), orderLog.getUserId());
        if (!flag) {
            log.info("没有抢到，返回");
            return;
        }
        MyMessage myMessage = new MyMessage(orderLog.getUserId(), orderLog.getCommodityId(), orderLog.getCount(), orderLog.getAmount());
        Message message = new Message(OrderConstant.MQ_TOPIC, JSON.toJSONString(myMessage).getBytes(RemotingHelper.DEFAULT_CHARSET));
        log.info("发送消息：{}",JSON.toJSONString(myMessage));
        transactionMQProducer.send(message);
    }
}
