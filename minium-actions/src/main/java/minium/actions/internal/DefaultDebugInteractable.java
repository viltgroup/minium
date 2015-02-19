package minium.actions.internal;

import platypus.Mixin;
import minium.Elements;
import minium.actions.debug.DebugInteractable;
import minium.actions.debug.DebugInteractionPerformer;

public class DefaultDebugInteractable extends Mixin.Impl implements DebugInteractable {

    private final DebugInteractionPerformer performer;

    public DefaultDebugInteractable(DebugInteractionPerformer performer) {
       this.performer = performer;
    }

    @Override
    public void highlight() {
        performer.highlight(that());
    }

    private Elements that() {
        return this.as(Elements.class);
    }

}
