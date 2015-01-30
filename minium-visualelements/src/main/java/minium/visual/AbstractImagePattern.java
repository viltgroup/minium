/*
 * Copyright 2010-2014, Sikuli.org, SikuliX.com
 * Released under the MIT License.
 *
 * modified RaiMan
 */
package minium.visual;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import minium.internal.Paths;
import minium.visual.Pattern.AbstractPattern;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

public class AbstractImagePattern<T extends Pattern> extends AbstractPattern<T> {

    private BufferedImage image = null;
    private URL imageUrl;

    public AbstractImagePattern() {
    }

    public AbstractImagePattern(String imageUrl) {
        this(Paths.toURL(imageUrl));
    }

    public AbstractImagePattern(ImagePattern p) {
        super(p);
        this.imageUrl = p.imageUrl();
        this.image = Preconditions.checkNotNull(p.image());
    }

    public AbstractImagePattern(URL imageUrl) {
        this.imageUrl = imageUrl;
        try {
            this.image = ImageIO.read(imageUrl);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public AbstractImagePattern(BufferedImage image) {
        this.image = image;
    }

    public URL imageUrl() {
        return imageUrl;
    }

    public BufferedImage image() {
        return image;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ImagePattern)) return false;

        ImagePattern other = (ImagePattern) obj;

        return Objects.equal(image, other.image()) &&
                Objects.equal(offset, other.offset) &&
                Objects.equal(similarity, other.similarity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(image, offset, similarity);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(ImagePattern.class.getSimpleName())
                .addValue(imageUrl)
                .add("offset", offset)
                .add("similarity", similarity)
                .toString();
    }
}
