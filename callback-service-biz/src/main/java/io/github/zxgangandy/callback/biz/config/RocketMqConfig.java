package io.github.zxgangandy.callback.biz.config;

import io.andy.rocketmq.wrapper.core.RMWrapper;
import io.andy.rocketmq.wrapper.core.producer.RMProducer;
import io.github.zxgangandy.callback.biz.mq.AddTaskTxListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RocketMqConfig {

    @Bean
    public RMProducer defaultProducer() {
        RMProducer producer = RMWrapper.with(RMProducer.class)
                .producerGroup("producer-test")
                .nameSrvAddr("127.0.0.1:9876")
                .retryTimes(3)
                .txListener(new AddTaskTxListener())
                .start();

        return producer;
    }


}
