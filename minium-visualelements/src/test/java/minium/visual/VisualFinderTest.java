package minium.visual;

import static minium.visual.DefaultVisualElements.by;

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
        URL windowImg = VisualFinderTest.class.getClassLoader().getResource("nine-patch/window.9.png");

        DefaultVisualElements genPassIconElems = by.image("classpath:passgen.png");
        DefaultVisualElements editElems = by.text("Tools");
        DefaultVisualElements genPassElems = by.text("Generate Password").rightOf(genPassIconElems);

        elements(editElems).click();
        elements(genPassElems).click();

        DefaultVisualElements windowElems = by.ninePatch(windowImg);
        DefaultVisualElements profileDropdown = windowElems.findText("Custom");

        elements(profileDropdown).click();
    }

    @Test
    public void click_cancel() {
        URL btnImg = VisualFinderTest.class.getClassLoader().getResource("nine-patch/button.9.png");

        DefaultVisualElements cancelBtn = by.ninePatch(btnImg).findText("Cancel");

        elements(cancelBtn).click();
    }

    @SuppressWarnings("unchecked")
    protected <T extends Elements> T elements(T elems) {
        return (T) elems.as(InternalFinder.class).eval(root);
    }
}
