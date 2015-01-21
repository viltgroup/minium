package minium.web.internal;

import minium.web.internal.DefaultTargetLocatorWebElements.WindowRoots;
import minium.web.DocumentWebDriver;
import minium.web.WebElements;

import com.google.common.base.Preconditions;

public class DefaultFilteredWindowRoots<T extends WebElements> extends WindowRoots<T> {

    private final WebElements filter;

    public DefaultFilteredWindowRoots(WebElements filter) {
        Preconditions.checkNotNull(filter);
        this.filter = filter;
    }

    @Override
    public Iterable<DocumentWebDriver> candidateDocumentDrivers() {
        return filter.as(InternalWebElements.class).documentDrivers();
    }
}
