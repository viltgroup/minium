package minium.visual.internal.actions;

import java.awt.Robot;

import minium.Elements;
import minium.Offsets.Offset;
import minium.Point;
import minium.Rectangle;
import minium.actions.internal.AbstractInteraction;
import minium.visual.internal.HasScreen;
import minium.visual.internal.InternalVisualElements;
import minium.visual.internal.actions.Keyboard.WindowUnicodeKeyboard;

import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Region;

import com.google.common.collect.Iterables;

public abstract class AbstractVisualInteraction extends AbstractInteraction {

    public AbstractVisualInteraction(Elements elems) {
        super(elems);
    }

    protected Location getFirstLocation() {
        Region region = getFirstRegion(getSource());
        return region instanceof Match ? ((Match) region).getTarget() : region.getCenter();
    }

    protected Region getFirstRegion(Elements elems) {
        return Iterables.getFirst(elems.as(InternalVisualElements.class).matches(), null);
    }

    protected Keyboard getKeyboard() {
        Robot robot = (Robot) getSource().as(HasScreen.class).screen().getRobot();
        Keyboard keyboard = new WindowUnicodeKeyboard(robot);
        return keyboard;
    }

    protected Rectangle rectangleFor(Region region) {
        return new Rectangle(region.x, region.y, region.w, region.h);
    }

    protected Location getOffsetLocation(Region region, Offset offset) {
        Rectangle rectangle = rectangleFor(region);
        Point center = offset.apply(rectangle);
        Location location = new Location(center.x(), center.y());
        return location;
    }
}