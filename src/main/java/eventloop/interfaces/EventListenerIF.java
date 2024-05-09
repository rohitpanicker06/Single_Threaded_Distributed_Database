package eventloop.interfaces;

import eventloop.EventMessage;

public interface EventListenerIF<T> {

    void onMessage(EventMessage<T> message);
    void onError(EventMessage<T> message);
}
