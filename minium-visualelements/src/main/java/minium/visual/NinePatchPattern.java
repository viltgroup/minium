package minium.visual;

import java.awt.image.BufferedImage;
import java.net.URL;

public class NinePatchPattern extends AbstractImagePattern<NinePatchPattern> {

    public NinePatchPattern() {
        super();
    }

    public NinePatchPattern(BufferedImage image) {
        super(image);
    }

    public NinePatchPattern(ImagePattern p) {
        super(p);
    }

    public NinePatchPattern(String imageUrl) {
        super(imageUrl);
    }

    public NinePatchPattern(URL imageUrl) {
        super(imageUrl);
    }

}
