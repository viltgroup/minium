package cucumber.runtime.rest.dto;


public abstract class ExecutionResult {

    public static enum Status {
        SUCCEDED,
        FAILED
    }

    private Status status = Status.SUCCEDED;
    protected ExceptionDTO exception;

    public ExecutionResult() {
        status = Status.SUCCEDED;
    }

    public ExecutionResult(Throwable e) {
        status = Status.FAILED;
        exception = new ExceptionDTO(e);
    }

    public Status getStatus() {
        return status == null ? (exception != null ? Status.FAILED : Status.SUCCEDED) : status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ExceptionDTO getException() {
        return exception;
    }

    public void setException(ExceptionDTO exception) {
        this.exception = exception;
    }

}