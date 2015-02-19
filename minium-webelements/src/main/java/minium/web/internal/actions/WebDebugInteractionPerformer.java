package minium.web.internal.actions;

import minium.Elements;
import minium.actions.debug.DebugInteractionPerformer;

public class WebDebugInteractionPerformer extends WebInteractionPerformer implements DebugInteractionPerformer {

    @Override
    public void highlight(Elements elements) {
        perform(new HighlightInteraction(elements));
    }

}
