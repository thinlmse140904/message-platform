package io.coffee.ordering.messaging.platform.kafka.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "kafka.consumer")
public class ConsumerConfigProperties {
    private String keyDeserializerClass;
    private String trustedPackages;
}
