package minium.script.dynjs;

import minium.web.WebElements;

import org.dynjs.runtime.JSFunction;

public interface JsFunctionWebElements<T extends WebElements> extends WebElements {

    public T filter(JSFunction fn);

    public T callWebElements(JSFunction fn);

    public Object call(JSFunction fn);
}
