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
        return ninePatch(new NinePatchPattern(imgUrl));
    }

    public E ninePatch(NinePatchPattern pattern) {
        return pattern(pattern);
    }

    public E image(String imgPath) {
        return pattern(new Pattern(imgPath));
    }

    public E image(URL imgUrl) {
        return pattern(new Pattern(imgUrl));
    }

    public E pattern(Pattern pattern) {
        return createFinder(FIND_PATTERN_METHOD, pattern);
    }
}
