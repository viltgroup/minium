package cucumber.runtime.rest;

import static cucumber.runtime.rest.CucumberRestConstants.GLUES_URI;
import static cucumber.runtime.rest.CucumberRestConstants.HOOK_EXEC_URI;
import static cucumber.runtime.rest.CucumberRestConstants.HOOK_TAG_MATCH_URI;
import static cucumber.runtime.rest.CucumberRestConstants.STEP_EXEC_URI;
import static cucumber.runtime.rest.CucumberRestConstants.STEP_MATCHED_URI;
import static cucumber.runtime.rest.CucumberRestConstants.WORLDS_URI;
import static cucumber.runtime.rest.CucumberRestConstants.WORLD_URI;
import gherkin.I18n;
import gherkin.formatter.Argument;
import gherkin.formatter.model.Step;
import gherkin.formatter.model.Tag;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cucumber.api.Scenario;
import cucumber.api.SnippetType;
import cucumber.runtime.Backend;
import cucumber.runtime.Glue;
import cucumber.runtime.UnreportedStepExecutor;
import cucumber.runtime.rest.dto.ArgumentDTO;
import cucumber.runtime.rest.dto.ExecutionResult.Status;
import cucumber.runtime.rest.dto.GlueDTO;
import cucumber.runtime.rest.dto.HookDefinitionDTO;
import cucumber.runtime.rest.dto.HookExecutionResult;
import cucumber.runtime.rest.dto.ScenarioDTO;
import cucumber.runtime.rest.dto.StepDTO;
import cucumber.runtime.rest.dto.StepDefinitionDTO;
import cucumber.runtime.rest.dto.StepDefinitionInvocation;
import cucumber.runtime.rest.dto.StepExecutionResult;
import cucumber.runtime.rest.dto.TagDTO;
import cucumber.runtime.rest.dto.WorldDTO;
import cucumber.runtime.snippets.FunctionNameGenerator;

