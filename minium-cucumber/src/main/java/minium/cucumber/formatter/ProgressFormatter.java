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
package minium.cucumber.formatter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;
import minium.cucumber.data.ExecutionProgress;
import minium.cucumber.internal.CucumberContext;

public class ProgressFormatter implements Formatter, Reporter {

    private final File outputFile;
    private ObjectMapper mapper = new ObjectMapper();
    private ExecutionProgress progress = CucumberContext.getCurrent().getProgress();
    private boolean isTheFirstFeature = true;

    public ProgressFormatter(File outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public void feature(Feature feature) {
        if (isTheFirstFeature) {
            progress.startedNextProfile();
            isTheFirstFeature = false;
        }
        progress.startedFeature(feature);
    }

    @Override
    public void scenario(Scenario scenario) {
        progress.startedScenario(scenario);
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        try {
            progress.finishedScenario(scenario);
            FileUtils.writeStringToFile(outputFile, mapper.writeValueAsString(progress), Charsets.UTF_8);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public void close() {
    }

    @Override
    public void uri(String uri) {
    }

    @Override
    public void background(Background background) {
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {
    }

    @Override
    public void examples(Examples examples) {
    }

    @Override
    public void step(Step step) {
    }

    @Override
    public void eof() {
    }

    @Override
    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {
    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {
    }

    @Override
    public void done() {
    }

    @Override
    public void result(Result result) {
        if (result.getStatus().equals(Result.FAILED)) {
            progress.addFailedScenario();
        }
    }

    @Override
    public void before(Match match, Result result) {
    }

    @Override
    public void after(Match match, Result result) {
    }

    @Override
    public void match(Match match) {
    }

    @Override
    public void embedding(String mimeType, byte[] data) {
    }

    @Override
    public void write(String text) {
    }

}
