package minium.internal;

import minium.Elements;
import minium.Rectangle;

import java.util.List;

public interface LocatableElements extends Elements {

    public enum CoordinatesType {
        SCREEN,
        VIEWPORT,
        PAGE
    }

    public List<Rectangle> boundingBoxes();

    public List<Rectangle> boundingBoxes(CoordinatesType type);

}
