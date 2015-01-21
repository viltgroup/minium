package minium.box.internal;

import io.platypus.Mixin;
import minium.Elements;
import minium.box.BoxElements;
import minium.box.BoxElementsFactory;
import minium.internal.InternalElementsFactory;

public class DefaultBoxElementsFactory<T extends BoxElements> extends Mixin.Impl implements BoxElementsFactory<T>, InternalElementsFactory<T> {

    public DefaultBoxElementsFactory(Builder<T> builder) {
    }

    @Override
    public T createMixin(Elements elems) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T createMixin(Elements parent, Elements elems) {
        // TODO Auto-generated method stub
        return null;
    }
}