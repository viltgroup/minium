package minium.web;

import minium.Finder;
import minium.web.internal.CssSelectors;

public class WebFinder<T extends WebElements> extends Finder<T> {

    public WebFinder(Class<T> intf) {
        super(intf);
    }

    public WebFinder(T root, Class<T> intf, Class<?> ... others) {
        super(root, intf, others);
    }

    public T cssSelector(String selector) {
        return selector(selector);
    }

    public T name(String name) {
        return selector(CssSelectors.attr("name", name));
    }

    public T className(String name) {
        return selector(CssSelectors.className(name));
    }

    public T tagName(String name) {
        return selector(CssSelectors.tagName(name));
    }
}
