package minium;

public class ElementsException extends RuntimeException {

    private static final long serialVersionUID = -7292984867182787890L;

    private Elements elems;

    public ElementsException() {
        super();
    }

    public ElementsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElementsException(String message) {
        super(message);
    }

    public ElementsException(Throwable cause) {
        super(cause);
    }

    public ElementsException(Elements elems, Throwable e) {
        super(e);
        this.elems = elems;
    }

    public ElementsException(Elements elems, String msg, Throwable e) {
        super(msg, e);
        this.elems = elems;
    }

    public Elements getElements() {
        return elems;
    }
}
