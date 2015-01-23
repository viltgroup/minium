package minium.visual.internal;

import minium.Elements;
import minium.visual.VisualElements;

public interface VisualElementsAdapter<T extends VisualElements> extends Elements {

    public T visual();

}
