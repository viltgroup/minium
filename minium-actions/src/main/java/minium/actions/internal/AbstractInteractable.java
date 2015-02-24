package minium.actions.internal;

import java.util.Set;

import minium.Elements;
import minium.actions.AsyncInteraction;
import minium.actions.HasInteractionListeners;
import minium.actions.Interactable;
import minium.actions.Interaction;
import minium.actions.InteractionListener;
import minium.internal.Chainable;

public abstract class AbstractInteractable<T extends Interactable<?>> extends Chainable<T> implements Interactable<T> {

    public AbstractInteractable() {
        super();
    }

    @Override
    public T perform(Interaction interaction) {
        if (this.is(HasInteractionListeners.class)) {
            @SuppressWarnings("unchecked")
            Set<InteractionListener> interactionListeners = this.as(HasInteractionListeners.class).interactionListeners();
            for (InteractionListener listener : interactionListeners) {
                interaction.registerListener(listener);
            }
        }
        interaction.perform();
        return myself();
    }

    @Override
    public T performAndWait(AsyncInteraction interaction) {
        perform(interaction);
        interaction.waitUntilCompleted();
        return myself();
    }

    protected Elements getSource() {
        return this.as(Elements.class);
    }
}