public class RemoteBackend implements Backend {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteBackend.class);

    private static final String FUNCTION_NAME = "function name";
    private static final Map<String, SnippetType> snippetTypes = new HashMap<String, SnippetType>();

    static {
        snippetTypes.put(SnippetType.CAMELCASE.getFunctionNameGenerator().generateFunctionName(FUNCTION_NAME), SnippetType.CAMELCASE);
        snippetTypes.put(SnippetType.UNDERSCORE.getFunctionNameGenerator().generateFunctionName(FUNCTION_NAME), SnippetType.UNDERSCORE);
    }

    private final String baseUrl;
    private final String backendId;
    private final RestTemplate template;
    private WorldDTO world;

    public RemoteBackend(String baseUrl) {
        this(baseUrl, new RestTemplate());
    }

    public RemoteBackend(String baseUrl, String backendId) {
        this(baseUrl, backendId, new RestTemplate());
    }

    public RemoteBackend(String baseUrl, RestTemplate template) {
        this(baseUrl, CucumberRestController.DEFAULT_BACKEND, template);
    }

    public RemoteBackend(String baseUrl, String backendId, RestTemplate template) {
        this.baseUrl = baseUrl;
        this.backendId = backendId;
        this.template = template;
    }

    public RestTemplate getTemplate() {
        return template;
    }

    @Override
    public void loadGlue(Glue glue, List<String> gluePaths) {
        URI uri = uriBuilderFor(GLUES_URI).queryParam("path", gluePaths.toArray()).buildAndExpand(backendId).toUri();
        GlueDTO remoteGlue = template.postForObject(uri, null, GlueDTO.class);

        for (HookDefinitionDTO definitionDto : remoteGlue.getBeforeHooks()) {
            glue.addBeforeHook(new RemoteHookDefinition(this, definitionDto));
        }
        for (HookDefinitionDTO definitionDto : remoteGlue.getAfterHooks()) {
            glue.addAfterHook(new RemoteHookDefinition(this, definitionDto));
        }
        for (StepDefinitionDTO definitionDto : remoteGlue.getStepDefinitions()) {
            glue.addStepDefinition(new RemoteStepDefinition(this, definitionDto));
        }
    }

    @Override
    public void setUnreportedStepExecutor(UnreportedStepExecutor executor) {
    }

    @Override
    public void buildWorld() {
        if (world != null) {
            LOGGER.warn("A world with UUID {} already exists", world.getUuid());
        }
        URI uri = uriBuilderFor(WORLDS_URI).buildAndExpand(backendId).toUri();
        world = template.postForObject(uri, null, WorldDTO.class);
    }

    @Override
    public void disposeWorld() {
        if (world == null) {
            LOGGER.warn("No world exists");
        } else {
            URI uri = uriBuilderFor(WORLD_URI).buildAndExpand(backendId, world.getUuid()).toUri();
            template.delete(uri);
            world = null;
        }
    }

    @Override
    public String getSnippet(Step step, FunctionNameGenerator nameGenerator) {
        SnippetType snippetType = getSnippetType(nameGenerator);
        return template.getForObject(baseUrl + "/snippet", String.class, Collections.singletonMap("type", snippetType.name()));
    }

    public void execute(HookDefinitionDTO hookDefinition, Scenario scenario) {
        ScenarioDTO remoteScenario = new ScenarioDTO(scenario);
        URI uri = uriBuilderFor(HOOK_EXEC_URI).buildAndExpand(backendId, hookDefinition.getGlueId(), hookDefinition.getId()).toUri();
        HookExecutionResult execution = template.postForObject(uri, remoteScenario, HookExecutionResult.class);
        remoteScenario.populate(scenario);

        if (execution.getStatus() == Status.FAILED) {
            throw new RemoteExecutionException(execution.getException());
        }
    }

    public boolean matches(HookDefinitionDTO hookDefinition, Collection<Tag> tags) {
        URI uri = uriBuilderFor(HOOK_TAG_MATCH_URI).buildAndExpand(backendId, hookDefinition.getGlueId(), hookDefinition.getId()).toUri();
        return template.postForObject(uri, toTagDTOs(tags), Boolean.class);
    }

    public void execute(StepDefinitionDTO stepDefinition, I18n i18n, Object[] args) {
        StepDefinitionInvocation stepDefinitionInvocation = new StepDefinitionInvocation(i18n, args);
        URI uri = uriBuilderFor(STEP_EXEC_URI).buildAndExpand(backendId, stepDefinition.getGlueId(), stepDefinition.getId()).toUri();
        StepExecutionResult execution = template.postForObject(uri, stepDefinitionInvocation, StepExecutionResult.class);

        if (execution.getStatus() == Status.FAILED) {
            throw new RemoteExecutionException(execution.getException());
        }
    }

    public SnippetType getSnippetType(FunctionNameGenerator nameGenerator) {
        String functionName = nameGenerator.generateFunctionName(FUNCTION_NAME);
        SnippetType snippetType = snippetTypes.get(functionName);
        return Preconditions.checkNotNull(snippetType, "FunctionNameGenerator is not camel case or underscore (it generated %s)", functionName);
    }

    public List<Argument> matchedArguments(StepDefinitionDTO stepDefinition, Step step) {
        StepDTO stepProxy = new StepDTO(step);
        URI uri = uriBuilderFor(STEP_MATCHED_URI).buildAndExpand(backendId, stepDefinition.getGlueId(), stepDefinition.getId()).toUri();
        ArgumentDTO[] matchedArguments = template.postForObject(uri, stepProxy, ArgumentDTO[].class);
        return toGerkinArguments(matchedArguments);
    }

    protected UriComponentsBuilder uriBuilderFor(String path) {
        return UriComponentsBuilder.fromHttpUrl(baseUrl + path);
    }

    protected List<Argument> toGerkinArguments(ArgumentDTO[] matchedArguments) {
        if (matchedArguments == null) return null;
        List<Argument> gherkinArgs = new ArrayList<Argument>();
        for (ArgumentDTO arg : matchedArguments) {
            gherkinArgs.add(arg.toArgument());
        }
        return gherkinArgs;
    }

    protected List<TagDTO> toTagDTOs(Collection<Tag> gherkinTags) {
        if (gherkinTags == null) return null;
        List<TagDTO> dtos = new ArrayList<TagDTO>();
        for (Tag gherkinTag : gherkinTags) {
            dtos.add(new TagDTO(gherkinTag));
        }
        return dtos;
    }
}
