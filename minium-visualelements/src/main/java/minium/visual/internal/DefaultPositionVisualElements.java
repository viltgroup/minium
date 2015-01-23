package minium.visual.internal;

import static com.google.common.collect.FluentIterable.from;
import static minium.Offsets.at;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;

import minium.Elements;
import minium.Offsets;
import minium.Offsets.HorizontalOffset;
import minium.Offsets.Offset;
import minium.Offsets.VerticalOffset;
import minium.Point;
import minium.PositionElements;
import minium.Rectangle;
import minium.visual.VisualElements;

import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;

public class DefaultPositionVisualElements<T extends VisualElements> extends BaseVisualElements<T> implements PositionElements<T> {

    static abstract class RelativePositionWebElements<T extends VisualElements> extends BaseInternalVisualElements<T> {

        static final Logger LOGGER = LoggerFactory.getLogger(RelativePositionWebElements.class);

        private final Elements fromElems;

        public RelativePositionWebElements(Elements fromElems) {
            this.fromElems = fromElems;
        }

        @Override
        public Iterable<Region> matches(VisualContext context) {
            Iterable<Region> fromRegions = context.evaluate(fromElems);
            Iterable<Region> parentRegions = context.evaluate(parent());

            final Iterable<Region> extensionBoxes = from(fromRegions)
                    .transform(new Function<Region, Region>() {
                        @Override
                        public Region apply(Region region) {
                            return extend(region);
                        }
                    });

            final Map<Region, Integer> distances = Maps.newHashMap();

            for (Region region : parentRegions) {
                int distance = Integer.MAX_VALUE;
                for (Region extensionBox : extensionBoxes) {
                    if (intersects(region, extensionBox)) {
                        distance = Math.min(distance, distance(region, extensionBox));
                    }
                }
                if (distance != Integer.MAX_VALUE) distances.put(region, distance);
            }

            SortedSet<Region> results = from(distances.keySet()).toSortedSet(new Comparator<Region>() {
                @Override
                public int compare(Region r1, Region r2) {
                    return distances.get(r1) -  distances.get(r2);
                }
            });

            return debugResults(results);
        }

        protected abstract int distance(Region region, Region other);

        protected abstract Region extend(Region region);

