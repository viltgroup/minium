package minium.visual;

import java.awt.image.BufferedImage;
import java.net.URL;

import minium.Point;

public class NinePatchPattern extends Pattern {

    public NinePatchPattern() {
        super();
    }

    public NinePatchPattern(BufferedImage image) {
        super(image);
    }

    public NinePatchPattern(Pattern p) {
        super(p);
    }

    public NinePatchPattern(String imageUrl) {
        super(imageUrl);
    }

    public NinePatchPattern(URL imageUrl) {
        super(imageUrl);
    }

    @Override
    public NinePatchPattern similar(float sim) {
        super.similar(sim);
        return this;
    }

    @Override
    public NinePatchPattern exact() {
        super.exact();
        return this;
    }

    @Override
    public NinePatchPattern targetOffset(int dx, int dy) {
        super.targetOffset(dx, dy);
        return this;
    }

    @Override
    public NinePatchPattern targetOffset(Point point) {
        super.targetOffset(point);
        return this;
    }
}
