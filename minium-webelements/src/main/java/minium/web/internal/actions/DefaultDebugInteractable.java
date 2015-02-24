package minium.web.internal.actions;

import minium.actions.Interactable;
import minium.actions.debug.DebugInteractable;
import minium.actions.internal.AbstractInteractable;

public class DefaultDebugInteractable<T extends Interactable<?>> extends AbstractInteractable<T> implements DebugInteractable<T> {

    @Override
    public void highlight() {
        perform(new HighlightInteraction(getSource()));
    }
}
