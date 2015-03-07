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
import gherkin.formatter.model.Step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import minium.cucumber.rest.dto.GlueDTO;
import cucumber.api.StepDefinitionReporter;
import cucumber.runtime.DuplicateStepDefinitionException;
import cucumber.runtime.Glue;
import cucumber.runtime.HookDefinition;
import cucumber.runtime.StepDefinition;
import cucumber.runtime.StepDefinitionMatch;

public class SimpleGlue implements Glue {

    static AtomicLong HOOK_DEF_ID_GENERATOR = new AtomicLong();
    static AtomicLong STEP_DEF_ID_GENERATOR = new AtomicLong();

    private UUID uuid;
    private Map<Long, StepDefinition> stepDefinitions = new HashMap<Long, StepDefinition>();
    private Map<Long, HookDefinition> hookDefinitions = new HashMap<Long, HookDefinition>();
    private Map<Long, HookDefinition> beforeHookDefinitions = new HashMap<Long, HookDefinition>();
    private Map<Long, HookDefinition> afterHookDefinitions  = new HashMap<Long, HookDefinition>();

    public SimpleGlue() {
        uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void addStepDefinition(StepDefinition stepDefinition) throws DuplicateStepDefinitionException {
        long id = STEP_DEF_ID_GENERATOR.incrementAndGet();
        stepDefinitions.put(id, stepDefinition);
    }

    @Override
    public void addBeforeHook(HookDefinition hookDefinition) {
        long id = HOOK_DEF_ID_GENERATOR.incrementAndGet();
        hookDefinitions.put(id, hookDefinition);
        beforeHookDefinitions.put(id, hookDefinition);
    }

    @Override
    public void addAfterHook(HookDefinition hookDefinition) {
        long id = HOOK_DEF_ID_GENERATOR.incrementAndGet();
        hookDefinitions.put(id, hookDefinition);
        afterHookDefinitions.put(id, hookDefinition);
    }

    @Override
    public List<HookDefinition> getBeforeHooks() {
        List<HookDefinition> beforeHooks = new ArrayList<HookDefinition>();
        beforeHooks.addAll(beforeHookDefinitions.values());
        return beforeHooks;
    }

    @Override
    public List<HookDefinition> getAfterHooks() {
        List<HookDefinition> afterHooks = new ArrayList<HookDefinition>();
        afterHooks.addAll(afterHookDefinitions.values());
        return afterHooks;
    }

    @Override
    public int hashCode() {
        return uuid == null ? System.identityHashCode(this) : uuid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GlueDTO && uuid != null) {
            return uuid.equals(((GlueDTO) obj).getUuid());
        }
        return false;
    }

    @Override
    public StepDefinitionMatch stepDefinitionMatch(String featurePath, Step step, I18n i18n) {
        throw new UnsupportedOperationException("stepDefinitionMatch is not supported in GlueProxy");
    }

    @Override
    public void reportStepDefinitions(StepDefinitionReporter stepDefinitionReporter) {
        for (StepDefinition stepDefinition : stepDefinitions.values()) {
            stepDefinitionReporter.stepDefinition(stepDefinition);
        }
    }

    public StepDefinition stepDefinition(Long id) {
        return stepDefinitions.get(id);
    }

    public HookDefinition hookDefinition(Long id) {
        return hookDefinitions.get(id);
    }

    public Map<Long, StepDefinition> getStepDefinitions() {
        return stepDefinitions;
    }

    public Map<Long, HookDefinition> getBeforeHookDefinitions() {
        return beforeHookDefinitions;
    }

    public Map<Long, HookDefinition> getAfterHookDefinitions() {
        return afterHookDefinitions;
    }

    @Override
    public void removeScenarioScopedGlue() {
        // Cucumber REST does not support scenario scoped glue
    }
}
