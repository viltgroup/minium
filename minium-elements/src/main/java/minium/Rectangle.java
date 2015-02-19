package minium;

import com.google.common.base.MoreObjects;

public class Rectangle {

    private final int left;
    private final int top;
    private final int width;
    private final int height;

    public Rectangle(int left, int top, int width, int height) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

    public int top() {
        return top;
    }

    public int bottom() {
        return top + height;
    }

    public int left() {
        return left;
    }

    public int right() {
        return left + width;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public Point topLeft() {
        return new Point(left(), top());
    }

    public Dimension dimension() {
        return new Dimension(width, height);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Rectangle.class)
                .add("top", top())
                .add("bottom", bottom())
                .add("left", left())
                .add("right", right())
                .add("width", width())
                .add("height", height())
                .toString();
    }

}