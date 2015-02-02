package minium.visual;

import static minium.Offsets.HorizontalReference.LEFT;
import static minium.Offsets.VerticalReference.TOP;
import static minium.visual.CoreVisualElements.DefaultVisualElements.by;
import static minium.visual.VisualModules.baseModule;
import static minium.visual.VisualModules.debugModule;
import static minium.visual.VisualModules.interactableModule;
import static minium.visual.VisualModules.positionModule;

import java.util.concurrent.TimeUnit;

import minium.Minium;
import minium.Offsets;
import minium.Offsets.Offset;
import minium.actions.HasConfiguration;
import minium.actions.debug.DebugInteractable;
import minium.visual.CoreVisualElements.DefaultVisualElements;
import minium.visual.Drawing.DoublePoint;
import minium.visual.Drawing.Polygon;
import minium.visual.VisualElementsFactory.Builder;
import minium.visual.internal.actions.VisualDebugInteractionPerformer;

import org.junit.Before;
import org.junit.Test;
import org.sikuli.script.Screen;

public class GimpDrawingIT {

    private static final String SVG_FILE = "drawing.json";
    private static final int MIN_DISTANCE = 4;

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
        DefaultVisualElements root = visualFactory.createRoot();
        root.as(HasConfiguration.class).configure()
            .defaultInterval(1, TimeUnit.SECONDS)
            .defaultTimeout(20, TimeUnit.SECONDS);
        Minium.set(root);
    }

    @Test
    public void testDrawTiger() {
        // get drawing
        Drawing drawing = Drawing.read(SVG_FILE);

        // gimp elements
        DefaultVisualElements winstart = by.image("classpath:images/win7start.png");
        DefaultVisualElements gimp = by.text("GIMP 2");

        // window corners
        DefaultVisualElements fileMenuitem = by.text("File").leftOf(by.text("Edit"));
        DefaultVisualElements fileNewMenuitem = by.text("New").below(fileMenuitem.relative("left top", "right+100% bottom"));

        DefaultVisualElements pencilTool = by.image("classpath:images/gimp_penciltool.png");
        DefaultVisualElements brushsizeFld = by.image("classpath:images/gimp_brushsize.png").relative("left+20% top", "left+40% bottom");

        // new window
        DefaultVisualElements newWidth = by.text("Width").relative("right+16px top", "right+64px bottom");
        DefaultVisualElements newHeight = by.text("Height").relative("right+16px top", "right+64px bottom");
        DefaultVisualElements okBtn = by.image("classpath:images/gimp_ok.png");

        // drawing
        DefaultVisualElements drawingArea = by.ninePatch("classpath:nine-patch/canvasarea.9.png").freeze();

        // action!

        // open gimp
//        winstart.click();
//        gimp.click();

        // pick pencil tool and set brush size to 1
//        pencilTool.click();
//        brushsizeFld.fill("5");

        // create new file
//        fileMenuitem.click();
//        fileNewMenuitem.click();
        newWidth.fill("900");
        newHeight.fill("900");
        okBtn.click();

        drawingArea.as(DebugInteractable.class).highlight();

        // now draw!
        double scaleX = 100d / drawing.width();
        double scaleY = 100d / drawing.height();

        for (Polygon polygon : drawing.polygons()) {
            DoublePoint prevPoint = null;
            Offset target = null;
            for (DoublePoint point : polygon.points()) {
                target = Offsets.at(LEFT.plus(point.x() * scaleX).percent(), TOP.plus(point.y() * scaleY).percent());
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

    protected DefaultVisualElements windowWithTitle(DefaultVisualElements windows, String title) {
        DefaultVisualElements titlebar = windows.relative("left top", "right top+30px");
        DefaultVisualElements titlebarTitle = titlebar.relative("left+32px top", "right-160px bottom").contains(windows.findText(title));
        DefaultVisualElements titlebarWithTitle = titlebar.contains(titlebarTitle);
        DefaultVisualElements window = windows.contains(titlebarWithTitle);

        return window;
    }
}
