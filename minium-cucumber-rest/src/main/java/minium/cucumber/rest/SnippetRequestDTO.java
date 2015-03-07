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

import minium.cucumber.rest.dto.StepDTO;
import cucumber.api.SnippetType;

public class SnippetRequestDTO {

    private StepDTO step;
    private SnippetType type;

    public SnippetRequestDTO() {
    }

    public SnippetRequestDTO(StepDTO step, SnippetType snippetType) {
        this.step = step;
        type = snippetType;
    }

    public StepDTO getStep() {
        return step;
    }

    public void setStep(StepDTO step) {
        this.step = step;
    }

    public SnippetType getType() {
        return type;
    }

    public void setType(SnippetType type) {
        this.type = type;
    }
}
