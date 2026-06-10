package io.coffee.ordering.messaging.platform.saga;

public interface SagaContext {
    String sagaId();
    String requestId();
    String correlationId();
}
