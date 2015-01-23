package minium.visual.internal.actions;

import minium.Elements;

import org.sikuli.script.Mouse;

public class MouseUpInteraction extends AbstractVisualInteraction {

    public MouseUpInteraction(Elements elems) {
        super(elems);
    }

    @Override
    protected void doPerform() {
        Mouse.up();
    }
}
