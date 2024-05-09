package eventloop.interfaces;

import eventloop.EventMessage;
import eventloop.interfaces.EventListenerIF;

public interface EventSourceIF<T> {

    void addListener(EventListenerIF<T> listener);

    void removeListener(EventListenerIF<T> listener);

    void broadcast(EventMessage<T> message);
}
