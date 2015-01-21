package minium.actions;

public class TimeoutException extends RuntimeException {

    private static final long serialVersionUID = 2337656934950527267L;

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
}
