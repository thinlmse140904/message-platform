package io.coffee.ordering.messaging.platform.outbox;

public interface OutboxScheduler {
    void process();
}
