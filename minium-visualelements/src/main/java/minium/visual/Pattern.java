/*
 * Copyright 2010-2014, Sikuli.org, SikuliX.com
 * Released under the MIT License.
 *
 * modified RaiMan
 */
package minium.visual;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import minium.Point;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.io.Resources;

public class Pattern {

    private static final String CLASSPATH_PROTOCOL = "classpath:";

    private BufferedImage image = null;
    private float similarity = 0.8f;
    private Point offset = new Point(0, 0);

    public Pattern() {
    }

    public Pattern(Pattern p) {
        this.image = Preconditions.checkNotNull(p.image);
        this.similarity = p.similarity;
        this.offset = p.offset;
    }

    /**
     * create a Pattern based on an image file name<br>
     *
     * @param imgpath
     */
    public Pattern(String imageUrl) {
        this(toURL(imageUrl));
    }

    public Pattern(URL imageUrl) {
        try {
            this.image = ImageIO.read(imageUrl);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public Pattern(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    /**
     * sets the minimum Similarity to use with findX
     *
     * @param sim
     * @return the Pattern object itself
     */
    public Pattern similar(float sim) {
        similarity = sim;
        return this;
    }

    /**
     * sets the minimum Similarity to 0.99 which means exact match
     *
     * @return the Pattern object itself
     */
    public Pattern exact() {
        similarity = 0.99f;
        return this;
    }

    /**
     *
     * @return the current minimum similarity
     */
    public float getSimilar() {
        return this.similarity;
    }

    /**
     * set the offset from the match's center to be used with mouse actions
     *
     * @param dx
     * @param dy
     * @return the Pattern object itself
     */
    public Pattern targetOffset(int dx, int dy) {
        offset = new Point(dx, dy);
        return this;
    }

    /**
     * set the offset from the match's center to be used with mouse actions
     *
     * @param point
     * @return the Pattern object itself
     */
    public Pattern targetOffset(Point point) {
        offset = point;
        return this;
    }

    /**
     *
     * @return the current offset
     */
    public Point getTargetOffset() {
        return offset;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pattern)) return false;

        Pattern other = (Pattern) obj;

        return Objects.equal(image, other.image) &&
                Objects.equal(offset, other.offset) &&
                Objects.equal(similarity, other.similarity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(image, offset, similarity);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Pattern.class.getSimpleName())
                .addValue(image)
                .add("offset", offset)
                .add("similarity", similarity)
                .toString();
    }

    protected static URL toURL(String urlPath) {
        Preconditions.checkNotNull(urlPath);
        if (urlPath.startsWith(CLASSPATH_PROTOCOL)) {
            String path = urlPath.substring(CLASSPATH_PROTOCOL.length());
            return Resources.getResource(path);
        } else {
            try {
                return new URL(urlPath);
            } catch (MalformedURLException e) {
                // let's assume it's a local file
                File file = new File(urlPath);
                Preconditions.checkArgument(file.exists(), "File %s does not exist", file);
                Preconditions.checkArgument(file.isFile(), "%s is not a file", file);

                try {
                    return file.toURI().toURL();
                } catch (MalformedURLException e1) {
                    throw Throwables.propagate(e1);
                }
            }
        }
    }

}
