package io.github.zxgangandy.callback.biz.config;

import io.andy.rocketmq.wrapper.core.RMWrapper;
import io.andy.rocketmq.wrapper.core.producer.RMProducer;
import io.github.zxgangandy.callback.biz.mq.AddTaskTxListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RocketMqConfig {

    @Autowired
    private RocketMQProperties rocketMQProperties;

    @Bean
    public RMProducer defaultProducer() {
        RMProducer producer = RMWrapper.with(RMProducer.class)
                .producerGroup(rocketMQProperties.getProducer().getProducerGroup())
                .nameSrvAddr(rocketMQProperties.getNameServer())
                .retryTimes(3)
                .txListener(new AddTaskTxListener())
                .start();

        return producer;
    }


}
