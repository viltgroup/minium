package minium.web.internal;

import java.util.Iterator;

import minium.Elements;
import minium.FreezableElements;
import minium.IterableElements;
import minium.internal.BaseElements;
import minium.web.BasicWebElements;
import minium.web.WebElements;

import com.google.common.collect.AbstractIterator;
import com.google.common.reflect.TypeToken;

public class DefaultIterableWebElements<T extends WebElements> extends BaseElements<T> implements IterableElements<T> {

    private final class WebElementsIterator extends AbstractIterator<T> {
        private final Elements elems;
        private final int size;
        private int current = 0;

        private WebElementsIterator(Elements elems) {
            this.size = elems.as(BasicWebElements.class).size();
            this.elems = elems;
        }

        @Override
        protected T computeNext() {
            if (current == size) {
                return endOfData();
            }
            TypeToken<BasicWebElements<T>> typeToken = typeTokenFor(BasicWebElements.class);
            return elems.as(typeToken).eq(current++);
        }
    }

    @Override
    public Iterator<T> iterator() {
        final Elements elems = myself().is(FreezableElements.class) ? myself().as(FreezableElements.class).freeze() : myself();
        return new WebElementsIterator(elems);
    }
}
