package cucumber.runtime.remote;

public class StepExecutionResult {

    private ExceptionProxy exception;

    public StepExecutionResult() {
    }

    public ExceptionProxy getException() {
        return exception;
    }

    public void setException(ExceptionProxy exception) {
        this.exception = exception;
    }
}
