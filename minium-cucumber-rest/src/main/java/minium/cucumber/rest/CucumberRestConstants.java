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

public class CucumberRestConstants {

    public static final String URL_PREFIX        = "/cucumber";

    public static final String BACKEND_PREFIX    = "/backends/{backendId}";

    public static final String GLUES_URI          = "/glues";
    public static final String GLUE_URI           = "/glues/{uuid}";
    public static final String WORLDS_URI         = "/worlds";
    public static final String WORLD_URI          = "/worlds/{uuid}";
    public static final String HOOK_EXEC_URI      = "/glues/{uuid}/hookDefinitions/{id}/execution";
    public static final String HOOK_TAG_MATCH_URI = "/glues/{uuid}/hookDefinitions/{id}/matches";
    public static final String STEP_EXEC_URI      = "/glues/{uuid}/stepDefinitions/{id}/execution";
    public static final String STEP_MATCHED_URI   = "/glues/{uuid}/stepDefinitions/{id}/matchedArguments";
    public static final String SNIPPET_URI        = "/snippet";
}
