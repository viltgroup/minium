package cucumber.runtime.remote;

public class ExceptionProxy {
    private String message;
    private StackTraceElement[] stacktrace;

    public ExceptionProxy() {
    }

    public ExceptionProxy(Throwable e) {
        if (e == null) return;
        message = e.getMessage();
        stacktrace = e.getStackTrace();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StackTraceElement[] getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(StackTraceElement[] stacktrace) {
        this.stacktrace = stacktrace;
    }
}
