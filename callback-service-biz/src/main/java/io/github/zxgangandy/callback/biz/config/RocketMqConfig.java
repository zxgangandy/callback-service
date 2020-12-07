package io.github.zxgangandy.callback.biz.config;

import io.andy.rocketmq.wrapper.core.RMWrapper;
import io.andy.rocketmq.wrapper.core.producer.RMProducer;
import io.github.zxgangandy.callback.biz.mq.AddTaskTxListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RocketMqConfig {

    @Autowired
    private RocketMQProperties rocketMQProperties;

    @Resource(name = "addTaskTxListener")
    private AddTaskTxListener  addTaskTxListener;

    @Bean
    public RMProducer defaultProducer() {
        RMProducer producer = RMWrapper.with(RMProducer.class)
                .producerGroup(rocketMQProperties.getProducer().getProducerGroup())
                .nameSrvAddr(rocketMQProperties.getNameSrvAddr())
                .retryTimes(3)
                .txListener(addTaskTxListener)
                .start();

        return producer;
    }


}
