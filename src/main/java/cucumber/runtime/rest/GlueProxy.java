package cucumber.runtime.rest;

import gherkin.I18n;
import gherkin.formatter.model.Step;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import cucumber.api.StepDefinitionReporter;
import cucumber.runtime.DuplicateStepDefinitionException;
import cucumber.runtime.Glue;
import cucumber.runtime.HookDefinition;
import cucumber.runtime.StepDefinition;
import cucumber.runtime.StepDefinitionMatch;
import cucumber.runtime.rest.dto.StepDefinitionDTO;

@JsonAutoDetect(
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.ANY
)
public class GlueProxy implements Glue, Serializable {

    static transient AtomicLong HOOK_DEF_ID_GENERATOR = new AtomicLong();
    static transient AtomicLong STEP_DEF_ID_GENERATOR = new AtomicLong();

    private static final long serialVersionUID = -1207719130996258182L;

    private UUID uuid;
    private List<StepDefinitionDTO> stepDefinitions = new ArrayList<StepDefinitionDTO>();
    private List<HookDefinitionProxy> beforeHookDefinitions = new ArrayList<HookDefinitionProxy>();
    private List<HookDefinitionProxy> afterHookDefinitions = new ArrayList<HookDefinitionProxy>();

    private transient Map<Long, StepDefinition> cucumberStepDefinitions = new HashMap<Long, StepDefinition>();
    private transient Map<Long, HookDefinition> cucumberHookDefinitions = new HashMap<Long, HookDefinition>();

    public GlueProxy() {
    }

    public GlueProxy( UUID uuid) {
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
        long id = STEP_DEF_ID_GENERATOR.incrementAndGet();
        StepDefinitionDTO remoteStepDefinition = new StepDefinitionDTO(uuid, id, stepDefinition);
        stepDefinitions.add(remoteStepDefinition);
        cucumberStepDefinitions.put(remoteStepDefinition.getId(), stepDefinition);
    }

    @Override
    public void addBeforeHook(HookDefinition hookDefinition) {
        long id = HOOK_DEF_ID_GENERATOR.incrementAndGet();
        HookDefinitionProxy remoteHookDefinition = new HookDefinitionProxy(uuid, id);
        beforeHookDefinitions.add(remoteHookDefinition);
        cucumberHookDefinitions.put(remoteHookDefinition.getId(), hookDefinition);
    }

    @Override
    public void addAfterHook(HookDefinition hookDefinition) {
        long id = HOOK_DEF_ID_GENERATOR.incrementAndGet();
        HookDefinitionProxy remoteHookDefinition = new HookDefinitionProxy(uuid, id);
        afterHookDefinitions.add(remoteHookDefinition);
        cucumberHookDefinitions.put(remoteHookDefinition.getId(), hookDefinition);
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

    public List<StepDefinitionDTO> getStepDefinitions() {
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
        for (StepDefinitionDTO stepDefinition : stepDefinitions) {
            stepDefinitionReporter.stepDefinition(stepDefinition);
        }
    }

    public StepDefinition stepDefinition(Long id) {
        return cucumberStepDefinitions.get(id);
    }

    public HookDefinition hookDefinition(Long id) {
        return cucumberHookDefinitions.get(id);
    }
}
