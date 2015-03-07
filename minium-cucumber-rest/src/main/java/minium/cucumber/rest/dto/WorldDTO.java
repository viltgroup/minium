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

public class WorldDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID uuid;
    private String backendName;

    public WorldDTO() {
    }

    public WorldDTO(UUID uuid, String backendName) {
        this.uuid = uuid;
        this.backendName = backendName;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getBackendName() {
        return backendName;
    }

    public void setBackendName(String backendName) {
        this.backendName = backendName;
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
}
