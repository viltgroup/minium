package minium.visual.internal.actions;

import java.awt.event.KeyEvent;

import minium.Elements;

import org.sikuli.script.Region;

public class FillInteraction extends AbstractVisualInteraction {

    private final CharSequence text;

    public FillInteraction(Elements elems, CharSequence text) {
        super(elems);
        this.text = text;
    }

    @Override
    protected void doPerform() {
        Region region = getFirstRegion(getSource());
        region.click();
        Keyboard keyboard =  getKeyboard();
        keyboard.doType(KeyEvent.VK_CONTROL, KeyEvent.VK_A);
        keyboard.doType(KeyEvent.VK_BACK_SPACE);
        keyboard.type(text);
    }
}
