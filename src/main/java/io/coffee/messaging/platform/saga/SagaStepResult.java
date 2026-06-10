package io.coffee.messaging.platform.saga;

import io.coffee.messaging.platform.saga.constant.SagaStepStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SagaStepResult {
    private final SagaStepStatus status;
    private final String message;
    private final String errorCode;

    public static SagaStepResult success() {
        return new SagaStepResult(SagaStepStatus.SUCCESS, null, null);
    }

    public static SagaStepResult failed() {
        return new SagaStepResult(SagaStepStatus.FAILED, null, null);
    }

    public static SagaStepResult retryableFailed() {
        return new SagaStepResult(SagaStepStatus.RETRYABLE_FAILED, null, null);
    }

    public static SagaStepResult skipped() {
        return new SagaStepResult(SagaStepStatus.SKIPPED, null, null);
    }
}
