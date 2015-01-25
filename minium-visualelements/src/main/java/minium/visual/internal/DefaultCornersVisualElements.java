package minium.visual.internal;

import static com.google.common.collect.FluentIterable.from;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import java.util.Set;

import minium.Elements;
import minium.PositionElements;
import minium.visual.BasicVisualElements;
import minium.visual.CornersVisualElements;
import minium.visual.Pattern;
import minium.visual.VisualElements;
import minium.visual.internal.ninepatch.NinePatch;
import minium.visual.internal.ninepatch.NinePatchChunk;

import org.sikuli.script.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

public class DefaultCornersVisualElements<T extends VisualElements> extends BaseVisualElements<T> implements CornersVisualElements<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCornersVisualElements.class);

    static class FindByCornerVisualElements<T extends VisualElements> extends BaseInternalVisualElements<T> {

        private final Elements topleft;
        private final Elements topright;
        private final Elements bottomright;
        private final Elements bottomleft;

        public FindByCornerVisualElements(Elements topleft, Elements topright, Elements bottomright, Elements bottomleft) {
            this.topleft = topleft;
            this.topright = topright;
            this.bottomright = bottomright;
            this.bottomleft = bottomleft;
        }

        @Override
        public Iterable<Region> matches(final VisualContext context) {
            Set<Elements> topleftElems     = asSingletonElems(context.evaluate(topleft));
            Set<Elements> toprightElems    = asSingletonElems(context.evaluate(topright));
            Set<Elements> bottomrightElems = asSingletonElems(context.evaluate(bottomright));
            Set<Elements> bottomleftElems  = asSingletonElems(context.evaluate(bottomleft));

            LOGGER.debug("corner matches: [{}, {}, {}, {}]",
                    topleftElems.size(),
                    toprightElems.size(),
                    bottomrightElems.size(),
                    bottomleftElems.size());

            @SuppressWarnings("unchecked")
            Set<List<Elements>> combinations = Sets.cartesianProduct(topleftElems, toprightElems, bottomrightElems, bottomleftElems);

            Set<Region> results = from(combinations).filter(new Predicate<List<Elements>>() {
                @Override
                public boolean apply(List<Elements> corners) {
                    PositionElements<?> topleftElem     = corners.get(0).as(PositionElements.class);
                    PositionElements<?> toprightElem    = corners.get(1).as(PositionElements.class);
                    PositionElements<?> bottomrightElem = corners.get(2).as(PositionElements.class);
                    PositionElements<?> bottomleftElem  = corners.get(3).as(PositionElements.class);

                    Elements candidate = topleftElem.leftOf(toprightElem.above(bottomrightElem.rightOf(bottomleftElem.below(topleftElem))));

                    return !Iterables.isEmpty(context.evaluate(candidate));
                }
            }).transform(new Function<List<Elements>, Region>() {
                @Override
                public Region apply(List<Elements> corners) {
                    Region topleftRegion     = Iterables.get(context.evaluate(corners.get(0)), 0);
                    Region toprightRegion    = Iterables.get(context.evaluate(corners.get(1)), 0);
                    Region bottomrightRegion = Iterables.get(context.evaluate(corners.get(2)), 0);
                    Region bottomleftRegion  = Iterables.get(context.evaluate(corners.get(3)), 0);
                    return Regions.union(topleftRegion, toprightRegion, bottomrightRegion, bottomleftRegion);
                }
            }).toSet();

            return debugResults(results);
        }

        protected Set<Elements> asSingletonElems(Iterable<Region> regions) {
            return from(regions).transform(new Function<Region, Elements>() {
                @Override
                public Elements apply(Region region) {
                    return factory().createRoot(region);
                }
            }).toSet();
        }
    }

    static class FindByHorizontalCornerVisualElements<T extends VisualElements> extends BaseInternalVisualElements<T> {

        private final Elements left;
        private final Elements right;

        public FindByHorizontalCornerVisualElements(Elements left, Elements right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public Iterable<Region> matches(final VisualContext context) {
            Set<Elements> leftElems = asSingletonElems(context.evaluate(left));
            Set<Elements> rightElems = asSingletonElems(context.evaluate(right));

            @SuppressWarnings("unchecked")
            Set<List<Elements>> combinations = Sets.cartesianProduct(leftElems, rightElems);

            Set<Region> results = from(combinations).filter(new Predicate<List<Elements>>() {
                @Override
                public boolean apply(List<Elements> corners) {
                    PositionElements<?> leftElem = corners.get(0).as(PositionElements.class);
                    PositionElements<?> rightElem = corners.get(1).as(PositionElements.class);

                    Elements candidate = leftElem.leftOf(rightElem);

                    return !Iterables.isEmpty(context.evaluate(candidate));
                }
            }).transform(new Function<List<Elements>, Region>() {
                @Override
                public Region apply(List<Elements> corners) {
                    Region leftRegion = Iterables.get(context.evaluate(corners.get(0)), 0);
                    Region rightRegion = Iterables.get(context.evaluate(corners.get(1)), 0);
                    return Regions.union(leftRegion, rightRegion);
                }
            }).toSet();

            return debugResults(results);
        }

        protected Set<Elements> asSingletonElems(Iterable<Region> regions) {
            return from(regions).transform(new Function<Region, Elements>() {
                @Override
                public Elements apply(Region region) {
                    return factory().createRoot(region);
                }
            }).toSet();
        }
    }

    @Override
    public T findByNinePatch(URL imageUrl) {
        try {
            NinePatch ninePatch = NinePatch.load(imageUrl, false);
            NinePatchChunk chunk = ninePatch.getChunk();
            int[] p = chunk.getPadding();

            for (int i : p) {
                Preconditions.checkArgument(i != 0, "image %s doesn't seem to be nine-patch", imageUrl);
            }

            BufferedImage image = ninePatch.getImage();
            BufferedImage topLeft = image.getSubimage(0, 0, p[0], p[1]);
            BufferedImage topRight = image.getSubimage(image.getWidth() - p[2], 0, p[2], p[1]);
            BufferedImage bottomRight = image.getSubimage(image.getWidth() - p[2], image.getHeight() - p[3], p[2], p[3]);
            BufferedImage bottomLeft = image.getSubimage(0, image.getHeight() - p[3], p[0], p[3]);

            Elements topLeftElem = this.as(BasicVisualElements.class).findImage(new Pattern(topLeft));
            Elements topRightElem = this.as(BasicVisualElements.class).findImage(new Pattern(topRight));
            Elements bottomRightElem = this.as(BasicVisualElements.class).findImage(new Pattern(bottomRight));
            Elements bottomLeftElem = this.as(BasicVisualElements.class).findImage(new Pattern(bottomLeft));

            return findByCorners(topLeftElem, topRightElem, bottomRightElem, bottomLeftElem);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public T findByCorners(Elements topleft, Elements topright, Elements bottomright, Elements bottomleft) {
        return internalFactory().createMixin(myself(), new FindByCornerVisualElements<T>(topleft, topright, bottomright, bottomleft));
    }

    @Override
    public T findByVerticalCorners(Elements top, Elements bottom) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T findByHorizontalCorners(Elements left, Elements right) {
        return internalFactory().createMixin(myself(), new FindByHorizontalCornerVisualElements<T>(left, right));
    }

}
