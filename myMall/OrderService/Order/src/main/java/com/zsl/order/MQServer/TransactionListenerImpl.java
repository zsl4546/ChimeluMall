package com.zsl.order.MQServer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zsl.order.entitys.MyMessage;
import com.zsl.order.service.OtherService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;

@Slf4j
public class TransactionListenerImpl implements TransactionListener {

    @Autowired
    private OtherService otherService;

    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // 幂等性检查
        // 因为成功下单的 redis 记录只有 30 分钟有效时间，还需要再这里查询数据库检查是否已经下单
        // 这里可以优化，只在开放购买开始后的 30 分钟才执行数据库的检查操作
        MyMessage myMessage = null;
        try {
            myMessage = JSON.parseObject(new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET), MyMessage.class);
        } catch (UnsupportedEncodingException e) {
            log.error("消息转换发生错误");
            e.printStackTrace();
        }
        if (otherService.queryOrder(myMessage.getUserId(), myMessage.getCommodityId()) != null) {
            // 如果已经下单了就不再重复下单
            log.info("MQ消息回滚");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
        log.info("消息提交");
        return LocalTransactionState.COMMIT_MESSAGE;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        log.info("check");
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
