package minium.visual.internal.actions;

import minium.Elements;
import minium.Offsets.Offset;

import org.sikuli.script.Location;
import org.sikuli.script.Mouse;
import org.sikuli.script.Region;

public class MoveToInteraction extends AbstractVisualInteraction {

    private final Offset offset;

    public MoveToInteraction(Elements elems) {
        this(elems, null);
    }

    public MoveToInteraction(Elements elems, Offset offset) {
        super(elems);
        this.offset = offset;
    }

    @Override
    protected void doPerform() {
        Region region = getFirstRegion(getSource());
        Location location = offset == null ? getFirstLocation() : getOffsetLocation(region, offset);
        Mouse.move(location);
    }

}
