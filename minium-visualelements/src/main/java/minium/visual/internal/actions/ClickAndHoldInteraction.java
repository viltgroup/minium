package minium.visual.internal.actions;

import minium.Elements;
import minium.Offsets.Offset;

import org.sikuli.script.Button;
import org.sikuli.script.Location;
import org.sikuli.script.Mouse;
import org.sikuli.script.Region;

public class ClickAndHoldInteraction extends AbstractVisualInteraction {

    private final Offset offset;

    public ClickAndHoldInteraction(Elements elems) {
        this(elems, null);
    }

    public ClickAndHoldInteraction(Elements elems, Offset offset) {
        super(elems);
        this.offset = offset;
    }

    @Override
    protected void doPerform() {
        Region region = getFirstRegion(getSource());
        if (offset == null) {
            region.mouseDown(Button.LEFT);
        } else {
            Location location = getOffsetLocation(region, offset);
            Mouse.move(location);
            Mouse.down(Button.LEFT);
        }
    }
}
