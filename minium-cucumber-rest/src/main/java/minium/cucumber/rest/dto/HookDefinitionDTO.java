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

import cucumber.runtime.HookDefinition;

public class HookDefinitionDTO implements Serializable {

    private static final long serialVersionUID = 116997860196255407L;

    private UUID glueId;
    private long id;
    private int order;
    private String location;
    private String detailedLocation;

    public HookDefinitionDTO() {
    }

    public HookDefinitionDTO(UUID glueId, long id, HookDefinition hookDefinition) {
        this.glueId = glueId;
        this.id = id;
        this.order = hookDefinition.getOrder();
        this.location = hookDefinition.getLocation(false);
        this.detailedLocation = hookDefinition.getLocation(true);
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HookDefinitionDTO) {
            HookDefinitionDTO other = (HookDefinitionDTO) obj;
            return id == other.id;
        }
        return false;
    }
}
