package minium.box;

import minium.Elements;

public interface BasicBoxElements<T extends Elements> extends BoxElements {
    public T left();
    public T right();
    public T top();
    public T bottom();
    public T combine();
    public T intersect(T other);
}
