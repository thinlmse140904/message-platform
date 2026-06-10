package io.coffee.messaging.platform.saga;

public interface SagaContext {
    String sagaId();
    String requestId();
    String correlationId();
}
