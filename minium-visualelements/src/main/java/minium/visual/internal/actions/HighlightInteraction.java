package minium.visual.internal.actions;

import minium.Elements;

import org.sikuli.script.Region;

public class HighlightInteraction extends AbstractVisualInteraction {

    public HighlightInteraction(Elements elems) {
        super(elems);
    }

    @Override
    protected void doPerform() {
        Region region = getFirstRegion(getSource());
        region.highlight(1);
    }

}
