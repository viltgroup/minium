package cucumber.runtime.rest;

import static cucumber.runtime.rest.CucumberRestConstants.BACKEND_PREFIX;
import static cucumber.runtime.rest.CucumberRestConstants.GLUES_URI;
import static cucumber.runtime.rest.CucumberRestConstants.GLUE_URI;
import static cucumber.runtime.rest.CucumberRestConstants.HOOK_EXEC_URI;
import static cucumber.runtime.rest.CucumberRestConstants.HOOK_TAG_MATCH_URI;
import static cucumber.runtime.rest.CucumberRestConstants.SNIPPET_URI;
import static cucumber.runtime.rest.CucumberRestConstants.STEP_EXEC_URI;
import static cucumber.runtime.rest.CucumberRestConstants.STEP_MATCHED_URI;
import static cucumber.runtime.rest.CucumberRestConstants.URL_PREFIX;
import static cucumber.runtime.rest.CucumberRestConstants.WORLDS_URI;
import static cucumber.runtime.rest.CucumberRestConstants.WORLD_URI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cucumber.runtime.Backend;
import cucumber.runtime.rest.dto.ExecutionResult;
import cucumber.runtime.rest.dto.GlueDTO;
import cucumber.runtime.rest.dto.HookExecutionResult;
import cucumber.runtime.rest.dto.ScenarioDTO;
import cucumber.runtime.rest.dto.StepDTO;
import cucumber.runtime.rest.dto.StepDefinitionInvocation;
import cucumber.runtime.rest.dto.StepMatchDTO;
import cucumber.runtime.rest.dto.TagDTO;
import cucumber.runtime.rest.dto.WorldDTO;

@RestController
@RequestMapping(URL_PREFIX)
public class CucumberRestController {

    public static final String DEFAULT_BACKEND = "default";

    Map<String, BackendContext> backendContexts = new HashMap<String, BackendContext>();

    public CucumberRestController(Backend backend) {
        this(new BackendRegistry().register(DEFAULT_BACKEND, backend));
    }

    @Autowired
    public CucumberRestController(BackendRegistry backends) {
        for (Entry<String, Backend> entry : backends.getAll().entrySet()) {
            backendContexts.put(entry.getKey(), new BackendContext(entry.getKey(), entry.getValue()));
        }
    }

    @RequestMapping(value = BACKEND_PREFIX + GLUES_URI, method = RequestMethod.GET)
    public List<GlueDTO> getGlues(@PathVariable String backendId) {
        return backendContext(backendId).getGlues();
    }

    @RequestMapping(value = BACKEND_PREFIX + GLUES_URI, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public GlueDTO createGlue(@PathVariable String backendId, @RequestParam("path") String ... paths) {
        return backendContext(backendId).createGlue(paths);
    }

    @RequestMapping(value = BACKEND_PREFIX + GLUE_URI, method = RequestMethod.DELETE)
    public void deleteGlue(@PathVariable String backendId, UUID uuid) {
        backendContext(backendId).deleteGlue(uuid);
    }

    @RequestMapping(value = BACKEND_PREFIX + WORLDS_URI, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public WorldDTO createWorld(@PathVariable String backendId) {
        return backendContext(backendId).createWorld();
    }

    @RequestMapping(value = BACKEND_PREFIX + WORLD_URI, method = RequestMethod.DELETE)
    public void deleteWorld(@PathVariable String backendId, @PathVariable UUID uuid) {
        backendContext(backendId).deleteWorld(uuid);
    }

    @RequestMapping(value = BACKEND_PREFIX + HOOK_EXEC_URI, method = RequestMethod.POST)
    public HookExecutionResult execute(@PathVariable String backendId, @PathVariable UUID uuid, @PathVariable long id, @RequestBody ScenarioDTO scenario) throws Throwable {
        return backendContext(backendId).execute(uuid, id, scenario);
    }

    @RequestMapping(value = BACKEND_PREFIX + HOOK_TAG_MATCH_URI, method = RequestMethod.POST)
    public boolean matches(@PathVariable String backendId, @PathVariable UUID uuid, @PathVariable long id, @RequestBody List<TagDTO> tags) {
        return backendContext(backendId).matches(uuid, id, tags);
    }

    @RequestMapping(value = BACKEND_PREFIX + STEP_EXEC_URI, method = RequestMethod.POST)
    public ExecutionResult execute(@PathVariable String backendId, @PathVariable UUID uuid, @PathVariable long id, @RequestBody StepDefinitionInvocation stepDefinitionInvocation) throws Throwable {
        return backendContext(backendId).execute(uuid, id, stepDefinitionInvocation);
    }

    @RequestMapping(value = BACKEND_PREFIX + STEP_MATCHED_URI, method = RequestMethod.POST)
    public StepMatchDTO matchedArguments(@PathVariable String backendId, @PathVariable UUID uuid, @PathVariable long id, @RequestBody StepDTO step) throws Throwable {
        return backendContext(backendId).matchedArguments(uuid, id, step);
    }

    @RequestMapping(value = BACKEND_PREFIX + SNIPPET_URI, method = RequestMethod.POST)
    public String getSnippet(@PathVariable String backendId, @RequestBody SnippetRequestDTO snippetRequest) {
        return backendContext(backendId).getSnippet(snippetRequest.getStep(), snippetRequest.getType());
    }

    private BackendContext backendContext(String backendId) {
        return backendContexts.get(backendId);
    }
}
