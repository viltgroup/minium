package minium.visual.internal;

import java.util.List;

import minium.Rectangle;
import minium.internal.LocatableElements;
import minium.internal.LocatableElements.CoordinatesType;
import minium.visual.VisualElements;

import org.sikuli.script.Location;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;

public class AdapterImpl<T extends VisualElements> extends BaseInternalVisualElements<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdapterImpl.class);

    @Override
    public Iterable<Region> matches() {

        final Screen screen = AdapterImpl.this.as(HasScreen.class).screen();

        List<Rectangle> boundingBoxes = parent().as(LocatableElements.class).boundingBoxes(CoordinatesType.SCREEN);

        LOGGER.debug("Found {} bounding boxes to adapt to visual:\n{}", boundingBoxes.size(), Joiner.on("\n").join(boundingBoxes));

        return FluentIterable.from(boundingBoxes).transform(new Function<Rectangle, Region>() {
            @Override
            public Region apply(Rectangle bb) {
                return screen.newRegion(new Location(bb.left(), bb.top()), bb.width(), bb.height());
            }
        });
    }

    @Override
    public Iterable<Region> matches(VisualContext context) {
        return matches();
    }

    @Override
    public Region candidateRegion() {
        Region union = Regions.union(matches());
        return union == null ? screen() : union;
    }
}
