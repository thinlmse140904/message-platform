package io.coffee.ordering.messaging.platform.kafka.producer;

import lombok.NonNull;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public interface KafkaMessagePublisher {

    CompletableFuture<SendResult<@NonNull String, @NonNull Object>> publish(String topic, String key, Object message);

    default void publish(String topic, String key, Object message, BiConsumer<SendResult<@NonNull String, @NonNull Object>, Throwable> callback) {
        this.publish(topic, key, message).whenComplete(callback);
    }
}
