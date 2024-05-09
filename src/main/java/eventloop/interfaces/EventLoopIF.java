package eventloop.interfaces;

import eventloop.EventMessage;

public interface EventLoopIF<T> {

    EventLoopIF<T> start();

    void push(EventMessage<T> message);

    boolean hasEvents();
}
