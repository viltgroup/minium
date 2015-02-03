package minium.cucumber.rest;

import gherkin.I18n;
import gherkin.formatter.Argument;
import gherkin.formatter.model.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import minium.cucumber.rest.dto.ExecutionResult;
import minium.cucumber.rest.dto.GlueDTO;
import minium.cucumber.rest.dto.HookExecutionResult;
import minium.cucumber.rest.dto.ScenarioDTO;
import minium.cucumber.rest.dto.StepDTO;
import minium.cucumber.rest.dto.StepDefinitionInvocation;
import minium.cucumber.rest.dto.StepExecutionResult;
import minium.cucumber.rest.dto.StepMatchDTO;
import minium.cucumber.rest.dto.TagDTO;
import minium.cucumber.rest.dto.WorldDTO;
import cucumber.api.Scenario;
import cucumber.api.SnippetType;
import cucumber.runtime.Backend;
import cucumber.runtime.HookDefinition;
import cucumber.runtime.StepDefinition;
import cucumber.runtime.xstream.LocalizedXStreams;

public class BackendContext {

    private final String backendName;
    private final Backend backend;
    private final Map<UUID, SimpleGlue> glues = new HashMap<UUID, SimpleGlue>();
    final Map<UUID, WorldDTO> worlds = new HashMap<UUID, WorldDTO>();

    public BackendContext(String backendName, Backend backend) {
        this.backendName = backendName;
        this.backend = backend;
    }

    public List<GlueDTO> getGlues() {
        List<GlueDTO> glueDtos = new ArrayList<GlueDTO>();
        for (SimpleGlue glueProxy : glues.values()) {
            glueDtos.add(new GlueDTO(glueProxy));
        }
        return glueDtos;
    }

    public GlueDTO createGlue(String ... paths) {
        SimpleGlue glue = new SimpleGlue();
        backend.loadGlue(glue, Arrays.asList(paths));
        glues.put(glue.getUuid(), glue);
        return new GlueDTO(glue);
    }

    public void deleteGlue(UUID uuid) {
        SimpleGlue remoteGlue = glues.remove(uuid);
        if (remoteGlue == null) throw new ResourceNotFoundException(String.format("Glue %s not found", uuid));
    }

    public WorldDTO createWorld() {
        WorldDTO world = new WorldDTO(UUID.randomUUID(), backendName);
        backend.buildWorld();
        worlds.put(world.getUuid(), world);
        return world;
    }

    public void deleteWorld(UUID uuid) {
        Preconditions.checkState(worlds.containsKey(uuid), "No world exists with UUID %s", uuid);
        worlds.remove(uuid);
        backend.disposeWorld();
    }

    public HookExecutionResult execute(UUID uuid, long id, ScenarioDTO scenarioDto) throws Throwable {
        SimpleGlue glue = glues.get(uuid);
        Scenario scenarioAdapter = new ScenarioAdapter(scenarioDto);
        HookDefinition hookDefinition = glue.hookDefinition(id);
        try {
            hookDefinition.execute(scenarioAdapter);
        } catch (Exception e) {
            return new HookExecutionResult(scenarioDto, e);
        }
        return new HookExecutionResult(scenarioDto);
    }

    public boolean matches(UUID uuid, long id, Collection<TagDTO> tags) {
        SimpleGlue glue = glues.get(uuid);
        HookDefinition hookDefinition = glue.hookDefinition(id);
        return hookDefinition.matches(convertTags(tags));
    }

    public ExecutionResult execute(UUID uuid, long id, StepDefinitionInvocation stepDefinitionInvocation) throws Throwable {
        SimpleGlue glue = glues.get(uuid);
        StepDefinition stepDefinition = glue.stepDefinition(id);
        LocalizedXStreams xStreams = new LocalizedXStreams(Thread.currentThread().getContextClassLoader());
        try {
            String isoCode = stepDefinitionInvocation.getIsoCode();
            I18n i18n = new I18n(isoCode);
            stepDefinition.execute(i18n, stepDefinitionInvocation.getArgs(xStreams.get(i18n.getLocale()), stepDefinition));
        } catch (Exception e) {
            return new StepExecutionResult(e);
        }
        return new StepExecutionResult();
    }

    public StepMatchDTO matchedArguments(UUID uuid, long id, StepDTO stepProxy) throws Throwable {
        SimpleGlue glue = glues.get(uuid);
        StepDefinition stepDefinition = glue.stepDefinition(id);
        List<Argument> matchedArguments = stepDefinition.matchedArguments(stepProxy.toStep());
        return new StepMatchDTO(matchedArguments);
    }

    public String getSnippet(StepDTO serializableStep, SnippetType type) {
        return backend.getSnippet(serializableStep.toStep(), type.getFunctionNameGenerator());
    }

    public Collection<Tag> convertTags(Collection<TagDTO> tags) {
        if (tags == null) return null;
        List<Tag> gherkinTags = new ArrayList<Tag>();
        for (TagDTO tag : tags) {
            gherkinTags.add(tag.toTag());
        }
        return gherkinTags;
    }

}
