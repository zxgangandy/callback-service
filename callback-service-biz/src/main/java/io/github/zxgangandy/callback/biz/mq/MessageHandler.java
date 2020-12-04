package io.github.zxgangandy.callback.biz.mq;

import io.andy.rocketmq.wrapper.core.RMWrapper;
import io.andy.rocketmq.wrapper.core.consumer.RMConsumer;
import io.andy.rocketmq.wrapper.core.consumer.processor.ConcurrentlyProcessor;
import io.github.zxgangandy.callback.biz.config.RocketMQProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MessageHandler {

    @Autowired
    private ConcurrentlyProcessor concurrentlyProcessor;

    @Autowired
    private RocketMQProperties    rocketMQProperties;

    @PostConstruct
    private void initialize() {
        register();
    }

    private void register() {
        RMWrapper.with(RMConsumer.class)
                .consumerGroup(rocketMQProperties.getConsumer().getConsumerGroup())
                .nameSrvAddr(rocketMQProperties.getNameServer())
                .subscribe("test")
                .concurrentlyProcessor(concurrentlyProcessor)
                .start();
    }
}
