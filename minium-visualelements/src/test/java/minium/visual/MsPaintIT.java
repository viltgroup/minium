package minium.visual;

import static minium.Offsets.HorizontalReference.LEFT;
import static minium.Offsets.VerticalReference.TOP;
import static minium.visual.CoreVisualElements.DefaultVisualElements.by;
import static minium.visual.VisualModules.baseModule;
import static minium.visual.VisualModules.debugModule;
import static minium.visual.VisualModules.interactableModule;
import static minium.visual.VisualModules.positionModule;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import minium.Minium;
import minium.Offsets;
import minium.Offsets.Offset;
import minium.actions.HasConfiguration;
import minium.visual.CoreVisualElements.DefaultVisualElements;
import minium.visual.Drawing.DoublePoint;
import minium.visual.Drawing.Polygon;
import minium.visual.VisualElementsFactory.Builder;
import minium.visual.internal.actions.VisualDebugInteractionPerformer;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sikuli.script.Screen;

public class MsPaintIT {

    private static final String SVG_FILE = "drawing.json";
    private static final int MIN_DISTANCE = 4;
    private static final double SCALE_FACTOR = 0.3d;

    @BeforeClass
    public static void globalSetup() throws IOException {
        // quick way to launch mspaint maximized
        Runtime.getRuntime().exec("cmd /c start \"\" /max /b mspaint.exe");
    }

    @AfterClass
    public static void globalTearDown() throws IOException {
        // quick way to ensure we kill mspaint at the end
        Runtime.getRuntime().exec("cmd /c taskkill /IM mspaint.exe");
    }

    private Screen screen;

    @Before
    public void setup() {
        screen = new Screen();
        VisualDebugInteractionPerformer performer = new VisualDebugInteractionPerformer();
        VisualModule visualModule = VisualModules.combine(
                baseModule(screen, DefaultVisualElements.class),
                positionModule(),
                interactableModule(performer),
                debugModule(performer));
        Builder<DefaultVisualElements> builder = new VisualElementsFactory.Builder<DefaultVisualElements>();
        visualModule.configure(builder);
        VisualElementsFactory<DefaultVisualElements> visualFactory = builder.build();
        DefaultVisualElements root = visualFactory.createRoot(screen);
        root.as(HasConfiguration.class).configure()
            .defaultInterval(1, TimeUnit.SECONDS)
            .defaultTimeout(20, TimeUnit.SECONDS);
        Minium.set(root);
    }

    @Test
    public void drawMiniumLogo() {
        // get drawing
        Drawing drawing = Drawing.read(SVG_FILE);

        DefaultVisualElements menuBtn            = by.image("classpath:mspaint/menuBtn.png");
        DefaultVisualElements clickableMenuBtn   = by.image("classpath:mspaint/menuBtn.clickable.png");

        DefaultVisualElements propertiesMenuItem = by.text("Properties");
        DefaultVisualElements widthFld           = by.text("Width").relative("right+16px top", "right+90px bottom");
        DefaultVisualElements heightFld          = by.text("Height").relative("right+16px top", "right+90px bottom");

        DefaultVisualElements okBtn              = by.image("classpath:mspaint/okBtn.png");

        DefaultVisualElements drawingArea        = by.ninePatch(new NinePatchPattern("classpath:mspaint/drawingArea.9.png").exact()).freeze();

        // actions
        menuBtn.moveTo();
        clickableMenuBtn.click();
        propertiesMenuItem.click();

        int w = (int) (drawing.width() * SCALE_FACTOR);
        int h = (int) (drawing.height() * SCALE_FACTOR);

        widthFld.fill(Integer.toString(w));
        heightFld.fill(Integer.toString(h));
        okBtn.click();

        // now draw!
        for (Polygon polygon : drawing.polygons()) {
            DoublePoint prevPoint = null;
            Offset target = null;
            for (DoublePoint point : polygon.points()) {
                target = Offsets.at(LEFT.plus(point.x() * SCALE_FACTOR).pixels(), TOP.plus(point.y() * SCALE_FACTOR).pixels());
                if (prevPoint == null) {
                    drawingArea.clickAndHold(target);
                    prevPoint = point;
                } else {
                    if (prevPoint.distance(point) < MIN_DISTANCE) continue;
                    drawingArea.moveTo(target);
                    prevPoint = point;
                }
            }
            drawingArea.release(target);
        }

    }
}
