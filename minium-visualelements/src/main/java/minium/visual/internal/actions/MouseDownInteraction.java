package minium.visual.internal.actions;

import minium.Elements;

import org.sikuli.script.Button;
import org.sikuli.script.Mouse;

public class MouseDownInteraction extends AbstractVisualInteraction {

    public MouseDownInteraction(Elements elems) {
        super(elems);
    }

    @Override
    protected void doPerform() {
        Mouse.move(getFirstLocation());
        Mouse.down(Button.LEFT);
    }
}
