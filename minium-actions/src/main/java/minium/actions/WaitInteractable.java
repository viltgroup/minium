package minium.actions;

import java.util.concurrent.TimeUnit;

public interface WaitInteractable {

    public abstract void waitForExistence();

    public abstract void waitForExistence(String waitingPreset);

    public abstract void waitForUnexistence();

    public abstract void waitForUnexistence(String waitingPreset);

    public abstract boolean checkForExistence();

    public abstract boolean checkForExistence(String waitingPreset);

    public abstract boolean checkForUnexistence();

    public abstract boolean checkForUnexistence(String waitingPreset);

    public void waitTime(long time, TimeUnit timeUnit);

}