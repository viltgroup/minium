package cucumber.runtime.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import cucumber.runtime.HookDefinition;
import cucumber.runtime.StepDefinition;
import cucumber.runtime.rest.SimpleGlue;

public class GlueDTO implements Serializable {

    private static final long serialVersionUID = -1207719130996258182L;

    private UUID uuid;
    private List<StepDefinitionDTO> stepDefinitions = new ArrayList<StepDefinitionDTO>();
    private List<HookDefinitionDTO> beforeHooks = new ArrayList<HookDefinitionDTO>();
    private List<HookDefinitionDTO> afterHooks = new ArrayList<HookDefinitionDTO>();

    public GlueDTO() {
    }

    public GlueDTO(SimpleGlue glue) {
        this.uuid = glue.getUuid();
        for (Entry<Long, HookDefinition> entry : glue.getBeforeHookDefinitions().entrySet()) {
            beforeHooks.add(new HookDefinitionDTO(uuid, entry.getKey(), entry.getValue()));
        }
        for (Entry<Long, HookDefinition> entry : glue.getAfterHookDefinitions().entrySet()) {
            afterHooks.add(new HookDefinitionDTO(uuid, entry.getKey(), entry.getValue()));
        }
        for (Entry<Long, StepDefinition> entry : glue.getStepDefinitions().entrySet()) {
            stepDefinitions.add(new StepDefinitionDTO(uuid, entry.getKey(), entry.getValue()));
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public List<StepDefinitionDTO> getStepDefinitions() {
        return stepDefinitions;
    }

    public void setStepDefinitions(List<StepDefinitionDTO> stepDefinitions) {
        this.stepDefinitions = stepDefinitions;
    }

    public List<HookDefinitionDTO> getBeforeHooks() {
        return beforeHooks;
    }

    public void setBeforeHooks(List<HookDefinitionDTO> beforeHookDefinitions) {
        this.beforeHooks = beforeHookDefinitions;
    }

    public List<HookDefinitionDTO> getAfterHooks() {
        return afterHooks;
    }

    public void setAfterHooks(List<HookDefinitionDTO> afterHookDefinitions) {
        this.afterHooks = afterHookDefinitions;
    }
}
