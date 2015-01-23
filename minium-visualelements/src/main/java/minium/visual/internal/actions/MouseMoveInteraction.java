package minium.visual.internal.actions;

import minium.Elements;

import org.sikuli.script.Mouse;

public class MouseMoveInteraction extends AbstractVisualInteraction {

    public MouseMoveInteraction(Elements elems) {
        super(elems);
    }

    @Override
    protected void doPerform() {
        Mouse.move(getFirstLocation());
    }

}
