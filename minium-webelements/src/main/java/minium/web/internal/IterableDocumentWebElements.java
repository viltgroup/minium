package minium.web.internal;

import java.util.Iterator;

import minium.internal.BaseElements;
import minium.web.WebElements;
import minium.web.internal.drivers.DocumentWebElement;

public interface IterableDocumentWebElements extends Iterable<DocumentWebElement> {

    public static class Impl extends BaseElements<WebElements> implements IterableDocumentWebElements {
        @Override
        public Iterator<DocumentWebElement> iterator() {
            return this.as(InternalWebElements.class).wrappedNativeElements().iterator();
        }
    }
}
