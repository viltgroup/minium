package cucumber.runtime.remote;

public class HookExecutionResult {
    private ScenarioProxy scenario;

    public HookExecutionResult() {
    }

    public HookExecutionResult(ScenarioProxy scenario) {
        super();
        this.scenario = scenario;
    }

    public ScenarioProxy getScenario() {
        return scenario;
    }

    public void setScenario(ScenarioProxy scenario) {
        this.scenario = scenario;
    }

}