        protected boolean intersects(Region region, Region other) {
            return other.getRect().intersects(region.getRect());
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this.getClass().getSimpleName())
                    .add("parent", parent())
                    .add("fromElems", fromElems)
                    .toString();
        }
    }

    static class AboveVisualElements<T extends VisualElements> extends RelativePositionWebElements<T> {

        public AboveVisualElements(Elements fromElems) {
            super(fromElems);
        }

        @Override
        protected int distance(Region region, Region extensionBox) {
            int rb = region.y + region.h;
            int ebb = extensionBox.y + extensionBox.h;
            return Math.max(0, ebb - rb);
        }

        @Override
        protected Region extend(Region region) {
            return region.above();
        }
    }

    static class LeftOfVisualElements<T extends VisualElements> extends RelativePositionWebElements<T> {

        public LeftOfVisualElements(Elements fromElems) {
            super(fromElems);
        }

        @Override
        protected int distance(Region region, Region extensionBox) {
            int rr = region.x + region.w;
            int ebr = extensionBox.x + extensionBox.w;
            return Math.max(0, ebr - rr);
        }

        @Override
        protected Region extend(Region region) {
            return region.left();
        }
    }

    static class RightOfVisualElements<T extends VisualElements> extends RelativePositionWebElements<T> {

        public RightOfVisualElements(Elements fromElems) {
            super(fromElems);
        }

        @Override
        protected int distance(Region region, Region extensionBox) {
            int rl = region.x;
            int ebl = extensionBox.x;
            return Math.max(0, rl - ebl);
        }

        @Override
        protected Region extend(Region region) {
            return region.right();
        }
    }

    static class BelowVisualElements<T extends VisualElements> extends RelativePositionWebElements<T> {

        public BelowVisualElements(Elements fromElems) {
            super(fromElems);
        }

        @Override
        protected int distance(Region region, Region extensionBox) {
            int rt = region.y;
            int ebt = extensionBox.y;
            return Math.max(0, rt - ebt);
        }

        @Override
        protected Region extend(Region region) {
            return region.below();
        }
    }

    static class RelativeRegionVisualElements<T extends VisualElements> extends BaseInternalVisualElements<T> {

        private final Offset topLeftOffset;
        private final Offset bottomRightOffset;

        public RelativeRegionVisualElements(String topleftPos, String bottomRightPos) {
            this(at(topleftPos), at(bottomRightPos));
        }

        public RelativeRegionVisualElements(Offset topLeftOffset, Offset bottomRightOffset) {
            this.topLeftOffset = topLeftOffset;
            this.bottomRightOffset = bottomRightOffset;
        }

        @Override
        public Iterable<Region> matches(VisualContext context) {
            Iterable<Region> parentMatches = context.evaluate(parent());
            return from(parentMatches).transform(new Function<Region, Region>() {
                @Override
                public Region apply(Region region) {
                    Rectangle rectangle = rectangleFor(region);

                    Point topleft = topLeftOffset.apply(rectangle);
                    Point bottomright = bottomRightOffset.apply(rectangle);

                    return new Region(topleft.x(), topleft.y(), bottomright.x() - topleft.x(), bottomright.y() - topleft.y());
                }
            }).toSet();
        }

        protected Rectangle rectangleFor(Region region) {
            return new Rectangle(region.x, region.y, region.w, region.h);
        }
    }

    static class TargetVisualElements<T extends VisualElements> extends BaseInternalVisualElements<T> {

        private final Offset centerOffset;

        public TargetVisualElements(Offset centerOffset) {
            this.centerOffset = centerOffset;
        }

        @Override
        public Iterable<Region> matches(VisualContext context) {
            Iterable<Region> parentMatches = context.evaluate(parent());
            return from(parentMatches).transform(new Function<Region, Region>() {
                @Override
                public Region apply(Region region) {
                    Rectangle rectangle = rectangleFor(region);

                    Point center = centerOffset.apply(rectangle);
                    Location regionLocation = region.getCenter();
                    Location offset = new Location(center.x() - regionLocation.x, center.y() - regionLocation.y);
                    Match match = new Match(region, 1);
                    match.setTargetOffset(offset);
                    return match;
                }
            }).toSet();
        }

        protected Rectangle rectangleFor(Region region) {
            return new Rectangle(region.x, region.y, region.w, region.h);
        }
    }

    @Override
    public T above(Elements fromElems) {
        return internalFactory().createMixin(myself(), new AboveVisualElements<T>(fromElems));
    }

    @Override
    public T leftOf(Elements fromElems) {
        return internalFactory().createMixin(myself(), new LeftOfVisualElements<T>(fromElems));
    }

    @Override
    public T below(Elements fromElems) {
        return internalFactory().createMixin(myself(), new BelowVisualElements<T>(fromElems));
    }

    @Override
    public T rightOf(Elements fromElems) {
        return internalFactory().createMixin(myself(), new RightOfVisualElements<T>(fromElems));
    }

    @Override
    public T relative(String topleft, String bottomright) {
        return this.relative(at(topleft), at(bottomright));
    }

    @Override
    public T relative(Offset topLeftOffset, Offset bottomRightOffset) {
        return internalFactory().createMixin(myself(), new RelativeRegionVisualElements<T>(topLeftOffset, bottomRightOffset));
    }

    @Override
    public T target(String center) {
        return target(Offsets.at(center));
    }

    @Override
    public T target(HorizontalOffset horizontal, VerticalOffset vertical) {
        return target(at(horizontal, vertical));
    }

    @Override
    public T target(Offset center) {
        return internalFactory().createMixin(myself(), new TargetVisualElements<T>(center));
    }

    @Override
    public T overlaps(Elements fromElems) {
        // TODO Auto-generated method stub
        return null;
    }


}
