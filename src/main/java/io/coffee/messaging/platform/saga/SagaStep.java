package io.coffee.messaging.platform.saga;

public interface SagaStep<C extends SagaContext>{
    String stepName();
    void execute(C context);

}
