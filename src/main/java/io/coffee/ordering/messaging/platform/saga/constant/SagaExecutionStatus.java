package io.coffee.ordering.messaging.platform.saga.constant;

public enum SagaExecutionStatus {
    STARTED, PROCESSING, COMPLETED, COMPENSATING, COMPENSATED
}
