package org.btwr.shared_library.api.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Small generic event container.
 * Stores handlers and produces a single invoker by combining them.
 */
public final class SimpleEvent<T> {

    // All registered handlers for this event, ordered by registration.
    private final List<T> handlers = new ArrayList<>();

    // Combines the current handler list into a single callable invoker.
    private final Function<List<T>, T> combiner;

    /**
     * @param combiner A function that takes the current handler list and returns
     *                 a merged invoker (e.g., a dispatcher or composite handler).
     */
    public SimpleEvent(Function<List<T>, T> combiner) {
        this.combiner = combiner;
    }

    /**
     * Register a handler into this event.
     */
    public void add(T handler) {
        handlers.add(handler);
    }

    /**
     * Unregister a handler from this event.
     */
    public void remove(T handler) {
        handlers.remove(handler);
    }

    /**
     * Produce the current invoker by running the combiner over the handler list.
     * This does *not* cache the result—call again if the list changes.
     */
    public T createInvoker() {
        return combiner.apply(handlers);
    }
}
