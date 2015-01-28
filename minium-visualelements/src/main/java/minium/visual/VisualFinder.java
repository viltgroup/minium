package minium.visual;

import java.lang.reflect.Method;
import java.net.URL;

import minium.Finder;
import minium.internal.Paths;
import minium.internal.Reflections;

public class VisualFinder<E extends VisualElements> extends Finder<E> {

    private static final Method FIND_TEXT_METHOD = Reflections.getDeclaredMethod(BasicVisualElements.class, "findText", String.class);

    private static final Method FIND_IMAGE_METHOD = Reflections.getDeclaredMethod(BasicVisualElements.class, "findImage", Pattern.class);

    private static final Method FIND_NINE_PATCH_METHOD = Reflections.getDeclaredMethod(CornersVisualElements.class, "findByNinePatch", URL.class);

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
        return createFinder(FIND_NINE_PATCH_METHOD, imgUrl);
    }

    public E image(String imgPath) {
        return pattern(new Pattern(imgPath));
    }

    public E image(URL imgUrl) {
        return pattern(new Pattern(imgUrl));
    }

    public E pattern(Pattern pattern) {
        return createFinder(FIND_IMAGE_METHOD, pattern);
    }
}
