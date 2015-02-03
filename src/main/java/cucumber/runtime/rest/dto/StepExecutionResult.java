package cucumber.runtime.rest.dto;


public class StepExecutionResult extends ExecutionResult {

    public StepExecutionResult() {
        super();
    }

    public StepExecutionResult(Exception e) {
        super(e);
    }
}
