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
package minium.cucumber.data;

import java.util.List;

import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Scenario;

public class ExecutionProgress {

    private List<String> profiles;
    private float progressInPercentage;
    private int numberOfProfiles;
    private int numberOfExecutedProfiles = -1;
    private Feature currentFeature;
    private int numberOfFeatures;
    private int numberOfExecutedFeatures;
    private Scenario currentScenario;
    private int numberOfScenarios;
    private int numberOfExecutedScenarios;
    private long startTimestamp;

    public ExecutionProgress() {
    }

    private void updatePercentageOfProgress() {
        float totalNumberOfExecutedScenarios = numberOfExecutedProfiles * numberOfScenarios + numberOfExecutedScenarios,
                racio = totalNumberOfExecutedScenarios / (numberOfProfiles * numberOfScenarios);
        progressInPercentage = racio * 100f;
    }

    public void startedNextProfile() {
        if (numberOfExecutedProfiles == -1) {
            startTimestamp = System.currentTimeMillis();
        }
        numberOfExecutedProfiles++;
        numberOfExecutedFeatures = -1;
        numberOfExecutedScenarios = 0;
    }
    
    public void startedFeature(Feature feature) {
        currentFeature = feature;
        numberOfExecutedFeatures++;
    }

    public void startedScenario(Scenario scenario) {
        currentScenario = scenario;
    }
    
    public void finishedScenario(Scenario scenario) {
        numberOfExecutedScenarios++;
        updatePercentageOfProgress();
    }

    public void setProfiles(List<String> profiles) {
        this.profiles = profiles;
        this.numberOfProfiles = profiles.size();
    }

    public void setNumberOfFeatures(int numberOfFeatures) {
        this.numberOfFeatures = numberOfFeatures;
    }

    public void setNumberOfScenarios(int numberOfScenarios) {
        this.numberOfScenarios = numberOfScenarios;
    }

    public String getCurrentProfile() {
        return profiles.get(numberOfExecutedProfiles);
    }

    public Feature getCurrentFeature() {
        return currentFeature;
    }

    public Scenario getCurrentScenario() {
        return currentScenario;
    }

    public float getProgressInPercentage() {
        return progressInPercentage;
    }

    public int getNumberOfProfiles() {
        return numberOfProfiles;
    }

    public int getNumberOfExecutedProfiles() {
        return numberOfExecutedProfiles;
    }

    public int getNumberOfFeatures() {
        return numberOfFeatures;
    }

    public int getNumberOfExecutedFeatures() {
        return numberOfExecutedFeatures;
    }

    public int getNumberOfScenarios() {
        return numberOfScenarios;
    }

    public int getNumberOfExecutedScenarios() {
        return numberOfExecutedScenarios;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }
}