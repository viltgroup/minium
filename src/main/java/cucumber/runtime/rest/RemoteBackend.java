package cucumber.runtime.rest;

import static cucumber.runtime.rest.CucumberRestConstants.GLUES_URI;
import static cucumber.runtime.rest.CucumberRestConstants.HOOK_EXEC_URI;
import static cucumber.runtime.rest.CucumberRestConstants.STEP_EXEC_URI;
import static cucumber.runtime.rest.CucumberRestConstants.STEP_MATCHED_URI;
import static cucumber.runtime.rest.CucumberRestConstants.WORLDS_URI;
import static cucumber.runtime.rest.CucumberRestConstants.WORLD_URI;
import gherkin.I18n;
import gherkin.formatter.Argument;
import gherkin.formatter.model.Step;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cucumber.api.Scenario;
import cucumber.api.SnippetType;
import cucumber.runtime.Backend;
import cucumber.runtime.Glue;
import cucumber.runtime.HookDefinition;
import cucumber.runtime.StepDefinition;
import cucumber.runtime.UnreportedStepExecutor;
import cucumber.runtime.rest.dto.ArgumentDTO;
import cucumber.runtime.rest.dto.ScenarioDTO;
import cucumber.runtime.rest.dto.StepDTO;
import cucumber.runtime.rest.dto.StepDefinitionDTO;
import cucumber.runtime.rest.dto.WorldDTO;
import cucumber.runtime.snippets.FunctionNameGenerator;

public class RemoteBackend implements Backend {

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
        GlueProxy remoteGlue = template.postForObject(uri, null, GlueProxy.class);

        for (HookDefinition hookDefinition : remoteGlue.getBeforeHooks()) {
            ((HookDefinitionProxy) hookDefinition).setRemoteBackend(this);
            glue.addBeforeHook(hookDefinition);
        }
        for (HookDefinition hookDefinition : remoteGlue.getAfterHooks()) {
            ((HookDefinitionProxy) hookDefinition).setRemoteBackend(this);
            glue.addAfterHook(hookDefinition);
        }
        for (StepDefinition stepDefinition : remoteGlue.getStepDefinitions()) {
            ((StepDefinitionDTO) stepDefinition).setRemoteBackend(this);
            glue.addStepDefinition(stepDefinition);
        }
    }

    @Override
    public void setUnreportedStepExecutor(UnreportedStepExecutor executor) {
    }

    @Override
    public void buildWorld() {
        Preconditions.checkState(world == null, "A world already exists");
        URI uri = uriBuilderFor(WORLDS_URI).buildAndExpand(backendId).toUri();
        world = template.postForObject(uri, null, WorldDTO.class);
    }

    @Override
    public void disposeWorld() {
        Preconditions.checkState(world != null, "No world exists");
        URI uri = uriBuilderFor(WORLD_URI).buildAndExpand(backendId, world.getUuid()).toUri();
        template.delete(uri);
    }

    protected void execute(HookDefinitionProxy hookDefinition, Scenario scenario) {
        ScenarioDTO remoteScenario = new ScenarioDTO(scenario);
        URI uri = uriBuilderFor(HOOK_EXEC_URI).buildAndExpand(backendId, hookDefinition.getGlueId(), hookDefinition.getId()).toUri();
        HookExecutionResult execution = template.postForObject(uri, remoteScenario, HookExecutionResult.class);
        remoteScenario.populate(execution.getScenario());
    }

    public void execute(StepDefinitionDTO stepDefinition, I18n i18n, Object[] args) {
        StepDefinitionInvocation stepDefinitionInvocation = new StepDefinitionInvocation(i18n, args);
        URI uri = uriBuilderFor(STEP_EXEC_URI).buildAndExpand(backendId, stepDefinition.getGlueId(), stepDefinition.getId()).toUri();
        template.postForObject(uri, stepDefinitionInvocation, StepExecutionResult.class);
    }

    @Override
    public String getSnippet(Step step, FunctionNameGenerator nameGenerator) {
        SnippetType snippetType = getSnippetType(nameGenerator);
        return template.getForObject(baseUrl + "/snippet", String.class, Collections.singletonMap("type", snippetType.name()));
    }

    protected SnippetType getSnippetType(FunctionNameGenerator nameGenerator) {
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

    private UriComponentsBuilder uriBuilderFor(String path) {
        return UriComponentsBuilder.fromHttpUrl(baseUrl + path);
    }

    private List<Argument> toGerkinArguments(ArgumentDTO[] matchedArguments) {
        if (matchedArguments == null) return null;
        List<Argument> gherkinArgs = new ArrayList<Argument>();
        for (ArgumentDTO arg : matchedArguments) {
            gherkinArgs.add(arg.toArgument());
        }
        return gherkinArgs;
    }
}
