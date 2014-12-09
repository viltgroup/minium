package cucumber.runtime.rest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

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
import cucumber.runtime.rest.dto.ArgumentDTO;
import cucumber.runtime.rest.dto.ScenarioDTO;
import cucumber.runtime.rest.dto.StepDTO;
import cucumber.runtime.rest.dto.WorldDTO;

@RestController
@RequestMapping("/cucumber")
public class CucumberRestController {

    public static final String DEFAULT_BACKEND = "default";

    private Map<String, BackendContext> backendContexts = new HashMap<String, BackendContext>();

    public CucumberRestController(Backend backend) {
        this(Collections.singletonMap(DEFAULT_BACKEND, backend));
    }

    public CucumberRestController(Map<String, Backend> backends) {
        for (Entry<String, Backend> entry : backends.entrySet()) {
            backendContexts.put(entry.getKey(), new BackendContext(entry.getValue()));
        }
    }

    @RequestMapping(value = "/backends/{backendId}/glues", method = RequestMethod.GET)
    public Set<GlueProxy> getGlues(@PathVariable String backendId) {
        return backendContext(backendId).getGlues();
    }

    @RequestMapping(value = CucumberRestConstants.GLUES_URI, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public GlueProxy createGlue(@PathVariable String backendId, @RequestParam("path") String ... paths) {
        return backendContext(backendId).createGlue(paths);
    }

    @RequestMapping(value = CucumberRestConstants.GLUE_URI, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteGlue(@PathVariable String backendId, UUID uuid) {
        backendContext(backendId).deleteGlue(uuid);
    }

    @RequestMapping(value = CucumberRestConstants.WORLDS_URI, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public WorldDTO createWorld(@PathVariable String backendId) {
        return backendContext(backendId).createWorld();
    }

    @RequestMapping(value = CucumberRestConstants.WORLD_URI, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteWorld(@PathVariable String backendId, @PathVariable UUID uuid) {
        backendContext(backendId).deleteWorld(uuid);
    }

    @RequestMapping(value = CucumberRestConstants.HOOK_EXEC_URI, method = RequestMethod.POST)
    public HookExecutionResult execute(@PathVariable String backendId, @PathVariable UUID uuid, @PathVariable long id, @RequestBody ScenarioDTO scenario) throws Throwable {
        return backendContext(backendId).execute(uuid, id, scenario);
    }

    @RequestMapping(value = CucumberRestConstants.STEP_EXEC_URI, method = RequestMethod.POST)
    public StepExecutionResult execute(@PathVariable String backendId, @PathVariable UUID uuid, @PathVariable long id, @RequestBody StepDefinitionInvocation stepDefinitionInvocation) throws Throwable {
        return backendContext(backendId).execute(uuid, id, stepDefinitionInvocation);
    }

    @RequestMapping(value = CucumberRestConstants.STEP_MATCHED_URI, method = RequestMethod.POST)
    public ArgumentDTO[] matchedArguments(@PathVariable String backendId, @PathVariable UUID uuid, @PathVariable long id, @RequestBody StepDTO step) throws Throwable {
        return backendContext(backendId).matchedArguments(uuid, id, step);
    }

    @RequestMapping(value = CucumberRestConstants.SNIPPET_URI, params = "type", method = RequestMethod.GET)
    public String getSnippet(@PathVariable String backendId, @RequestBody StepDTO serializableStep, @RequestParam SnippetType type) {
        return backendContext(backendId).getSnippet(serializableStep, type);
    }

    private BackendContext backendContext(String backendId) {
        return backendContexts.get(backendId);
    }
}
