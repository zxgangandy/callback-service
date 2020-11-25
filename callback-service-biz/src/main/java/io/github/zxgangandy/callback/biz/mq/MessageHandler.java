package io.github.zxgangandy.callback.biz.mq;

import io.andy.rocketmq.wrapper.core.RMWrapper;
import io.andy.rocketmq.wrapper.core.consumer.RMConsumer;
import io.andy.rocketmq.wrapper.core.consumer.processor.ConcurrentlyProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MessageHandler {

    @Autowired
    private ConcurrentlyProcessor concurrentlyProcessor;

    @PostConstruct
    private void initialize() {
        register();
    }

    private void register() {
        RMWrapper.with(RMConsumer.class)
                .consumerGroup("consumer-test")
                .nameSrvAddr("127.0.0.1:9876")
                .subscribe("test")
                .concurrentlyProcessor(concurrentlyProcessor)
                .start();
    }
}
