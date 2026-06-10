package io.coffee.ordering.messaging.platform.kafka.producer.impl;

import io.coffee.ordering.messaging.platform.kafka.exception.ErrorCode;
import io.coffee.ordering.messaging.platform.kafka.exception.KafkaPublisherException;
import io.coffee.ordering.messaging.platform.kafka.producer.KafkaMessagePublisher;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
public class KafkaMessagePublisherImpl implements KafkaMessagePublisher {

    private final KafkaTemplate<@NonNull String, @NonNull Object> kafkaTemplate;

    @Override
    public CompletableFuture<SendResult<@NonNull String, @NonNull Object>> publish(String topic, String key, Object message) {
        // Validate kafka publisher parameters
        validateEventInfo(topic, key, message);

        log.debug("Sending message={} to topic={}", message, topic);
        try {
            return kafkaTemplate.send(topic, key, message)
                    .whenComplete((result, ex) -> {
                        if (Objects.isNull(ex)) {
                            log.debug("Kafka message published successfully with topic={}, key={}, partition={}, offset={}",
                                    topic,
                                    key,
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset());
                        } else {
                            log.error(
                                    "Failed to publish Kafka message with topic={}, key={}, messageType={}",
                                    topic,
                                    key,
                                    message.getClass().getSimpleName(),
                                    ex
                            );
                        }
                    });
        } catch (KafkaException ex) {
            log.error("Failed to send kafka message with topic={}, key={}, messageType={}",
                    topic, key, message.getClass().getSimpleName(), ex);
            throw new KafkaPublisherException(ErrorCode.FAILED_TO_SEND_MESSAGE,
                    ex,
                    topic,
                    key,
                    message.getClass().getSimpleName());
        }
    }

    private void validateEventInfo(String topic, String key, Object message) {
        if (Objects.isNull(topic) || topic.isBlank())
            throw new KafkaPublisherException(ErrorCode.TOPIC_NAME_MUST_NOT_BE_BLANK);
        if (Objects.isNull(key) || key.isBlank())
            throw new KafkaPublisherException(ErrorCode.KEY_MUST_NOT_BE_BLANK);
        if (Objects.isNull(message))
            throw new KafkaPublisherException(ErrorCode.MESSAGE_MUST_NOT_BE_NULL);
    }
}
