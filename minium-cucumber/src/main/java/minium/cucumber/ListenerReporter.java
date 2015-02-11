package minium.cucumber;

import static cucumber.runtime.Runtime.isPending;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.Match;
import gherkin.formatter.model.Result;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;

import java.util.List;

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
    }

    @Override
    public void examples(Examples examples) {
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
        System.out.println(">> [ Scenario Started ] " + scenario.getName());
        inScenarioLifeCycle = true;
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        System.out.println(">> [ Scenario Finished ] " + scenario.getName());
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
}
