package minium.visual;

import minium.BasicElements;
import minium.Elements;

public interface BasicVisualElements<T extends VisualElements> extends VisualElements, BasicElements<T>, Iterable<T> {

    public T findImage(String imagePath);

    public T findImage(Pattern pattern);

    public T findText(String text);

    public T enclose();

    public T add(Elements elems);

    public T contains(Elements elems);

    public String text();

}
