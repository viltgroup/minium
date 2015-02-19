package minium.web.internal;

import minium.ElementsException;

public class ResourceException extends ElementsException {

    private static final long serialVersionUID = 6388359025347218415L;

    public ResourceException() {
        super();
    }

    public ResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(Throwable cause) {
        super(cause);
    }

}
