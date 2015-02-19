package minium.internal;

import java.util.Iterator;

import minium.BasicElements;
import minium.Elements;
import minium.FreezableElements;
import minium.IterableElements;

import com.google.common.collect.AbstractIterator;
import com.google.common.reflect.TypeToken;

public class DefaultIterableElements<T extends Elements> extends BaseElements<T> implements IterableElements<T> {

    private final class ElementsIterator extends AbstractIterator<T> {
        private final Elements elems;
        private final int size;
        private int current = 0;

        private ElementsIterator(Elements elems) {
            this.size = elems.as(BasicElements.class).size();
            this.elems = elems;
        }

        @Override
        protected T computeNext() {
            if (current == size) {
                return endOfData();
            }
            TypeToken<BasicElements<T>> typeToken = typeTokenFor(BasicElements.class);
            return elems.as(typeToken).eq(current++);
        }
    }

    @Override
    public Iterator<T> iterator() {
        final Elements elems = myself().is(FreezableElements.class) ? myself().as(FreezableElements.class).freeze() : myself();
        return new ElementsIterator(elems);
    }
}
