package minium.web;

public class WebElementsException extends RuntimeException {

    private static final long serialVersionUID = -7292984867182787890L;

    public WebElementsException() {
        super();
    }

    public WebElementsException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebElementsException(String message) {
        super(message);
    }

    public WebElementsException(Throwable cause) {
        super(cause);
    }
}
