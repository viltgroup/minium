package minium.web;

import minium.ConditionalElements;

public interface ConditionalWebElements<T extends WebElements> extends WebElements, ConditionalElements<T> {
    public T and(String selector);
    public T or(String selector);
    public T then(String filter);
    public T when(String filter);
    public T unless(String filter);
}
