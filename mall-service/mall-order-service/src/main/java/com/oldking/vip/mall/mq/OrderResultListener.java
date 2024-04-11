package com.oldking.vip.mall.mq;


import com.alibaba.fastjson.JSON;
import com.oldking.vip.mall.order.service.OrderService;
import com.oldking.vip.mall.pay.model.PayLog;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
@RocketMQMessageListener(topic = "log",consumerGroup = "resultgroup")
public class OrderResultListener implements RocketMQListener, RocketMQPushConsumerLifecycleListener {
    @Autowired
    private OrderService orderService;

    @Override
    public void onMessage(Object o) {

    }

    /**
     * 消息监听操作流程
     * @param defaultMQPushConsumer
     */
    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt msg : list) {
                    try {
                        String string = new String(msg.getBody(), "UTF-8");
                        PayLog payLog = JSON.parseObject(string,PayLog.class);
                        if (payLog.getStatus().intValue() ==2) {
//                            支付成功
                            orderService.updateAfterPayStatus(payLog.getPayId());
                        }else {
//                            支付失败，等待？
//                            orderService.updateAfterPayStatus(payLog.getPayId());

                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
    }
}
