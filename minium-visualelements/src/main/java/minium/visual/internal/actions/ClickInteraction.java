package minium.visual.internal.actions;

import minium.Elements;

public class ClickInteraction extends AbstractVisualInteraction {

    public ClickInteraction(Elements elems) {
        super(elems);
    }

    @Override
    protected void doPerform() {
        getFirstRegion(getSource()).click();
    }
}
