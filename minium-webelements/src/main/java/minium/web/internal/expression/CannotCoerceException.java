package minium.web.internal.expression;

public class CannotCoerceException extends RuntimeException {

    private static final long serialVersionUID = -3758921245640806235L;

    public CannotCoerceException() {
        super();
    }

    public CannotCoerceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotCoerceException(String message) {
        super(message);
    }

    public CannotCoerceException(Throwable cause) {
        super(cause);
    }

}
