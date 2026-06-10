package io.coffee.messaging.platform.kafka.config;

import io.coffee.messaging.platform.kafka.bean.KafkaBeanName;
import io.coffee.messaging.platform.kafka.config.properties.KafkaConfigProperties;
import io.coffee.messaging.platform.kafka.config.properties.ProducerConfigProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

/**
 * Provides shared Kafka producer beans for services using this module.
 */
@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnClass(KafkaTemplate.class)
@EnableConfigurationProperties({KafkaConfigProperties.class, ProducerConfigProperties.class})
public class ProducerAutoConfiguration {
    private final KafkaConfigProperties kafkaProperties;
    private final ProducerConfigProperties producerProperties;

    /**
     * Custom producer factory for platform-managed publishing.
     * KafkaProducerFactory beans for dedicated Kafka clients.
     */
    @Bean(name = KafkaBeanName.KAFKA_PRODUCER_FACTORY)
    @ConditionalOnMissingBean(name = KafkaBeanName.KAFKA_PRODUCER_FACTORY)
    public ProducerFactory<@NonNull Object, Object> kafkaProducerFactory() {
        Map<String, Object> props = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getServer(),
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerProperties.getKeySerializerClass(),
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerProperties.getValueSerializerClass(),
                ProducerConfig.CLIENT_ID_CONFIG, producerProperties.getClientId(),
                ProducerConfig.ACKS_CONFIG, producerProperties.getAcks(),
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, producerProperties.getEnableIdempotence(),
                ProducerConfig.RETRIES_CONFIG, producerProperties.getMaxRetries()
        );
        return new DefaultKafkaProducerFactory<>(props);
    }

    /**
     * Expose custom KafkaTemplate backed by a producer factory with custom properties.
     */
    @Bean
    @ConditionalOnMissingBean(KafkaTemplate.class)
    public KafkaTemplate<@NonNull Object, @NonNull Object> kafkaTemplate(
            @Qualifier(KafkaBeanName.KAFKA_PRODUCER_FACTORY)
            ProducerFactory<@NonNull Object, Object> kafkaProducerFactory) {
        return new KafkaTemplate<>(kafkaProducerFactory);
    }
}
