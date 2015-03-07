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

import java.util.ArrayList;
import java.util.List;

import cucumber.api.Scenario;

public class ScenarioDTO {

    public static class Data {

        private byte[] data;
        private String mimeType;

        public Data(byte[] data, String mimeType) {
            this.data = data;
            this.mimeType = mimeType;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }
    }

    private String id;
    private String name;
    private List<String> sourceTagNames;
    private String status;
    private boolean failed;
    private List<Data> embedded = new ArrayList<Data>();
    private List<String> texts = new ArrayList<String>();

    public ScenarioDTO() {
    }

    public ScenarioDTO(Scenario scenario) {
        status = scenario.getStatus();
        sourceTagNames = new ArrayList<String>(scenario.getSourceTagNames());
        failed = scenario.isFailed();
        name = scenario.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSourceTagNames() {
        return sourceTagNames;
    }

    public void setSourceTagNames(List<String> sourceTagNames) {
        this.sourceTagNames = sourceTagNames;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public List<Data> getEmbedded() {
        return embedded;
    }

    public void setEmbedded(List<Data> embedded) {
        this.embedded = embedded;
    }

    public List<String> getTexts() {
        return texts;
    }

    public void setTexts(List<String> texts) {
        this.texts = texts;
    }

    public void populate(Scenario scenario) {
        for (Data data : embedded) {
            scenario.embed(data.data, data.mimeType);
        }
        for (String text : texts) {
            scenario.write(text);
        }
    }
}
