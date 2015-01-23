package minium.visual.internal.actions;

import minium.Elements;

import org.sikuli.script.Region;

public class TypeInteraction extends AbstractVisualInteraction {

    private CharSequence text;

    public TypeInteraction(Elements elems, CharSequence text) {
        super(elems);
        this.text = text;
    }

    @Override
    protected void doPerform() {
        Region region = getFirstRegion(getSource());
        region.click();
        getKeyboard().type(text);
    }
}
