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
package minium.cucumber.internal;

import org.springframework.test.context.ActiveProfilesResolver;

public class MiniumActiveProfilesResolver implements ActiveProfilesResolver {

    private static String[] activeProfiles;

    @Override
    public String[] resolve(Class<?> testClass) {
        return activeProfiles == null ? new String[0] : activeProfiles;
    }

    public static void setActiveProfiles(String ... activeProfiles) {
        MiniumActiveProfilesResolver.activeProfiles = activeProfiles;
    }
}
