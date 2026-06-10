package io.coffee.ordering.messaging.platform.kafka.consumer;

public interface KafkaMessageConsumer<T> {
    void handle(String key, T message);
}
