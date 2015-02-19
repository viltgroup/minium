package minium.actions.internal;

import java.util.concurrent.TimeUnit;

import minium.Elements;
import minium.ElementsException;

public interface WaitInterationPerformer {

    public abstract void waitForExistence(Elements elems);

    public abstract void waitForExistence(Elements elems, String waitingPreset);

    public abstract void waitForUnexistence(Elements elems) throws ElementsException;

    public abstract void waitForUnexistence(Elements elems, String waitingPreset);

    public abstract boolean checkForExistence(Elements elems);

    public abstract boolean checkForExistence(Elements elems, String waitingPreset);

    public abstract boolean checkForUnexistence(Elements elems);

    public abstract boolean checkForUnexistence(Elements elems, String waitingPreset);

    public abstract void waitTime(long time, TimeUnit unit);

}