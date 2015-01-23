package minium.actions;

import minium.Elements;

import com.google.common.base.Predicate;

public class TimeoutException extends RuntimeException {

    private static final long serialVersionUID = 2337656934950527267L;
    private Predicate<?> predicate;
    private Elements elems;

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
        super(e);
        this.predicate = predicate;
        this.elems = elems;
    }

    public Predicate<?> getPredicate() {
        return predicate;
    }

    public Elements getElements() {
        return elems;
    }
}
