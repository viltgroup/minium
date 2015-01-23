package minium.visual.internal;

import java.util.Collections;

import minium.FreezableElements;
import minium.visual.VisualElements;

import org.sikuli.script.Region;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

public class FreezableVisualElements<T extends VisualElements> extends BaseVisualElements<T> implements FreezableElements<T> {

    static class FrozenVisualElements<T extends VisualElements> extends BaseInternalVisualElements<T> {

        private Iterable<Region> matches;

        @Override
        public Iterable<Region> matches(VisualContext context) {
            if (this.matches == null) {
                Iterable<Region> matches = context.evaluate(parent());
                if (Iterables.isEmpty(matches)) {
                    return Collections.emptySet();
                } else {
                    this.matches = ImmutableSet.copyOf(matches);
                }
            }
            return this.matches;
        }
    }

    @Override
    public T freeze() {
        return internalFactory().createMixin(myself(), new FrozenVisualElements<T>());
    }

}
