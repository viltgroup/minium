package minium.actions.internal;

import java.util.Arrays;
import java.util.List;

import minium.actions.AsyncInteraction;
import minium.actions.Interaction;
import minium.actions.InteractionListener;
import platypus.Mixin;

import com.google.common.collect.Lists;

public abstract class AbstractInteractionPerformer extends Mixin.Impl implements InteractionPerformer {

    private List<InteractionListener> listeners = Lists.newArrayList();

    public AbstractInteractionPerformer() {
        super();
    }

    @Override
    public InteractionPerformer with(InteractionListener... listeners) {
        if (listeners != null) {
            this.listeners.addAll(Arrays.asList(listeners));
        }
        return this;
    }

    @Override
    public void perform(Interaction interaction) {
        for (InteractionListener listener : listeners) {
            interaction.registerListener(listener);
        }
        interaction.perform();
    }

    @Override
    public void performAndWait(AsyncInteraction interaction) {
        perform(interaction);
        interaction.waitUntilCompleted();
    }

}