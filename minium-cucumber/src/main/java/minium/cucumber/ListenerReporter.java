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
package minium.cucumber;

import static cucumber.runtime.Runtime.isPending;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.ExamplesTableRow;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.runner.notification.RunNotifier;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import cucumber.api.PendingException;
import cucumber.runtime.junit.ExecutionUnitRunner;

public class ListenerReporter implements Reporter, Formatter {

    private final List<StepListener> stepListeners = Lists.newArrayList();

    private final List<Step> steps = Lists.newArrayList();

    private final boolean strict;

    private Step matchedStep;
    private boolean ignoredStep;
    private boolean inScenarioLifeCycle;
    private boolean inScenarioOutilne;
    private Queue<Object> exampleOfScenarioOutline;
    private int lastExampleLine;
    private boolean lastExample;

    public ListenerReporter() {
        this(true);
    }

    public ListenerReporter add(StepListener ... listeners) {
        stepListeners.addAll(ImmutableList.copyOf(listeners));
        return this;
    }

    public ListenerReporter(boolean strict) {
        this.strict = strict;
    }

    public void startExecutionUnit(ExecutionUnitRunner executionUnitRunner, RunNotifier runNotifier) {
        this.ignoredStep = false;
        this.inScenarioOutilne = false;
    }

    public void finishExecutionUnit() {
        if (ignoredStep) {
            fireTestIgnored();
        }
        fireTestFinished(null);
    }

    @Override
    public void match(Match match) {
        matchedStep = steps.remove(0);
        fireMatch();
    }

    @Override
    public void embedding(String mimeType, byte[] data) {
    }

    @Override
    public void write(String text) {
    }

    @Override
    public void result(Result result) {
        Throwable error = result.getError();
        if (Result.SKIPPED == result) {
            fireTestIgnored();
        } else if (isPendingOrUndefined(result)) {
            addFailureOrIgnoreStep(result);
        } else {
            if (matchedStep != null) {
                fireTestStarted();
                if (error != null) {
                    fireFailure(result);
                }
                fireTestFinished(result);
            }
            if (error != null) {
                fireFailure(result);
            }
        }
        if (steps.isEmpty()) {
            // We have run all of our steps. Set the stepNotifier to null so that
            // if an error occurs in an After block, it's reported against the scenario
            // instead (via executionUnitNotifier).
            matchedStep = null;
        }

        // when the last example step got the result
        // the flag inScenarioOutilne is already false
        // so we need create this other flag
        if ((inScenarioOutilne || lastExample) && error != null) {
           int line = lastExampleLine;
           fireTestFailedExample(line);
        }
    }

    private boolean isPendingOrUndefined(Result result) {
        Throwable error = result.getError();
        return Result.UNDEFINED == result || isPending(error);
    }

    private void addFailureOrIgnoreStep(Result result) {
        if (strict) {
            fireTestStarted();
            fireFailure(result);
            fireTestFinished(result);
        } else {
            ignoredStep = true;
            fireTestIgnored();
        }
    }

    @Override
    public void before(Match match, Result result) {
        handleHook(result);
    }

    @Override
    public void after(Match match, Result result) {
        handleHook(result);
    }

    private void handleHook(Result result) {
        if (result.getStatus().equals(Result.FAILED)) {
            fireFailure(result);
        }
    }

    @Override
    public void uri(String uri) {
    }

    @Override
    public void feature(gherkin.formatter.model.Feature feature) {
    }

    @Override
    public void background(Background background) {
    }

    @Override
    public void scenario(Scenario scenario) {
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {
       inScenarioOutilne = true;
       lastExample = false;
    }

    @Override
    public void examples(Examples examples) {
       exampleOfScenarioOutline = new LinkedList<Object>();
       for (ExamplesTableRow example: examples.getRows()) {
          exampleOfScenarioOutline.add(example.getLine());
       }
       // remove the first because its the header
       exampleOfScenarioOutline.remove();
    }

    @Override
    public void step(Step step) {
        if (inScenarioLifeCycle) {
            steps.add(step);
        }
    }

    @Override
    public void eof() {
    }

    @Override
    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {
    }

    @Override
    public void done() {
    }

    @Override
    public void close() {
    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {
        inScenarioLifeCycle = true;
        if (inScenarioOutilne) {
           int line = (int) exampleOfScenarioOutline.element();
           fireTestStartedExample(line);
           lastExampleLine = line;
          exampleOfScenarioOutline.remove();
          if (exampleOfScenarioOutline.isEmpty()) {
             inScenarioOutilne = false;
             lastExample = true;
          }
       }
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        inScenarioLifeCycle = false;
    }

    private void fireFailure(Result result) {
        Throwable error = result.getError();
        if (error == null) {
            error = new PendingException();
        }
        for (StepListener stepListener : stepListeners) {
            stepListener.failedStep(matchedStep, error);
        }
    }

    private void fireMatch() {
        Preconditions.checkNotNull(matchedStep);
        for (StepListener stepListener : stepListeners) {
            stepListener.beforeStep(matchedStep);
        }
    }

    protected void fireTestStarted() {
    }

    protected void fireTestFinished(Result result) {
        if (result == null) return;
        Preconditions.checkNotNull(matchedStep);
        for (StepListener stepListener : stepListeners) {
            stepListener.afterStep(matchedStep, result);
        }
    }

    protected void fireTestIgnored() {
        Preconditions.checkNotNull(matchedStep);
        for (StepListener stepListener : stepListeners) {
            stepListener.ignoredStep(matchedStep);
        }
    }

    protected void fireTestStartedExample(int line) {
        for (StepListener stepListener : stepListeners) {
            stepListener.exampleStep(line);
        }
    }

    protected void fireTestFailedExample(int line) {
        for (StepListener stepListener : stepListeners) {
            stepListener.failedExampleStep(line);
        }
    }
}
