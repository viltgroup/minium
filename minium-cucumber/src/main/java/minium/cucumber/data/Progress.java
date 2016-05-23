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

public class Progress {

    private List<String> profiles;
    private Float progressInPercentage = new Float(0f);
    private Integer numberOfProfiles;
    private Integer numberOfExecutedProfiles = new Integer(-1);
    private Feature currentFeature;
    private Integer numberOfFeatures;
    private Integer numberOfExecutedFeatures;
    private Scenario currentScenario;
    private Integer numberOfScenarios;
    private Integer numberOfExecutedScenarios;

    public Progress(List<String> profiles, int numberOfFeatures, int numberOfScenarios) {
        this.profiles = profiles;
        this.numberOfProfiles = profiles.size();
        this.numberOfFeatures = numberOfFeatures;
        this.numberOfScenarios = numberOfScenarios;
    }

    private void updatePercentageOfProgress() {
        float totalNumberOfExecutedScenarios = numberOfExecutedProfiles * numberOfScenarios + numberOfExecutedScenarios,
                racio = totalNumberOfExecutedScenarios / (numberOfProfiles * numberOfScenarios);
        progressInPercentage = racio * 100f;
    }

    public void startedNextProfile() {
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

    public String getCurrentProfile() {
        return profiles.get(numberOfExecutedProfiles);
    }

    public Feature getCurrentFeature() {
        return currentFeature;
    }

    public Scenario getCurrentScenario() {
        return currentScenario;
    }

    public Float getProgressInPercentage() {
        return progressInPercentage;
    }

    public Integer getNumberOfProfiles() {
        return numberOfProfiles;
    }

    public Integer getNumberOfExecutedProfiles() {
        return numberOfExecutedProfiles;
    }

    public Integer getNumberOfFeatures() {
        return numberOfFeatures;
    }

    public Integer getNumberOfExecutedFeatures() {
        return numberOfExecutedFeatures;
    }

    public Integer getNumberOfScenarios() {
        return numberOfScenarios;
    }

    public Integer getNumberOfExecutedScenarios() {
        return numberOfExecutedScenarios;
    }
}