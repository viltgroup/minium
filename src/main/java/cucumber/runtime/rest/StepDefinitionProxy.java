package cucumber.runtime.rest;

import gherkin.I18n;
import gherkin.formatter.Argument;
import gherkin.formatter.model.Step;

import java.lang.reflect.Type;
import java.util.List;

import cucumber.runtime.ParameterInfo;
import cucumber.runtime.StepDefinition;
import cucumber.runtime.rest.dto.StepDefinitionDTO;

public class StepDefinitionProxy implements StepDefinition {

    private transient RemoteBackend remoteBackend;
    private StepDefinitionDTO definitionDto;

    public StepDefinitionProxy(RemoteBackend remoteBackend, StepDefinitionDTO definitionDto) {
        this.remoteBackend = remoteBackend;
        this.definitionDto = definitionDto;
    }

    @Override
    public List<Argument> matchedArguments(Step step) {
        return remoteBackend.matchedArguments(definitionDto, step);
    }

    @Override
    public String getLocation(boolean detail) {
        return detail ? definitionDto.getDetailedLocation() : definitionDto.getLocation();
    }

    @Override
    public Integer getParameterCount() {
        return definitionDto.getParameterCount();
    }

    @Override
    public ParameterInfo getParameterType(int n, Type argumentType) throws IndexOutOfBoundsException {
        // this logic will happen on the server side, here is ok to just return null and
        // not infer parameter types
        return null;
    }

    @Override
    public void execute(I18n i18n, Object[] args) throws Throwable {
        remoteBackend.execute(definitionDto, i18n, args);
    }

    @Override
    public boolean isDefinedAt(StackTraceElement stackTraceElement) {
        return false;
    }

    @Override
    public String getPattern() {
        return definitionDto.getPattern();
    }
}
