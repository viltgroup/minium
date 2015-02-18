package minium.actions.internal;

import java.util.concurrent.TimeUnit;

import platypus.Mixin;
import minium.Elements;
import minium.actions.WaitInteractable;

public class DefaultWaitInteractable extends Mixin.Impl implements WaitInteractable {

    private final WaitInterationPerformer performer;

    public DefaultWaitInteractable(WaitInterationPerformer performer) {
        this.performer = performer;
    }

    @Override
    public void waitForExistence() {
        waitForExistence(null);
    }

    @Override
    public void waitForExistence(String waitingPreset) {
        performer.waitForExistence(asElements(), waitingPreset);
    }

    @Override
    public void waitForUnexistence() {
        waitForUnexistence(null);
    }

    @Override
    public void waitForUnexistence(String waitingPreset) {
        performer.waitForUnexistence(asElements(), waitingPreset);
    }

    @Override
    public boolean checkForExistence() {
        return checkForExistence(null);
    }

    @Override
    public boolean checkForExistence(String waitingPreset) {
        return performer.checkForExistence(asElements(), waitingPreset);
    }

    @Override
    public boolean checkForUnexistence() {
        return checkForUnexistence(null);
    }

    @Override
    public boolean checkForUnexistence(String waitingPreset) {
        return performer.checkForUnexistence(asElements(), waitingPreset);
    }

    private Elements asElements() {
        return this.as(Elements.class);
    }

    @Override
    public void waitTime(long time, TimeUnit timeUnit) {
        performer.waitTime(time, timeUnit);
    }
}
