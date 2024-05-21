package eventloop.interfaces;

import eventloop.EventMessage;

import java.io.IOException;

public interface EventListenerIF<T> {

    void onMessage(EventMessage<T> message) throws IOException;
    void onError(EventMessage<T> message);
}
