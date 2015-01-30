package minium.visual;

import java.awt.image.BufferedImage;
import java.net.URL;

import minium.internal.Paths;

public class ImagePattern extends AbstractImagePattern<ImagePattern> {

    public ImagePattern(ImagePattern p) {
        super(p);
    }

    /**
     * create a Pattern based on an image file name<br>
     *
     * @param imgpath
     */
    public ImagePattern(String imageUrl) {
        this(Paths.toURL(imageUrl));
    }

    public ImagePattern(URL imageUrl) {
        super(imageUrl);
    }

    public ImagePattern(BufferedImage image) {
        super(image);
    }

}
