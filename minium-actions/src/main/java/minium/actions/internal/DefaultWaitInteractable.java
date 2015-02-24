package minium.actions.internal;

import java.util.concurrent.TimeUnit;

import minium.ElementsException;
import minium.actions.Duration;
import minium.actions.Interactable;
import minium.actions.WaitInteractable;

public class DefaultWaitInteractable<T extends Interactable<?>> extends AbstractInteractable<T> implements WaitInteractable<T> {

    public DefaultWaitInteractable() {
    }

    @Override
    public T waitForExistence() throws ElementsException {
        return waitForExistence(null);
    }

    @Override
    public T waitForExistence(String waitingPreset) {
        return perform(new WaitForExistenceInteraction(getSource(), waitingPreset));
    }

    @Override
    public T waitForUnexistence() throws ElementsException {
        return waitForUnexistence(null);
    }

    @Override
    public T waitForUnexistence(String waitingPreset) throws ElementsException {
        return perform(new WaitForUnexistenceInteraction(getSource(), waitingPreset));
    }

    @Override
    public boolean checkForExistence() {
        return checkForExistence(null);
    }

    @Override
    public boolean checkForExistence(String waitingPreset) {
        CheckForExistenceInteraction interaction = new CheckForExistenceInteraction(getSource(), waitingPreset);
        perform(interaction);
        return !interaction.isEmpty();
    }

    @Override
    public boolean checkForUnexistence() {
        return checkForUnexistence(null);
    }

    @Override
    public boolean checkForUnexistence(String waitingPreset) {
        CheckForUnexistenceInteraction interaction = new CheckForUnexistenceInteraction(getSource(), waitingPreset);
        perform(interaction);
        return interaction.isEmpty();
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#waitTime(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public T waitTime(long time, TimeUnit unit) {
        return perform(new WaitTimeInteraction(new Duration(time, unit)));
    }
}
