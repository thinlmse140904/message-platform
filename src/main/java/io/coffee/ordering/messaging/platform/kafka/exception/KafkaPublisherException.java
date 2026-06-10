package io.coffee.ordering.messaging.platform.kafka.exception;

import lombok.Getter;

@Getter
public class KafkaPublisherException extends RuntimeException {
    private final ErrorCode errorCode;

    public KafkaPublisherException(ErrorCode errorCode) {
        super(ErrorMessageResolver.getMessage(errorCode));
        this.errorCode = errorCode;
    }

    public KafkaPublisherException(ErrorCode errorCode, Object... args) {
        super(ErrorMessageResolver.getMessage(errorCode, args));
        this.errorCode = errorCode;
    }

    public KafkaPublisherException(ErrorCode errorCode, Throwable cause, Object... args) {
        super(ErrorMessageResolver.getMessage(errorCode, args), cause);
        this.errorCode = errorCode;
    }
}
