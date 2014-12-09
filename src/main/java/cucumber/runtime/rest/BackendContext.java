package cucumber.runtime.rest;

import gherkin.formatter.Argument;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import cucumber.api.SnippetType;
import cucumber.runtime.Backend;
import cucumber.runtime.HookDefinition;
import cucumber.runtime.StepDefinition;
import cucumber.runtime.rest.dto.ArgumentDTO;
import cucumber.runtime.rest.dto.ScenarioDTO;
import cucumber.runtime.rest.dto.StepDTO;
import cucumber.runtime.rest.dto.WorldDTO;
import cucumber.runtime.xstream.LocalizedXStreams;

public class BackendContext {

    private Backend backend;
    private Map<UUID, GlueProxy> glues = new HashMap<UUID, GlueProxy>();
    WorldDTO world;

    public BackendContext(Backend backend) {
        this.backend = backend;
    }

    public Set<GlueProxy> getGlues() {
        return new HashSet<GlueProxy>(glues.values());
    }

    public GlueProxy createGlue(String ... paths) {
        GlueProxy glue = new GlueProxy(UUID.randomUUID());
        backend.loadGlue(glue, Arrays.asList(paths));
        glues.put(glue.getUuid(), glue);
        return glue;
    }

    public void deleteGlue(UUID uuid) {
        GlueProxy remoteGlue = glues.remove(uuid);
        if (remoteGlue == null) throw new ResourceNotFoundException(String.format("Glue %s not found", uuid));
    }

    public WorldDTO createWorld() {
        Preconditions.checkState(world == null, "A world already exists");
        world = new WorldDTO(UUID.randomUUID());
        backend.buildWorld();
        return world;
    }

    public void deleteWorld(UUID uuid) {
        Preconditions.checkState(world != null, "No world exists");
        Preconditions.checkState(world.getUuid().equals(uuid), "No world with UUID %s exists", uuid);
        backend.disposeWorld();
        world = null;
    }

    public HookExecutionResult execute(UUID uuid, long id, ScenarioDTO scenario) throws Throwable {
        GlueProxy glue = glues.get(uuid);
        HookDefinition hookDefinition = glue.hookDefinition(id);
        hookDefinition.execute(scenario);
        return new HookExecutionResult(scenario);
    }

    public StepExecutionResult execute(UUID uuid, long id, StepDefinitionInvocation stepDefinitionInvocation) throws Throwable {
        GlueProxy glue = glues.get(uuid);
        StepDefinition stepDefinition = glue.stepDefinition(id);
        LocalizedXStreams streams = new LocalizedXStreams(Thread.currentThread().getContextClassLoader());
        stepDefinition.execute(stepDefinitionInvocation.getI18n(), stepDefinitionInvocation.getArgs(streams, stepDefinition));
        return new StepExecutionResult();
    }

    public ArgumentDTO[] matchedArguments(UUID uuid, long id, StepDTO stepProxy) throws Throwable {
        GlueProxy glue = glues.get(uuid);
        StepDefinition stepDefinition = glue.stepDefinition(id);
        List<Argument> matchedArguments = stepDefinition.matchedArguments(stepProxy.toStep());
        return convert(matchedArguments);
    }

    public String getSnippet(StepDTO serializableStep, SnippetType type) {
        return backend.getSnippet(serializableStep.toStep(), type.getFunctionNameGenerator());
    }

    public ArgumentDTO[] convert(List<Argument> args) {
        if (args == null) return null;
        ArgumentDTO[] proxies = new ArgumentDTO[args.size()];
        for (int i = 0; i < args.size(); i++) {
            Argument arg = args.get(i);
            proxies[i] = new ArgumentDTO(arg);
        }
        return proxies;
    }
}
