package com.oldking.vip.mall.pay.mq;

import com.alibaba.fastjson.JSON;
import com.oldking.vip.mall.pay.model.PayLog;
import com.oldking.vip.mall.pay.service.PayLogService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@RocketMQTransactionListener(txProducerGroup = "rocket")
public class TransactionListener implements RocketMQLocalTransactionListener {

    @Autowired
    private PayLogService payLogService;

    /**
     * 当向RocketMQ发送Half消息成功后调用该方法
     * @param message 发送的消息
     * @param o 额外参数
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
//            ===========本地事务控制===========
//            消息
            String result = new String((byte[]) message.getPayload(), "UTF-8");
            PayLog payLog = JSON.parseObject(result, PayLog.class);
            payLogService.savePayLog(payLog);
//            ===========本地事务控制==========
        } catch (Exception e) {
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * 超时回查，
     * @param message
     * @return
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        return RocketMQLocalTransactionState.COMMIT;
    }
}
