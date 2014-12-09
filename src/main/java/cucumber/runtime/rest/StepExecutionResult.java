package cucumber.runtime.rest;

import cucumber.runtime.rest.dto.ExceptionDTO;

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
