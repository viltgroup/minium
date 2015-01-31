package minium.script.rhinojs;

import minium.web.WebElements;

import org.mozilla.javascript.Function;

public interface JsFunctionWebElements<T extends WebElements> extends WebElements {

    public T filter(Function fn);

    public T applyWebElements(Function fn);

    public T applyWebElements(Function fn, Object[] args);

    public Object apply(Function fn);

    public Object apply(Function fn, Object[] args);

}
