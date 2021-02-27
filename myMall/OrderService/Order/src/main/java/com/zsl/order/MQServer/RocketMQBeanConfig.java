package com.zsl.order.MQServer;
import com.zsl.order.constant.OrderConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;

import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/24
 * Time: 13:22
 * Description: 配置 RocketMQ 生产者和消费者，注入 IOC 容器
 */
@Configuration
public class RocketMQBeanConfig {

    @Bean
    public TransactionMQProducer initProducer() {
        TransactionListener transactionListener = new TransactionListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer("order_producer_group");
        ExecutorService executorService = new ThreadPoolExecutor(4, 8, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("client-transaction-msg-check-thread");
                return thread;
            }
        });

        producer.setExecutorService(executorService);
        producer.setTransactionListener(transactionListener);
        producer.setNamesrvAddr("127.0.0.1:9876");
        return producer;
    }

    @Bean
    public DefaultMQPushConsumer initConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order_consumer_group");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe(OrderConstant.MQ_TOPIC, "*");
        MyMessageListenerConcurrently messageListenerConcurrently = new MyMessageListenerConcurrently();
        consumer.registerMessageListener(messageListenerConcurrently);
        return consumer;
    }

}
