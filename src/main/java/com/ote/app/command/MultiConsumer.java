package com.ote.app.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * Created by Olivier on 20/12/2015.
 */
public class MultiConsumer<T> implements Consumer<T> {

    private Collection<Consumer<T>> handlers;

    @Override
    public void accept(T param) {
        if (handlers != null) {
            handlers.forEach(h -> h.accept(param));
        }
    }

    public MultiConsumer addHandler(Consumer<T> handler) {
        if (handlers == null) {
            handlers = new ArrayList<>(10);
        }
        handlers.add(handler);
        return this;
    }
}
