package cucumber.runtime.remote;

import gherkin.formatter.Argument;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cucumber.api.SnippetType;
import cucumber.runtime.Backend;
import cucumber.runtime.HookDefinition;
import cucumber.runtime.StepDefinition;
import cucumber.runtime.xstream.LocalizedXStreams;

@RestController
@RequestMapping("/cucumber-backend")
public class BackendRestController {

    static AtomicLong LONG_GENERATOR = new AtomicLong();

    private Backend backend;
    private Map<UUID, GlueProxy> glues = new HashMap<UUID, GlueProxy>();
    WorldProxy world;
    private Map<Long, StepDefinition> stepDefinitions = new HashMap<Long, StepDefinition>();
    private Map<Long, HookDefinition> hookDefinitions = new HashMap<Long, HookDefinition>();

    public BackendRestController(Backend backend) {
        this.backend = backend;
    }

    @RequestMapping(value = "/glues", method = RequestMethod.GET)
    public Set<GlueProxy> getGlues() {
        return new HashSet<GlueProxy>(glues.values());
    }

    @RequestMapping(value = "/glues", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public GlueProxy createGlue(@RequestParam("path") String ... paths) {
        GlueProxy glue = new GlueProxy(this, UUID.randomUUID());
        backend.loadGlue(glue, Arrays.asList(paths));
        glues.put(glue.getUuid(), glue);
        return glue;
    }

    @RequestMapping(value = "/glues/{uuid}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteGlue(UUID uuid) {
        GlueProxy remoteGlue = glues.remove(uuid);
        if (remoteGlue == null) throw new ResourceNotFoundException(String.format("Glue %s not found", uuid));
    }

    @RequestMapping(value = "/worlds", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public WorldProxy createWorld() {
        Preconditions.checkState(world == null, "A world already exists");
        world = new WorldProxy(UUID.randomUUID());
        backend.buildWorld();
        return world;
    }

    @RequestMapping(value = "/worlds/{uuid}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteWorld(@PathVariable UUID uuid) {
        Preconditions.checkState(world != null, "No world exists");
        Preconditions.checkState(world.getUuid().equals(uuid), "No world with UUID %s exists", uuid);
        backend.disposeWorld();
        world = null;
    }

    @RequestMapping(value = "/glues/{uuid}/hookDefinitions/{id}/execution", method = RequestMethod.POST)
    public HookExecutionResult execute(@PathVariable UUID uuid, @PathVariable long id, @RequestBody ScenarioProxy scenario) throws Throwable {
        HookDefinition hookDefinition = hookDefinitions.get(id);
        hookDefinition.execute(scenario);
        return new HookExecutionResult(scenario);
    }

    @RequestMapping(value = "/glues/{uuid}/stepDefinitions/{id}/execution", method = RequestMethod.POST)
    public StepExecutionResult execute(@PathVariable UUID uuid, @PathVariable long id, @RequestBody StepDefinitionInvocation stepDefinitionInvocation) throws Throwable {
        StepDefinition stepDefinition = stepDefinitions.get(id);
        LocalizedXStreams streams = new LocalizedXStreams(Thread.currentThread().getContextClassLoader());
        stepDefinition.execute(stepDefinitionInvocation.getI18n(), stepDefinitionInvocation.getArgs(streams, stepDefinition));
        return new StepExecutionResult();
    }

    @RequestMapping(value = "/glues/{uuid}/stepDefinitions/{id}/matchedArguments", method = RequestMethod.POST)
    public ArgumentProxy[] matchedArguments(@PathVariable UUID uuid, @PathVariable long id, @RequestBody StepProxy stepProxy) throws Throwable {
        StepDefinition stepDefinition = stepDefinitions.get(id);
        List<Argument> matchedArguments = stepDefinition.matchedArguments(stepProxy.toStep());
        return convert(matchedArguments);
    }

    @RequestMapping(value = "/glues/{uuid}/snippet", params = "type", method = RequestMethod.GET)
    public String getSnippet(@RequestBody StepProxy serializableStep, @RequestParam SnippetType type) {
        return backend.getSnippet(serializableStep.toStep(), type.getFunctionNameGenerator());
    }

    protected HookDefinitionProxy addHookDefinition(UUID glueId, HookDefinition hookDefinition) {
        long id = LONG_GENERATOR.incrementAndGet();
        HookDefinitionProxy remoteHookDefinition = new HookDefinitionProxy(glueId, id);
        hookDefinitions.put(remoteHookDefinition.getId(), hookDefinition);
        return remoteHookDefinition;
    }

    protected StepDefinitionProxy addStepDefinition(UUID glueId, StepDefinition stepDefinition) {
        long id = LONG_GENERATOR.incrementAndGet();
        StepDefinitionProxy remoteStepDefinition = new StepDefinitionProxy(glueId, id, stepDefinition);
        stepDefinitions.put(remoteStepDefinition.getId(), stepDefinition);
        return remoteStepDefinition;
    }

    public ArgumentProxy[] convert(List<Argument> args) {
        if (args == null) return null;
        ArgumentProxy[] proxies = new ArgumentProxy[args.size()];
        for (int i = 0; i < args.size(); i++) {
            Argument arg = args.get(i);
            proxies[i] = new ArgumentProxy(arg);
        }
        return proxies;
    }
}
