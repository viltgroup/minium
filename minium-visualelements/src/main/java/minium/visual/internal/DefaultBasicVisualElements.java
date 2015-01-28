package minium.visual.internal;

import static com.google.common.collect.FluentIterable.from;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import minium.Elements;
import minium.FreezableElements;
import minium.visual.BasicVisualElements;
import minium.visual.Pattern;
import minium.visual.VisualElements;

import org.sikuli.script.Match;
import org.sikuli.script.Region;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterables;
import com.google.common.reflect.TypeToken;

public class DefaultBasicVisualElements<T extends VisualElements> extends BaseVisualElements<T> implements BasicVisualElements<T> {

    static class ImageMatchVisualElements<T extends VisualElements> extends BaseInternalVisualElements<T> {

        private final Pattern pattern;

        public ImageMatchVisualElements(Pattern pattern) {
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
            return MoreObjects.toStringHelper(ImageMatchVisualElements.class.getSimpleName())
                    .add("parent", parent())
                    .add("pattern", pattern)
                    .toString();
        }
    }

    static class TextMatchVisualElements<T extends VisualElements> extends BaseInternalVisualElements<T> {

        private final String text;

        public TextMatchVisualElements(String text) {
            this.text = text;
        }

        @Override
        public Iterable<Region> matches(final VisualContext context) {
            Iterable<Region> parentMatches = context.evaluate(parent());

            return from(parentMatches).transformAndConcat(new Function<Region, Iterable<Region>>() {
                @Override
                public Iterable<Region> apply(Region region) {
                    return Iterables.filter(Vision.findText(context, region, text), Region.class);
                }
            });
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(TextMatchVisualElements.class.getSimpleName())
                    .add("parent", parent())
                    .add("text", text)
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

    @Override
    public T findImage(String imagePath) {
        return findImage(new Pattern(imagePath));
    }

    @Override
    public T findImage(Pattern pattern) {
        return internalFactory().createMixin(myself(), new ImageMatchVisualElements<T>(pattern));
    }

    @Override
    public T findText(String text) {
        return internalFactory().createMixin(myself(), new TextMatchVisualElements<T>(text));
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
