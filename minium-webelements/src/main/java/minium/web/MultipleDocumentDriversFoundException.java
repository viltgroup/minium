package minium.web;

import minium.ElementsException;

public class MultipleDocumentDriversFoundException extends ElementsException {

    private static final long serialVersionUID = -4500350278311385767L;

    public MultipleDocumentDriversFoundException() {
        super();
    }

    public MultipleDocumentDriversFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MultipleDocumentDriversFoundException(String message) {
        super(message);
    }

    public MultipleDocumentDriversFoundException(Throwable cause) {
        super(cause);
    }

}
