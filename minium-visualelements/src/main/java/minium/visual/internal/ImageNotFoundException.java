package minium.visual.internal;


public class ImageNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8652392162476441743L;

    public ImageNotFoundException() {
        super();
    }

    public ImageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageNotFoundException(String message) {
        super(message);
    }

    public ImageNotFoundException(Throwable cause) {
        super(cause);
    }
}
