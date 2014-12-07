package cucumber.runtime.remote;

public class HookExecutionResult {
    private ScenarioDTO scenario;

    public HookExecutionResult() {
    }

    public HookExecutionResult(ScenarioDTO scenario) {
        super();
        this.scenario = scenario;
    }

    public ScenarioDTO getScenario() {
        return scenario;
    }

    public void setScenario(ScenarioDTO scenario) {
        this.scenario = scenario;
    }

}
