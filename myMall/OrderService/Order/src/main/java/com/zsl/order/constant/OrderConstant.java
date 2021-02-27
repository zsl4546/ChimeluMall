package com.zsl.order.constant;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/24
 * Time: 9:17
 * Description:
 */
public class OrderConstant {
    /**
     * MQ 发送的消息在这个 topic 下
     */
    public static String MQ_TOPIC = "mall";
    /**
     * 在 redis 存储商品的库存信息 格式一般是 （stock:商品id）
     */
    public static String STOCK_KEY_PREFIX = "stock:";

    /**
     * 库存信息过期时间，20 分钟更新一次库存
     */
    public static Integer STOCK_EXPIRE_TIME = 60 * 20;

    /**
     * 如果用户成功抢到库存，就在 redis 里面记录，然后交由 RocketMQ 处理下单服务
     */
    public static String SUCCESS_KEY_PREFIX = "ORDER:";

    /**
     * 设置过期时间，进行幂等性检查
     */
    public static Integer SUCCESS_EXPIRE_TIME = 60 * 30;

    /**
     * zookeeper 分布式锁的根目录
     */
    public static String LOCK_KEY_PREFIX = "/mallStock";
}
