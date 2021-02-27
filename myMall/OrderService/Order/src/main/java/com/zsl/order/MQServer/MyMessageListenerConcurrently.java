package com.zsl.order.MQServer;

import com.alibaba.fastjson.JSON;
import com.zsl.order.entitys.MyMessage;
import com.zsl.order.service.MainService;
import lombok.SneakyThrows;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/24
 * Time: 13:41
 * Description: No Description
 */
public class MyMessageListenerConcurrently implements MessageListenerConcurrently {

    @Autowired
    private MainService mainService;

    @SneakyThrows
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt item : msgs) {
            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), new String(item.getBody(), RemotingHelper.DEFAULT_CHARSET));
            System.out.println(JSON.parseObject(new String(item.getBody(), RemotingHelper.DEFAULT_CHARSET), MyMessage.class));
            // 执行下单操作
            boolean message = mainService.realOrder(JSON.parseObject(new String(item.getBody(), RemotingHelper.DEFAULT_CHARSET), MyMessage.class));
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
