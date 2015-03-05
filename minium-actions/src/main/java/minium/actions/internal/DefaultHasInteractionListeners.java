package minium.actions.internal;

import java.util.Arrays;
import java.util.Set;

import minium.Elements;
import minium.actions.HasInteractionListeners;
import minium.actions.Interactable;
import minium.actions.InteractionListener;
import minium.internal.Chainable;
import minium.internal.HasParent;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Sets;

public class DefaultHasInteractionListeners<T extends Interactable<?>> extends Chainable<T> implements HasInteractionListeners<T> {
    private Set<InteractionListener> listeners = Sets.newLinkedHashSet();

    @Override
    public T immediately() {
        return withWaitingPreset("immediate");
    }

    @Override
    public T withWaitingPreset(String waitingPreset) {
        return with(new WaitingPresetInteractionListener(waitingPreset));
    }

    @Override
    public T with(InteractionListener listener) {
        return with(new InteractionListener[] { listener });
    }

    @Override
    public T with(InteractionListener... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
        return myself();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<InteractionListener> interactionListeners() {
        Builder<InteractionListener> builder = ImmutableSet.<InteractionListener>builder();
        if (this.is(HasParent.class)) {
            Elements parent = this.as(HasParent.class).parent();
            if (parent.is(HasInteractionListeners.class)) {
                builder.addAll(parent.as(HasInteractionListeners.class).interactionListeners());
            }
        }
        return builder.addAll(listeners).build();
    }

}