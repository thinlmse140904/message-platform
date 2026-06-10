package io.coffee.messaging.platform.outbox;

public interface OutboxScheduler {
    void process();
}
