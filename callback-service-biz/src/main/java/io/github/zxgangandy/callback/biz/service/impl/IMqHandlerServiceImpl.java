package io.github.zxgangandy.callback.biz.service.impl;

import io.andy.rocketmq.wrapper.core.RMWrapper;
import io.andy.rocketmq.wrapper.core.consumer.RMConsumer;
import io.andy.rocketmq.wrapper.core.consumer.processor.ConcurrentlyProcessor;
import io.github.zxgangandy.callback.biz.config.RocketMQProperties;
import io.github.zxgangandy.callback.biz.service.IMqHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class IMqHandlerServiceImpl implements IMqHandlerService {

    private static final String   CONSUMER_GROUP_PREFIX = "ConsumerGroup-";

    @Autowired
    private RocketMQProperties    rocketMQProperties;
    @Autowired
    private ConcurrentlyProcessor concurrentlyProcessor;

    private Map<String, String>   topicMap = new ConcurrentHashMap<>();

    @Override
    public void subscribe(String topic) {
        RMWrapper.with(RMConsumer.class)
                .consumerGroup(CONSUMER_GROUP_PREFIX + topic)
                .nameSrvAddr(rocketMQProperties.getNameServer())
                .subscribe(topic)
                .concurrentlyProcessor(concurrentlyProcessor)
                .start();

        topicMap.putIfAbsent(topic, topic);
    }

    @Override
    public boolean exists(String topic) {
        return topicMap.containsKey(topic);
    }

}
