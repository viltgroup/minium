package com.vilt.minium.jquery;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

/**
 * 
 * @author Rui
 */
@Retention(RUNTIME)
public @interface JQueryResources {

	/**
	 * 
	 * @return
	 */
	String[] value();
	
	/**
	 * 
	 * @return
	 */
	String[] styles() default {};
}
