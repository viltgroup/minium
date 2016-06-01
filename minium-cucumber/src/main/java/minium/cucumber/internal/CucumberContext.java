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
/*
 * Copyright (c) 2008-2014 The Cucumber Organisation
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package minium.cucumber.internal;

import java.util.List;

import com.google.inject.internal.Lists;

import cucumber.runtime.model.CucumberExamples;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.model.CucumberScenario;
import cucumber.runtime.model.CucumberScenarioOutline;
import cucumber.runtime.model.CucumberTagStatement;
import minium.cucumber.data.ExecutionProgress;

public class CucumberContext {
    private static ThreadLocal<CucumberContext> cucumberContext = new InheritableThreadLocal<CucumberContext>() {
        @Override
        protected CucumberContext initialValue() {
            return new CucumberContext();
        }
    };

    public static CucumberContext getCurrent() {
        return cucumberContext.get();
    }

    public static void clear() {
        cucumberContext.set(new CucumberContext());
    }

    private ExecutionProgress progress = new ExecutionProgress();

    public CucumberContext() {
    }

    public ExecutionProgress getProgress() {
        return progress;
    }

    public static void setFeatures(List<CucumberFeature> cucumberFeatures) {
        int numberOfScenarios = 0;
        for (CucumberFeature feature : cucumberFeatures) {
            for (CucumberTagStatement scenario : feature.getFeatureElements()) {
                if (scenario instanceof CucumberScenario) {
                    numberOfScenarios++;
                } else if (scenario instanceof CucumberScenarioOutline) {
                    for (CucumberExamples examples : ((CucumberScenarioOutline) scenario).getCucumberExamplesList()) {
                        numberOfScenarios += examples.getExamples().getRows().size() - 1;
                    }
                }
            }
        }
        ExecutionProgress currentContextProgress = getCurrent().getProgress();
        currentContextProgress.setNumberOfFeatures(cucumberFeatures.size());
        currentContextProgress.setNumberOfScenarios(numberOfScenarios);
    }

    public static void setProfilesMatrix(List<String[]> profilesMatrix) {
        List<String> profiles = Lists.newArrayList();
        for (String[] profileMatrixLine : profilesMatrix) {
            profiles.add(profileMatrixLine[0]);
        }
        getCurrent().getProgress().setProfiles(profiles);
    }
}
