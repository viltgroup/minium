package minium.web.internal.actions;

import minium.Elements;
import minium.web.EvalWebElements;

public class HighlightInteraction extends AbstractWebInteraction {

    public HighlightInteraction(Elements elems) {
        super(elems);
    }

    @Override
    protected void doPerform() {
        getSource().as(EvalWebElements.class).eval("$(this).highlight();");
    }
}
