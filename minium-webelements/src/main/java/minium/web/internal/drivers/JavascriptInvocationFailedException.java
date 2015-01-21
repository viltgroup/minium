package minium.web.internal.drivers;

import minium.web.WebElementsException;

public class JavascriptInvocationFailedException extends WebElementsException {

    private static final long serialVersionUID = 1L;

    public JavascriptInvocationFailedException() {
        super();
    }

    public JavascriptInvocationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public JavascriptInvocationFailedException(String message) {
        super(message);
    }

    public JavascriptInvocationFailedException(Throwable cause) {
        super(cause);
    }
}
