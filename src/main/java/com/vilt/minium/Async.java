package com.vilt.minium;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.openqa.selenium.JavascriptExecutor;

/**
 * <p>Minium methods that perform some kind of client asynchronous operation must have
 * this annotation. Right now, only methods that <strong>do not return</strong> 
 * {@link WebElements} are allowed to be marked as asynchronous.</p>
 * <p>In pratical terms, instead of calling the method 
 * {@link JavascriptExecutor#executeScript(String, Object...)} to evaluate the
 * Minium expression, the engine will call 
 * {@link JavascriptExecutor#executeAsyncScript(String, Object...)}.</p>
 * <p>Also, the javascript function corresponding to the declared method must receive an
 * extra argument that will correspond to the <code>callback</code> function, which must 
 * be used to return this function asynchronously.</p>
 * 
 * @see JavascriptExecutor#executeAsyncScript(String, Object...)
 * 
 * @author rui.figueira
 */
@Retention(RUNTIME)
@Target(ElementType.METHOD)
public @interface Async { }
