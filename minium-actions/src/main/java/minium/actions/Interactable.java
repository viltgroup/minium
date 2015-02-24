package minium.actions;

import minium.AsIs;

public interface Interactable<T extends Interactable<?>> extends AsIs {

    /**
     * Perform.
     *
     * @param interaction the interaction
     */
    public abstract T perform(Interaction interaction);

    /**
     * Perform.
     *
     * @param interaction the interaction
     */
    public abstract T performAndWait(AsyncInteraction interaction);
}
