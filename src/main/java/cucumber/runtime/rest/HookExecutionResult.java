package cucumber.runtime.rest;

import cucumber.runtime.rest.dto.ScenarioDTO;

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
