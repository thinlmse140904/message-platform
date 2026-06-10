package io.coffee.ordering.messaging.platform.kafka.config;

import io.coffee.ordering.messaging.platform.kafka.bean.KafkaBeanName;
import io.coffee.ordering.messaging.platform.kafka.config.properties.ConsumerConfigProperties;
import io.coffee.ordering.messaging.platform.kafka.config.properties.KafkaConfigProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.Map;

/**
 * Registers Kafka consumer components used by the messaging platform.
 * <p>Provides separate listener factories for JSON and plain String messages.
 */
@Configuration
@RequiredArgsConstructor
@ConditionalOnClass(ConcurrentKafkaListenerContainerFactory.class)
@EnableConfigurationProperties({KafkaConfigProperties.class, ConsumerConfigProperties.class})
public class ConsumerAutoConfiguration {
    private final KafkaConfigProperties kafkaProperties;
    private final ConsumerConfigProperties consumerProperties;


    /**
     * Custom consumer factory for JSON message payloads.
     */
    @Bean(name = KafkaBeanName.JSON_CONSUMER_FACTORY)
    @ConditionalOnMissingBean(name = KafkaBeanName.JSON_CONSUMER_FACTORY)
    public ConsumerFactory<@NonNull String, Object> jsonConsumerFactory() {
        Map<String, Object> props = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getServer(),
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class,
                ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class,
                ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JacksonJsonDeserializer.class,
                JacksonJsonDeserializer.TRUSTED_PACKAGES, consumerProperties.getTrustedPackages()
        );
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * Listener container factory for JSON Kafka listeners.
     */
    @Bean(name = KafkaBeanName.JSON_KAFKA_LISTENER_CONTAINER_FACTORY)
    @ConditionalOnMissingBean(name = KafkaBeanName.JSON_KAFKA_LISTENER_CONTAINER_FACTORY)
    public ConcurrentKafkaListenerContainerFactory<@NonNull String, @NonNull Object> jsonKafkaListenerContainerFactory(
            @Qualifier(KafkaBeanName.JSON_CONSUMER_FACTORY)
            ConsumerFactory<@NonNull String, Object> jsonConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<@NonNull String, @NonNull Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(jsonConsumerFactory);
        return factory;
    }

    /**
     * Custom consumer factory for string message payloads.
     */
    @Bean(name = KafkaBeanName.STRING_CONSUMER_FACTORY)
    @ConditionalOnMissingBean(name = KafkaBeanName.STRING_CONSUMER_FACTORY)
    public ConsumerFactory<@NonNull String, String> stringConsumerFactory() {
        Map<String, Object> props = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getServer(),
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class,
                ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, consumerProperties.getKeyDeserializerClass(),
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class,
                ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, StringDeserializer.class
        );
        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * Listener container factory for string Kafka listeners.
     */
    @Bean
    @ConditionalOnMissingBean(name = KafkaBeanName.STRING_KAFKA_LISTENER_CONTAINER_FACTORY)
    public ConcurrentKafkaListenerContainerFactory<@NonNull String, @NonNull String> stringKafkaListenerContainerFactory(
            @Qualifier(KafkaBeanName.STRING_CONSUMER_FACTORY)
            ConsumerFactory<@NonNull String, String> stringConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<@NonNull String, @NonNull String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(stringConsumerFactory);
        return factory;
    }
}
