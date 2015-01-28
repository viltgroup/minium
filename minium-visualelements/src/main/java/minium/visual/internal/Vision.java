package minium.visual.internal;

import static java.lang.String.format;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.TimeUnit;

import minium.Point;
import minium.visual.Pattern;

import org.sikuli.script.Finder;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.ScreenImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;

public class Vision {

    private static final Logger LOGGER = LoggerFactory.getLogger(Vision.class);

    public static List<Match> findPattern(VisualContext context, Region region, Pattern pattern) {
        org.sikuli.script.Pattern sikuliPattern = new org.sikuli.script.Pattern(pattern.getImage());
        if (pattern.getSimilar() != 0f) sikuliPattern.similar(pattern.getSimilar());
        if (!pattern.getTargetOffset().isNull()) {
            Point targetOffset = pattern.getTargetOffset();
            sikuliPattern.targetOffset(new Location(targetOffset.x(), targetOffset.y()));
        }
        Finder finder = createFinder(context, region);
        try {
            Stopwatch stopwatch = Stopwatch.createStarted();
            finder.findAll(sikuliPattern);
            ImmutableList<Match> results = ImmutableList.copyOf(finder);
            stopwatch.stop();
            LOGGER.debug("[region = {}, elapsed = {} ms] Found {} results for pattern {}",
                    regionLog(region), stopwatch.elapsed(TimeUnit.MILLISECONDS), results.size(), pattern);
            return results;
        } finally {
            finder.destroy();
        }
    }

    public static Iterable<?> findText(VisualContext context, Region region, String text) {
        Finder finder = createFinder(context, region);
        try {
            Stopwatch stopwatch = Stopwatch.createStarted();
            finder.findAllText(text);
            ImmutableList<Match> results = ImmutableList.copyOf(finder);
            LOGGER.debug("[region = {}, elapsed = {} ms] Found {} results for text {}",
                    regionLog(region), stopwatch.elapsed(TimeUnit.MILLISECONDS), results.size(), text);
            return results;
        } finally {
            finder.destroy();
        }
    }

    public static Finder createFinder(VisualContext context, Region region) {
        ScreenImage screenImage = context.getScreenImage();
        BufferedImage regionImage = screenImage.getImage().getSubimage(region.x - screenImage.x, region.y - screenImage.y, region.w, region.h);
        ScreenImage regionScreenImage = new ScreenImage(region.getRect(), regionImage);
        Finder finder = new Finder(regionScreenImage, region);
        return finder;
    }

    private static String regionLog(Region region) {
        return format("{%dx%d @ %d,%d}", region.w, region.h, region.x, region.y);
    }

    public static ScreenImage capture(Screen screen, Region candidateRegion) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        ScreenImage screenImage = screen.capture(candidateRegion);
        LOGGER.debug("[region = {}, elapsed = {} ms] Screen region captured",
                regionLog(candidateRegion), stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return screenImage;
    }
}
