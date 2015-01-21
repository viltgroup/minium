package minium.script.rhino;

import minium.web.WebElements;

import org.mozilla.javascript.Function;

public interface FunctionCallWebElements<T extends WebElements> extends WebElements {
    public T callWebElements(Function fn, Object ... args);
    public Object call(Function fn, Object ... args);
    public <R> R call(Class<R> returnClazz, Function fn, Object ... args);
}
