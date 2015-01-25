package minium.visual.internal.actions;

import minium.Elements;

import org.sikuli.script.Button;

public class ClickAndHoldInteraction extends AbstractVisualInteraction {

    public ClickAndHoldInteraction(Elements elems) {
        super(elems);
    }

    @Override
    protected void doPerform() {
        getFirstRegion(getSource()).mouseDown(Button.LEFT);
    }
}
