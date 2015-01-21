package minium.web;

public class CannotFreezeException extends WebElementsException {

    private static final long serialVersionUID = 6388359025347218415L;

    public CannotFreezeException() {
        super();
    }

    public CannotFreezeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotFreezeException(String message) {
        super(message);
    }

    public CannotFreezeException(Throwable cause) {
        super(cause);
    }

}
