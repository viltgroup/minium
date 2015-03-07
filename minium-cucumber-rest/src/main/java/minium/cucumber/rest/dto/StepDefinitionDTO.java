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
package minium.cucumber.rest.dto;

import java.io.Serializable;
import java.util.UUID;

import cucumber.runtime.StepDefinition;

public class StepDefinitionDTO implements Serializable {

    private static final long serialVersionUID = 1195844972068596887L;

    private UUID glueId;
    private long id;
    private String pattern;
    private Integer parameterCount;
    private String location;
    private String detailedLocation;

    public StepDefinitionDTO() {
    }

    public StepDefinitionDTO(UUID glueId, long id, StepDefinition stepDefinition) {
        this.glueId = glueId;
        this.id = id;
        this.pattern = stepDefinition.getPattern();
        this.parameterCount = stepDefinition.getParameterCount();
        this.location = stepDefinition.getLocation(false);
        this.detailedLocation = stepDefinition.getLocation(true);
    }

    public UUID getGlueId() {
        return glueId;
    }

    public void setGlueId(UUID glueId) {
        this.glueId = glueId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Integer getParameterCount() {
        return parameterCount;
    }

    public void setParameterCount(Integer parameterCount) {
        this.parameterCount = parameterCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetailedLocation() {
        return detailedLocation;
    }

    public void setDetailedLocation(String detailedLocation) {
        this.detailedLocation = detailedLocation;
    }

}

