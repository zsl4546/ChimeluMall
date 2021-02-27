package com.zsl.order.listener;

import lombok.SneakyThrows;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/24
 * Time: 12:53
 * Description: 项目启动开始就将 MQ 生产者和消费者启动起来
 */
@Component
public class MQStarterListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private TransactionMQProducer transactionMQProducer;

    @Autowired
    private DefaultMQPushConsumer defaultMQPushConsumer;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        transactionMQProducer.start();
        System.out.println("Producer start!");
        defaultMQPushConsumer.start();
        System.out.println("Consumer start!");
    }
}
