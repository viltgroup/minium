package minium.visual;

import java.net.URL;

import minium.Elements;

public interface CornersVisualElements<T extends VisualElements> extends VisualElements {

    public T findByNinePatch(URL imageUrl);

    public T findByCorners(Elements topleft, Elements topright, Elements bottomright, Elements bottomleft);

    public T findByVerticalCorners(Elements top, Elements bottom);

    public T findByHorizontalCorners(Elements left, Elements right);

}
