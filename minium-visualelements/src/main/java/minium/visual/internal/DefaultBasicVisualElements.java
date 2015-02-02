package minium.visual.internal;

import static com.google.common.collect.FluentIterable.from;
import static java.lang.String.format;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import minium.Elements;
import minium.FreezableElements;
import minium.PositionElements;
import minium.visual.BasicVisualElements;
import minium.visual.ImagePattern;
import minium.visual.NinePatchPattern;
import minium.visual.Pattern;
import minium.visual.TextPattern;
import minium.visual.VisualElements;
import minium.visual.internal.ninepatch.NinePatch;
import minium.visual.internal.ninepatch.NinePatchChunk;

import org.sikuli.script.Match;
import org.sikuli.script.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Throwables;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.reflect.TypeToken;

public class DefaultBasicVisualElements<T extends VisualElements> extends BaseVisualElements<T> implements BasicVisualElements<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultBasicVisualElements.class);

    static class PatternMatchVisualElements<T extends VisualElements> extends BaseInternalVisualElements<T> {

        private final ImagePattern pattern;

        public PatternMatchVisualElements(ImagePattern pattern) {
            this.pattern = Preconditions.checkNotNull(pattern);
        }

        @Override
        public Iterable<Region> matches(final VisualContext context) {
            Iterable<Region> parentMatches = context.evaluate(parent());

            Set<Region> results = from(parentMatches).transformAndConcat(new Function<Region, Iterable<Region>>() {
                @Override
                public Iterable<Region> apply(Region region) {
                    List<Match> matches = Vision.findPattern(context, region, pattern);
                    return from(matches).filter(Region.class);
                }
            }).toSet();

            return debugResults(results);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(PatternMatchVisualElements.class.getSimpleName())
                    .add("parent", parent())
                    .add("pattern", pattern)
                    .toString();
        }
    }

    static class TextMatchVisualElements<T extends VisualElements> extends BaseInternalVisualElements<T> {

        private final TextPattern textPattern;

        public TextMatchVisualElements(TextPattern textPattern) {
            this.textPattern = textPattern;
        }

        @Override
        public Iterable<Region> matches(final VisualContext context) {
            Iterable<Region> parentMatches = context.evaluate(parent());

            return from(parentMatches).transformAndConcat(new Function<Region, Iterable<Region>>() {
                @Override
                public Iterable<Region> apply(Region region) {
                    return Iterables.filter(Vision.findText(context, region, textPattern.getText()), Region.class);
                }
            });
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(TextMatchVisualElements.class.getSimpleName())
                    .add("parent", parent())
                    .add("text", textPattern.getText())
                    .toString();
        }
    }

    static class EnclosedVisualElements<T extends VisualElements> extends BaseInternalVisualElements<T> {

        @Override
        public Iterable<Region> matches(VisualContext context) {
            Iterable<Region> matches = context.evaluate(parent());
            Region region = Regions.union(matches);
            if (region == null) return Collections.emptySet();

            return Collections.singleton(region);
        }
    }

    static class AddVisualElements<T extends VisualElements> extends BaseInternalVisualElements<T> {

        private final Elements elems;

        public AddVisualElements(Elements elems) {
            this.elems = elems;
        }

        @Override
        public Iterable<Region> matches(VisualContext context) {
            Iterable<Region> parentMatches = context.evaluate(parent());
            Iterable<Region> elemMatches = context.evaluate(elems);
            return Iterables.concat(parentMatches, elemMatches);
        }

        @Override
        public Region candidateRegion() {
            return  Regions.union(
                    parent().as(InternalVisualElements.class).candidateRegion(),
                    elems.as(InternalVisualElements.class).candidateRegion());
        }
    }

    static class EqVisualElementsImpl<T extends VisualElements> extends BaseInternalVisualElements<T> {

        private final int i;

        public EqVisualElementsImpl(int i) {
            this.i = i;
        }

        @Override
        public Iterable<Region> matches(VisualContext context) {
            Iterable<Region> matches = context.evaluate(parent());
            Region region = i >= 0 ? Iterables.get(matches, i, null) : Iterables.getLast(matches, null);
            Iterable<Region> results = region == null ? Collections.<Region>emptySet() : Collections.singleton(region);
            return debugResults(results);
        }
    }

    static class ContainsVisualElements<T extends VisualElements> extends BaseInternalVisualElements<T> {

        private final class ContainsRegionPredicate implements Predicate<Region> {
            private final Region parentRegion;

            private ContainsRegionPredicate(Region parentRegion) {
                this.parentRegion = parentRegion;
            }

            @Override
            public boolean apply(Region elemsRegion) {
                return parentRegion.contains(elemsRegion);
            }
        }

        private final Elements elems;

        public ContainsVisualElements(Elements elems) {
            this.elems = elems;
        }

        @Override
        public Iterable<Region> matches(VisualContext context) {
            final Iterable<Region> parentMatches = context.evaluate(parent());
            final Iterable<Region> elemsMatches = context.evaluate(elems);
            return from(parentMatches).filter(new Predicate<Region>() {
                @Override
                public boolean apply(final Region parentRegion) {
                    return Iterables.any(elemsMatches, new ContainsRegionPredicate(parentRegion));
                }
            }).toSet();
        }

    }

    static class NinePatchVisualElements<T extends VisualElements> extends BaseInternalVisualElements<T> {

        private final Pattern topleft;
        private final Pattern topright;
        private final Pattern bottomright;
        private final Pattern bottomleft;

        public NinePatchVisualElements(NinePatchPattern pattern) {
            NinePatch ninePatch = createNinePatch(pattern);
            NinePatchChunk chunk = ninePatch.getChunk();
            int[] p = chunk.getPadding();
            for (int i : p) {
                Preconditions.checkArgument(i != 0, "image %s doesn't seem to be nine-patch", pattern.imageUrl());
            }

            BufferedImage image = ninePatch.getImage();
            topleft = new ImagePattern(image.getSubimage(0, 0, p[0], p[1])).similar(pattern.getSimilar());
            topright = new ImagePattern(image.getSubimage(image.getWidth() - p[2], 0, p[2], p[1])).similar(pattern.getSimilar());
            bottomright = new ImagePattern(image.getSubimage(image.getWidth() - p[2], image.getHeight() - p[3], p[2], p[3])).similar(pattern.getSimilar());
            bottomleft = new ImagePattern(image.getSubimage(0, image.getHeight() - p[3], p[0], p[3])).similar(pattern.getSimilar());
        }

        @Override
        public Iterable<Region> matches(final VisualContext context) {
            Set<Elements> topleftElems     = asSingletonElems(context.evaluate(root().as(BasicVisualElements.class).find(topleft)));
            Set<Elements> toprightElems    = asSingletonElems(context.evaluate(root().as(BasicVisualElements.class).find(topright)));
            Set<Elements> bottomrightElems = asSingletonElems(context.evaluate(root().as(BasicVisualElements.class).find(bottomright)));
            Set<Elements> bottomleftElems  = asSingletonElems(context.evaluate(root().as(BasicVisualElements.class).find(bottomleft)));

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
                    return factory().createNative(region);
                }
            }).toSet();
        }

        protected NinePatch createNinePatch(NinePatchPattern pattern) {
            NinePatch ninePatch = null;
                try {
                    ninePatch = NinePatch.load(pattern.image(), true, false);
                } catch (Exception e) {
                    throw Throwables.propagate(e);
                }
            return ninePatch;
        }
    }

    @Override
    public T find(String imagePath) {
        return find(new ImagePattern(imagePath));
    }

    @Override
    public T find(Pattern pattern) {
        if (pattern instanceof NinePatchPattern) {
            return internalFactory().createMixin(myself(), new NinePatchVisualElements<T>((NinePatchPattern) pattern));
        } else if (pattern instanceof ImagePattern) {
            return internalFactory().createMixin(myself(), new PatternMatchVisualElements<T>((ImagePattern) pattern));
        } else if (pattern instanceof TextPattern) {
            return internalFactory().createMixin(myself(), new TextMatchVisualElements<T>((TextPattern) pattern));
        }
        throw new IllegalArgumentException(format("Pattern %s is not recognized", pattern));
    }

    @Override
    public T findText(String text) {
        return find(new TextPattern(text));
    }

    @Override
    public T enclose() {
        return internalFactory().createMixin(myself(), new EnclosedVisualElements<T>());
    }

    @Override
    public T add(Elements elems) {
        return internalFactory().createMixin(myself(), new AddVisualElements<T>(elems));
    }

    @Override
    public T eq(int i) {
        return internalFactory().createMixin(myself(), new EqVisualElementsImpl<T>(i));
    }

    @Override
    public T first() {
        return eq(0);
    }

    @Override
    public T last() {
        return eq(-1);
    }

    @Override
    public T contains(Elements elems) {
        return internalFactory().createMixin(myself(), new ContainsVisualElements<T>(elems));
    }

    @Override
    public String text() {
        Iterable<Region> matches = this.as(InternalVisualElements.class).matches();
        return Joiner.on("").join(Iterables.transform(matches, new Function<Region, String>() {
            @Override
            public String apply(Region region) {
                return region.text();
            }
        }));
    }

    @Override
    public int size() {
        return Iterables.size(this.as(InternalVisualElements.class).matches());
    }

    @Override
    public Iterator<T> iterator() {
        final Elements elems = myself().is(FreezableElements.class) ? myself().as(FreezableElements.class).freeze() : myself();
        final int size = elems.as(BasicVisualElements.class).size();

        return new AbstractIterator<T>() {
            private int current = 0;

            @Override
            protected T computeNext() {
                if (current == size) {
                    return endOfData();
                }
                TypeToken<BasicVisualElements<T>> typeToken = typeTokenFor(BasicVisualElements.class);
                return elems.as(typeToken).eq(current++);
            }
        };
    }
}
