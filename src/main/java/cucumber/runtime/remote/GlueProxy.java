package cucumber.runtime.remote;

import gherkin.I18n;
import gherkin.formatter.model.Step;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import cucumber.api.StepDefinitionReporter;
import cucumber.runtime.DuplicateStepDefinitionException;
import cucumber.runtime.Glue;
import cucumber.runtime.HookDefinition;
import cucumber.runtime.StepDefinition;
import cucumber.runtime.StepDefinitionMatch;

@JsonAutoDetect(
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.ANY
)
public class GlueProxy implements Glue, Serializable {

    private static final long serialVersionUID = -1207719130996258182L;

    private UUID uuid;
    private List<StepDefinitionProxy> stepDefinitions = new ArrayList<StepDefinitionProxy>();
    private List<HookDefinitionProxy> beforeHookDefinitions = new ArrayList<HookDefinitionProxy>();
    private List<HookDefinitionProxy> afterHookDefinitions = new ArrayList<HookDefinitionProxy>();

    private transient BackendRestController backendService;

    public GlueProxy() {
    }

    public GlueProxy(BackendRestController backendService, UUID uuid) {
        this.backendService = backendService;
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void addStepDefinition(StepDefinition stepDefinition) throws DuplicateStepDefinitionException {
        Preconditions.checkNotNull(backendService);
        StepDefinitionProxy remoteStepDefinition = backendService.addStepDefinition(uuid, stepDefinition);
        stepDefinitions.add(remoteStepDefinition);
    }

    @Override
    public void addBeforeHook(HookDefinition hookDefinition) {
        Preconditions.checkNotNull(backendService);
        HookDefinitionProxy remoteHookDefinition = backendService.addHookDefinition(uuid, hookDefinition);
        beforeHookDefinitions.add(remoteHookDefinition);
    }

    @Override
    public void addAfterHook(HookDefinition hookDefinition) {
        Preconditions.checkNotNull(backendService);
        HookDefinitionProxy remoteHookDefinition = backendService.addHookDefinition(uuid, hookDefinition);
        afterHookDefinitions.add(remoteHookDefinition);
    }

    @Override
    public List<HookDefinition> getBeforeHooks() {
        List<HookDefinition> hookDefinitions = new ArrayList<HookDefinition>();
        hookDefinitions.addAll(beforeHookDefinitions);
        return hookDefinitions;
    }

    @Override
    public List<HookDefinition> getAfterHooks() {
        List<HookDefinition> hookDefinitions = new ArrayList<HookDefinition>();
        hookDefinitions.addAll(afterHookDefinitions);
        return hookDefinitions;
    }

    public List<StepDefinitionProxy> getStepDefinitions() {
        return stepDefinitions;
    }

    @Override
    public int hashCode() {
        return uuid == null ? System.identityHashCode(this) : uuid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GlueProxy && uuid != null) {
            return uuid.equals(((GlueProxy) obj).getUuid());
        }
        return false;
    }

    @Override
    public StepDefinitionMatch stepDefinitionMatch(String featurePath, Step step, I18n i18n) {
        throw new UnsupportedOperationException("stepDefinitionMatch is not supported in GlueProxy");
    }

    @Override
    public void reportStepDefinitions(StepDefinitionReporter stepDefinitionReporter) {
        for (StepDefinitionProxy stepDefinition : stepDefinitions) {
            stepDefinitionReporter.stepDefinition(stepDefinition);
        }
    }
}
