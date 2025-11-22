package org.btwr.shared_library.event;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

/**
 * Generic event hub.
 * Each event holds a list of handlers and produces an invoker via a combiner function.
 */
public final class SimpleEvent<T> {

    private final List<T> handlers = new CopyOnWriteArrayList<>();
    private final Function<List<T>, T> combiner;

    public SimpleEvent(Function<List<T>, T> combiner) {
        this.combiner = combiner;
    }

    public void add(T handler) {
        handlers.add(handler);
    }

    public void remove(T handler) {
        handlers.remove(handler);
    }

    public T createInvoker() {
        return combiner.apply(handlers);
    }
}
