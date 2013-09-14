package com.vilt.minium;

@JQueryResources("minium/js/jsexecutor.js")
public interface JavascriptExecutorWebElements<T extends CoreWebElements<T>> extends WebElements {
    
    public <R> R eval(JavascriptFunction fn, Object ... args);
    
    public T evalWebElements(JavascriptFunction fn, Object ... args);
    
    @Async
    public <R> R evalAsync(JavascriptFunction fn, Object ... args);
}
