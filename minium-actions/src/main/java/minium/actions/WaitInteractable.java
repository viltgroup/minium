package minium.actions;

import java.util.concurrent.TimeUnit;

public interface WaitInteractable<T extends Interactable<?>> extends Interactable<T> {

    public T waitForExistence();

    public T waitForExistence(String waitingPreset);

    public T waitForUnexistence();

    public T waitForUnexistence(String waitingPreset);

    public boolean checkForExistence();

    public boolean checkForExistence(String waitingPreset);

    public boolean checkForUnexistence();

    public boolean checkForUnexistence(String waitingPreset);

    public T waitTime(long time, TimeUnit timeUnit);

}