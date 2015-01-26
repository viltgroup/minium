package minium.visual.internal.actions;

import minium.Elements;
import minium.actions.debug.DebugInteractionPerformer;

public class VisualDebugInteractionPerformer extends VisualInteractionPerformer implements DebugInteractionPerformer {

    @Override
    public void highlight(Elements elements) {
        perform(new HighlightInteraction(elements));
    }
}
