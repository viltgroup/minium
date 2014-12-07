package cucumber.runtime.remote;

public class StepExecutionResult {

    private ExceptionDTO exception;

    public StepExecutionResult() {
    }

    public ExceptionDTO getException() {
        return exception;
    }

    public void setException(ExceptionDTO exception) {
        this.exception = exception;
    }
}
