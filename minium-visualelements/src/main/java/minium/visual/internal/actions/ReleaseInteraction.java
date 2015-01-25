package minium.visual.internal.actions;

import minium.Elements;

import org.sikuli.script.Mouse;

public class ReleaseInteraction extends AbstractVisualInteraction {

    public ReleaseInteraction(Elements elems) {
        super(elems);
    }

    @Override
    protected void doPerform() {
        Mouse.up();
    }
}
