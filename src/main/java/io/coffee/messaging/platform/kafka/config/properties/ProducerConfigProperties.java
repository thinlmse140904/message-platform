package io.coffee.messaging.platform.kafka.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "kafka.producer")
public class ProducerConfigProperties {
    private String keySerializerClass;
    private String valueSerializerClass;
    private String clientId;
    private String acks;
    private String enableIdempotence;
    private String maxRetries;
}
