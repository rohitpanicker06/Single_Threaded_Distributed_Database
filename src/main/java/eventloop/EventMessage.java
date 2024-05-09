package eventloop;

import java.io.Serial;
import java.util.EventObject;
import lombok.Getter;

@Getter
public final class EventMessage<T> extends EventObject {

    @Serial private static final long serialVersionUID = 1L;
    private final T message;
    private final Throwable error;

    public EventMessage(Object source, T message) {
        super(source);
        this.message = message;
        this.error = null;
    }

    public EventMessage(Object source, T message, Throwable error) {
        super(source);
        this.message = message;
        this.error = error;
    }

    public boolean hasError() {
        return error != null;
    }
}