package minium.web;

import minium.BasicElements;
import minium.FindElements;

public interface BasicWebElements<T extends WebElements> extends WebElements, BasicElements<T>, FindElements<T> {
    public T find(WebElements elems);
    public T add(String selector);
    public T add(WebElements elems);
    public T filter(String filter);
    public T filter(WebElements elems);
    public boolean is(String selector);
    public boolean is(WebElements elems);
    public String attr(String attrname);
    public String text();
    public String html();
}
