package minium.visual;

import java.net.URL;

import minium.Elements;
import minium.internal.InternalFinder;
import minium.visual.VisualElementsFactory.Builder;

import org.junit.BeforeClass;
import org.junit.Test;
import org.sikuli.script.Screen;

public class VisualFinderTest {

    private static DefaultVisualElements root;

    @BeforeClass
    public static void setup() {
        Screen screen = new Screen();
        Builder<DefaultVisualElements> builder = new VisualElementsFactory.Builder<>();
        VisualModules.defaultModule(screen, DefaultVisualElements.class).configure(builder);
        root = builder.build().createRoot(screen);
    }

    @Test
    public void simple_click() {
        URL windowImg = VisualFinderTest.class.getClassLoader().getResource("window.9.png");

        DefaultVisualElements editElems = DefaultVisualElements.by.text("Tools");
        DefaultVisualElements genPassElems = DefaultVisualElements.by.text("Generate Password");

        elements(editElems).click();
        elements(genPassElems).click();

        DefaultVisualElements windowElems = DefaultVisualElements.by.ninePatch(windowImg);
        DefaultVisualElements profileDropdown = windowElems.findText("Custom");

        elements(profileDropdown).click();
    }

    @SuppressWarnings("unchecked")
    protected <T extends Elements> T elements(T elems) {
        return (T) elems.as(InternalFinder.class).eval(root);
    }
}
