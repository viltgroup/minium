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

import gherkin.formatter.Argument;

import java.util.List;

public class StepMatchDTO {

    private ArgumentDTO[] arguments;

    public StepMatchDTO() {
    }

    public StepMatchDTO(List<Argument> matchedArguments) {
        this.arguments = convertArguments(matchedArguments);
    }

    public ArgumentDTO[] getArguments() {
        return arguments;
    }

    public void setArguments(ArgumentDTO[] arguments) {
        this.arguments = arguments;
    }

    public ArgumentDTO[] convertArguments(List<Argument> args) {
        if (args == null) return null;
        ArgumentDTO[] dtos = new ArgumentDTO[args.size()];
        for (int i = 0; i < args.size(); i++) {
            Argument arg = args.get(i);
            dtos[i] = new ArgumentDTO(arg);
        }
        return dtos;
    }
}
