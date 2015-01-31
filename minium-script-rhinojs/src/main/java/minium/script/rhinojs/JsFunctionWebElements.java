package minium.script.rhinojs;

import minium.web.WebElements;

import org.mozilla.javascript.Function;

public interface JsFunctionWebElements<T extends WebElements> extends WebElements {

    public T filter(Function fn);

    public T callWebElements(Function fn);

    public Object call(Function fn);

}
