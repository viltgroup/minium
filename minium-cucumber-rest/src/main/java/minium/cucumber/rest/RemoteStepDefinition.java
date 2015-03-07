/*
 * Copyright (C) 2015 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minium.cucumber.rest;

import gherkin.I18n;
import gherkin.formatter.Argument;
import gherkin.formatter.model.Step;

import java.lang.reflect.Type;
import java.util.List;

import minium.cucumber.rest.dto.StepDefinitionDTO;
import cucumber.runtime.ParameterInfo;
import cucumber.runtime.StepDefinition;

public class RemoteStepDefinition implements StepDefinition {

    private transient RemoteBackend remoteBackend;
    private StepDefinitionDTO definitionDto;

    public RemoteStepDefinition(RemoteBackend remoteBackend, StepDefinitionDTO definitionDto) {
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

    @Override
    public boolean isScenarioScoped() {
        // Minium Cucumber REST does not support scenario scoped steps
        return false;
    }
}
