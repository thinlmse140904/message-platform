package io.coffee.ordering.messaging.platform.kafka.bean;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KafkaBeanName {
    // KAFKA TEMPLATE
    public static final String KAFKA_TEMPLATE = "kafkaTemplate";

    // PRODUCER
    public static final String KAFKA_PRODUCER_FACTORY = "kafkaProducerFactory";

    // CONSUMER
    public static final String JSON_CONSUMER_FACTORY = "jsonConsumerFactory";
    public static final String JSON_KAFKA_LISTENER_CONTAINER_FACTORY = "jsonKafkaListenerContainerFactory";
    public static final String STRING_CONSUMER_FACTORY = "stringConsumerFactory";
    public static final String STRING_KAFKA_LISTENER_CONTAINER_FACTORY = "stringKafkaListenerContainerFactory";
}
