package eventloop;

import eventloop.interfaces.EventListenerIF;
import eventloop.interfaces.EventSourceIF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class InMemoryEventSource<T> implements EventSourceIF<T> {

    private final List<EventListenerIF<T>> listeners = new ArrayList<>();

    @Override
    public void addListener(final EventListenerIF<T> listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(final EventListenerIF<T> listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void broadcast(final EventMessage<T> message) {
        if (message.hasError()) this.listeners.forEach(listener -> listener.onError(message));
        this.listeners.forEach(listener -> {
            try {
                listener.onMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}