package com.vilt.minium;

@JQueryResources("minium/js/jsexecutor.js")
public interface JavascriptExecutorWebElements<T extends CoreWebElements<T>> extends WebElements {
    
    public <R> R call(JavascriptFunction fn, Object ... args);
    
    public T callWebElements(JavascriptFunction fn, Object ... args);
    
    @Async
    public <R> R callAsync(JavascriptFunction fn, Object ... args);
 
    public <R> R eval(String script, Object ... args);
    
    public T evalWebElements(String script, Object ... args);
    
    @Async
    public <R> R evalAsync(String script, Object ... args);
}
