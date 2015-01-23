package minium.visual.internal;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Set;

import minium.Elements;

import org.sikuli.script.Finder;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

public interface VisualContext {

    public Iterable<Region> evaluate(Elements elems);

    public Finder createFinder(Region region);

    public static class Impl implements VisualContext {

        private final Map<InternalVisualElements, Iterable<Region>> cache = Maps.newHashMap();
        private final ScreenImage screenImage;

        public Impl(ScreenImage screenImage) {
            this.screenImage = screenImage;
        }

        public Impl(Screen screen, Region candidateRegion) {
            this(screen.capture(candidateRegion));
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
        public Finder createFinder(Region region) {
            BufferedImage regionImage = screenImage.getImage().getSubimage(region.x - screenImage.x, region.y - screenImage.y, region.w, region.h);
            ScreenImage regionScreenImage = new ScreenImage(region.getRect(), regionImage);
            Finder finder = new Finder(regionScreenImage, region);
            return finder;
        }
    }
}
