package cucumber.runtime.rest.dto;


public class HookExecutionResult extends ExecutionResult {

    private ScenarioDTO scenario;

    public HookExecutionResult() {
        super();
    }

    public HookExecutionResult(ScenarioDTO scenario) {
        this();
    }

    public HookExecutionResult(ScenarioDTO scenario, Throwable e) {
        super(e);
        this.scenario = scenario;
    }

    public ScenarioDTO getScenario() {
        return scenario;
    }

    public void setScenario(ScenarioDTO scenario) {
        this.scenario = scenario;
    }

}
