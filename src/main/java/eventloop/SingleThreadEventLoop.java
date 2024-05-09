package eventloop;

import eventloop.interfaces.EventLoopIF;
import eventloop.interfaces.EventSourceIF;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


public final class SingleThreadEventLoop<T> implements EventLoopIF<T> {

    private final BlockingQueue<EventMessage<T>> eventQueue;
    private final Thread eventLoopThread;

    private static final Logger log = LoggerFactory.getLogger(EventManager.class);

    private SingleThreadEventLoop(EventSourceIF<T> eventSource) {
        this.eventQueue = new LinkedBlockingQueue<>();
        this.eventLoopThread = new Thread(new EventLoopExecutor<>(eventSource, this.eventQueue));
        Runtime.getRuntime().addShutdownHook(new Thread(eventLoopThread::interrupt));
    }

    public static <T> SingleThreadEventLoop<T> of(EventSourceIF<T> eventSource) {
        return new SingleThreadEventLoop<>(eventSource);
    }

    @Override
    public EventLoopIF<T> start() {
        if (!this.eventLoopThread.isAlive()) eventLoopThread.start();
        return this;
    }

    @Override
    public void push(final EventMessage<T> message) {
        if(this.eventLoopThread.isAlive()) this.eventQueue.add(message);
    }

    @Override
    public boolean hasEvents() {
        return !eventQueue.isEmpty();
    }

    private record EventLoopExecutor<T>(
            EventSourceIF<T> eventSource, BlockingQueue<EventMessage<T>> eventQueue) implements Runnable {

        @Override
        @SuppressWarnings("InfiniteLoopStatement")
        public void run() {
            log.debug("Running single thread event loop");
            try (final var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
                while (true) {
                    var message = eventQueue.poll();
                    if (message == null) continue;
                    executorService.submit(() -> eventSource.broadcast(message));
                }
            }
        }
    }
}
