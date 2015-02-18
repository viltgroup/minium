package minium.visual;

import static minium.visual.CoreVisualElements.DefaultVisualElements.by;

import java.net.URL;

import minium.Elements;
import minium.internal.InternalLocator;
import minium.visual.CoreVisualElements.DefaultVisualElements;
import minium.visual.internal.VisualElementsFactory;
import minium.visual.internal.VisualModules;
import minium.visual.internal.VisualElementsFactory.Builder;

import org.junit.BeforeClass;
import org.junit.Test;
import org.sikuli.script.Screen;

public class VisualFinderIT {

    private static DefaultVisualElements root;

    @BeforeClass
    public static void setup() {
        Screen screen = new Screen();
        Builder<DefaultVisualElements> builder = new VisualElementsFactory.Builder<>();
        VisualModules.defaultModule(screen, DefaultVisualElements.class).configure(builder);
        root = builder.build().createRoot();
    }

    @Test
    public void simple_click() {
        URL windowImg = VisualFinderIT.class.getClassLoader().getResource("nine-patch/window.9.png");

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
        URL btnImg = VisualFinderIT.class.getClassLoader().getResource("nine-patch/button.9.png");

        DefaultVisualElements cancelBtn = by.ninePatch(btnImg).findText("Cancel");

        elements(cancelBtn).click();
    }

    @SuppressWarnings("unchecked")
    protected <T extends Elements> T elements(T elems) {
        return (T) elems.as(InternalLocator.class).eval(root);
    }
}
