package cucumber.runtime.remote;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;
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
import cucumber.runtime.snippets.FunctionNameGenerator;

public class RemoteBackend implements Backend {

    private static final String FUNCTION_NAME = "function name";
    private static final Map<String, SnippetType> snippetTypes = new HashMap<String, SnippetType>();

    static {
        snippetTypes.put(SnippetType.CAMELCASE.getFunctionNameGenerator().generateFunctionName(FUNCTION_NAME), SnippetType.CAMELCASE);
        snippetTypes.put(SnippetType.UNDERSCORE.getFunctionNameGenerator().generateFunctionName(FUNCTION_NAME), SnippetType.UNDERSCORE);
    }

    private final String baseUrl;
    private final RestTemplate template;
    private WorldDTO world;

    private UriComponentsBuilder gluesUriBuilder;
    private UriComponentsBuilder worldsUriBuilder;
    private UriComponentsBuilder worldUriBuilder;
    private UriComponentsBuilder hookExecUriBuilder;
    private UriComponentsBuilder stepExecUriBuilder;
    private UriComponentsBuilder stepMatchedUriBuilder;

    public RemoteBackend(String baseUrl) {
        this(baseUrl, new RestTemplate());
    }

    public RemoteBackend(String baseUrl, RestTemplate template) {
        this.baseUrl = baseUrl;
        this.template = template;

        gluesUriBuilder       = fromHttpUrl(baseUrl).pathSegment("glues");
        worldsUriBuilder      = fromHttpUrl(baseUrl).pathSegment("worlds");
        worldUriBuilder       = fromHttpUrl(baseUrl).pathSegment("worlds/{uuid}");
        hookExecUriBuilder    = fromHttpUrl(baseUrl).pathSegment("glues/{uuid}/hookDefinitions/{id}/execution");
        stepExecUriBuilder    = fromHttpUrl(baseUrl).pathSegment("glues/{uuid}/stepDefinitions/{id}/execution");
        stepMatchedUriBuilder = fromHttpUrl(baseUrl).pathSegment("glues/{uuid}/stepDefinitions/{id}/matchedArguments");
    }

    public RestTemplate getTemplate() {
        return template;
    }

    @Override
    public void loadGlue(Glue glue, List<String> gluePaths) {
        URI uri = gluesUriBuilder.queryParam("path", gluePaths.toArray()).build().toUri();
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
        URI uri = worldsUriBuilder.build().toUri();
        world = template.postForObject(uri, null, WorldDTO.class);
    }

    @Override
    public void disposeWorld() {
        Preconditions.checkState(world != null, "No world exists");
        URI uri = worldUriBuilder.buildAndExpand(world.getUuid()).toUri();
        template.delete(uri);
    }

    protected void execute(HookDefinitionProxy hookDefinition, Scenario scenario) {
        ScenarioDTO remoteScenario = new ScenarioDTO(scenario);
        URI uri = hookExecUriBuilder.buildAndExpand(hookDefinition.getGlueId(), hookDefinition.getId()).toUri();
        HookExecutionResult execution = template.postForObject(uri, remoteScenario, HookExecutionResult.class);
        remoteScenario.populate(execution.getScenario());
    }

    public void execute(StepDefinitionDTO stepDefinition, I18n i18n, Object[] args) {
        StepDefinitionInvocation stepDefinitionInvocation = new StepDefinitionInvocation(i18n, args);
        URI uri = stepExecUriBuilder.buildAndExpand(stepDefinition.getGlueId(), stepDefinition.getId()).toUri();
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
        URI uri = stepMatchedUriBuilder.buildAndExpand(stepDefinition.getGlueId(), stepDefinition.getId()).toUri();
        ArgumentDTO[] matchedArguments = template.postForObject(uri, stepProxy, ArgumentDTO[].class);
        return toGerkinArguments(matchedArguments);
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
