package eventloop;

import eventloop.interfaces.EventListenerIF;
import eventloop.interfaces.EventLoopIF;
import eventloop.interfaces.EventSourceIF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class EventManager<T> {

    private final EventSourceIF<String> eventSource;
    private final EventLoopIF<String> eventLoop;
    private static final Logger log = LoggerFactory.getLogger(EventManager.class);


    private EventManager() {
        this.eventSource = new InMemoryEventSource<>();
        this.eventLoop = SingleThreadEventLoop.of(eventSource).start();
        this.eventLoop.start();
    }

    public EventManager<T> addListener(final EventListenerIF<String> listener) {
        this.eventSource.addListener(listener);
        log.debug("Added a new listener");
        return this;
    }

    public EventManager<T> removeListener(final EventListenerIF<String> listener) {
        this.eventSource.removeListener(listener);
        log.debug("Removed a listener");
        return this;
    }

    public void send(EventMessage<String> message) {
        this.eventLoop.push(message);
    }

    private static class EventManagerHelper {
        private static final EventManager<String> INSTANCE = new EventManager<>();
    }

    public static EventManager<String> getInstance() {
        return EventManagerHelper.INSTANCE;
    }
}
