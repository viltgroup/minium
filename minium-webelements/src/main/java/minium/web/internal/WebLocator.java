package minium.web.internal;

import minium.internal.Locator;
import minium.web.WebElements;

public class WebLocator<T extends WebElements> extends Locator<T> {

    public WebLocator(Class<T> intf) {
        super(intf);
    }

    public WebLocator(T root, Class<T> intf, Class<?> ... others) {
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
