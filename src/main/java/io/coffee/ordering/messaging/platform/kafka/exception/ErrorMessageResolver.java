package io.coffee.ordering.messaging.platform.kafka.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessageResolver {
    private static final ResourceBundle RESOURCE_PATH = ResourceBundle.getBundle("message.kafka-error-messages");

    public static String getMessage(ErrorCode errorCode, Object... args) {
        String template;
        try {
            template = RESOURCE_PATH.getString(errorCode.getMessage());
        } catch (MissingResourceException ex) {
            template = errorCode.getMessage();
        }
        return MessageFormat.format(template, args);
    }
}
