package minium.visual.internal.actions;

import java.util.concurrent.TimeUnit;

import minium.Elements;
import minium.internal.HasParent;

import org.sikuli.script.Region;

public class HighlightInteraction extends AbstractVisualInteraction {

    public HighlightInteraction(Elements elems) {
        super(elems);
    }

    @Override
    protected void doPerform() {
        Region region = getFirstRegion(getSource().as(HasParent.class).parent());
        region.highlight(1);
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
