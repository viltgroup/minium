package minium.internal;

import minium.Elements;

public interface InternalElementsFactory<T extends Elements> {

    public abstract T createMixin(Elements elems);

    public abstract T createMixin(Elements parent, Elements elems);
}