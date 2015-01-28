package minium.visual.internal.actions;

import minium.Elements;
import minium.Offsets.Offset;

import org.sikuli.script.Location;
import org.sikuli.script.Mouse;
import org.sikuli.script.Region;

public class ReleaseInteraction extends AbstractVisualInteraction {

    private final Offset offset;

    public ReleaseInteraction(Elements elems) {
        this(elems, null);
    }

    public ReleaseInteraction(Elements elems, Offset offset) {
        super(elems);
        this.offset = offset;
    }

    @Override
    protected void doPerform() {
        if (offset != null) {
            Region region = getFirstRegion(getSource());
            Location location = offset == null ? getFirstLocation() : getOffsetLocation(region, offset);
            Mouse.move(location);
        }
        Mouse.up();
    }
}
