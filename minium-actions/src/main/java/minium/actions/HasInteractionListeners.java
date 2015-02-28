package minium.actions;

import java.util.Set;

public interface HasInteractionListeners<T extends Interactable<?>> {

    public T with(InteractionListener listener);

    public T with(InteractionListener... listeners);

    public Set<InteractionListener> interactionListeners();
}
