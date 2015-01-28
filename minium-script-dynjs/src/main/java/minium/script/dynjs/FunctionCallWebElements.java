package minium.script.dynjs;

import minium.web.WebElements;

import org.dynjs.runtime.JSFunction;

public interface FunctionCallWebElements<T extends WebElements> extends WebElements {

    public T callWebElements(JSFunction fn, Object ... args);

    public Object call(JSFunction fn, Object ... args);

}
