package minium.actions;

import minium.Elements;
import minium.ElementsException;

import com.google.common.base.Predicate;

public class TimeoutException extends ElementsException {

    private static final long serialVersionUID = 2337656934950527267L;

    protected Predicate<?> predicate;

    public TimeoutException() {
        super();
    }

    public TimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeoutException(String message) {
        super(message);
    }

    public TimeoutException(Throwable cause) {
        super(cause);
    }

    public TimeoutException(Predicate<?> predicate, Elements elems, Throwable e) {
        super(elems, e);
        this.predicate = predicate;
    }

    public Predicate<?> getPredicate() {
        return predicate;
    }
}
