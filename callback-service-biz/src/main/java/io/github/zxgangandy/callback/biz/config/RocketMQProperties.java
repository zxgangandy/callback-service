package io.github.zxgangandy.callback.biz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMQProperties {
    private String nameServer;


    private RocketMQProperties.Producer producer = new RocketMQProperties.Producer();

    private RocketMQProperties.Consumer consumer = new RocketMQProperties.Consumer();

    @Data
    public static final class Producer {
        private String producerGroup;
    }

    @Data
    public static final class Consumer {
    }
}
