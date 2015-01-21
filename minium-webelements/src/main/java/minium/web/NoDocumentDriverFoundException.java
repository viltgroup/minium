package minium.web;

public class NoDocumentDriverFoundException extends WebElementsException {

    private static final long serialVersionUID = 7011052529976809314L;

    public NoDocumentDriverFoundException() {
        super();
    }

    public NoDocumentDriverFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDocumentDriverFoundException(String message) {
        super(message);
    }

    public NoDocumentDriverFoundException(Throwable cause) {
        super(cause);
    }

}
