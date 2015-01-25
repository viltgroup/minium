package minium.visual.internal.actions;

import minium.Elements;

import org.sikuli.script.Mouse;

public class MoveToInteraction extends AbstractVisualInteraction {

    public MoveToInteraction(Elements elems) {
        super(elems);
    }

    @Override
    protected void doPerform() {
        Mouse.move(getFirstLocation());
    }

}
