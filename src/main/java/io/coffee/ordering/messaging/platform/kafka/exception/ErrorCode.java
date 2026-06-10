package io.coffee.ordering.messaging.platform.kafka.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    TOPIC_NAME_MUST_NOT_BE_BLANK("kafka.producer.topic-name.blank"),
    KEY_MUST_NOT_BE_BLANK("kafka.producer.key.blank"),
    MESSAGE_MUST_NOT_BE_NULL("kafka.producer.message.null"),
    FAILED_TO_SEND_MESSAGE("kafka.producer.message.publish.failed");

    private final String message;
}
