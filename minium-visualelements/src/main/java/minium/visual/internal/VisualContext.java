package minium.visual.internal;

import java.util.Map;
import java.util.Set;

import minium.Elements;

import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

public interface VisualContext {

    public Iterable<Region> evaluate(Elements elems);

    public ScreenImage getScreenImage();

    public static class Impl implements VisualContext {

        private final Map<InternalVisualElements, Iterable<Region>> cache = Maps.newHashMap();

        private final Region candidateRegion;
        private final Screen screen;

        //lazy
        private ScreenImage screenImage;

        public Impl(Screen screen, Region candidateRegion) {
            this.screen = screen;
            this.candidateRegion = candidateRegion;
        }

        @Override
        public Iterable<Region> evaluate(Elements elems) {
            InternalVisualElements internalElems = elems.as(InternalVisualElements.class);
            if (cache.containsKey(internalElems)) {
                return cache.get(internalElems);
            }

            Set<Region> results = ImmutableSet.copyOf(internalElems.matches(this));
            cache.put(internalElems, results);
            return results;
        }

        @Override
        public ScreenImage getScreenImage() {
            if (screenImage == null) {
                screenImage = Vision.capture(screen, candidateRegion);
            }
            return screenImage;
        }
    }
}
