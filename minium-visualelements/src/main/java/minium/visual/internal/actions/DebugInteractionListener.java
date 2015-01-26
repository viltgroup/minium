package minium.visual.internal.actions;

import minium.actions.debug.DebugInteractionPerformer;
import minium.actions.internal.BeforeInteractionEvent;
import minium.actions.internal.DefaultInteractionListener;

public class DebugInteractionListener extends DefaultInteractionListener {

    private final DebugInteractionPerformer performer;

    public DebugInteractionListener(DebugInteractionPerformer performer) {
        this.performer = performer;
    }

    @Override
    protected void onBeforeEvent(BeforeInteractionEvent event) {
        if (event.getInteraction() instanceof HighlightInteraction) return;
        performer.highlight(event.getSource());
    }

}
