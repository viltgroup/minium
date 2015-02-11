package minium.cucumber;

import gherkin.formatter.model.Result;
import gherkin.formatter.model.Step;

public interface StepListener {

    public void beforeStep(Step step);

    public void afterStep(Step step, Result result);

    public void ignoredStep(Step step);

    public void failedStep(Step step, Throwable error);
}
