package minium.visual;

import java.lang.reflect.Method;
import java.net.URL;

import minium.Finder;
import minium.internal.Paths;
import minium.internal.Reflections;

public class VisualFinder<E extends VisualElements> extends Finder<E> {

    private static final Method FIND_TEXT_METHOD = Reflections.getDeclaredMethod(BasicVisualElements.class, "findText", String.class);

    private static final Method FIND_PATTERN_METHOD = Reflections.getDeclaredMethod(BasicVisualElements.class, "find", Pattern.class);

    protected VisualFinder(Class<E> intf) {
        super(intf);
    }

    public E text(String text) {
        return createFinder(FIND_TEXT_METHOD, text);
    }

    public E ninePatch(String imgPath) {
        return ninePatch(Paths.toURL(imgPath));
    }

    public E ninePatch(URL imgUrl) {
        return pattern(new NinePatchPattern(imgUrl));
    }

    public E image(String imgPath) {
        return pattern(new ImagePattern(imgPath));
    }

    public E image(URL imgUrl) {
        return pattern(new ImagePattern(imgUrl));
    }

    public E pattern(Pattern pattern) {
        return createFinder(FIND_PATTERN_METHOD, pattern);
    }
}
