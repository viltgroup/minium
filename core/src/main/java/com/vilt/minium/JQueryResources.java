package com.vilt.minium;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

/**
 * <p>When extending Minium {@link WebElements}, this annotation associates the
 * corresponding javscript files that provide the declared methods implementation.</p>
 * <p>Method names must match a JQuery function defined inside one of those jvscript
 * files.</p>
 * <p>Values must be ordered by precedence. For instance, if a custom function 
 * uses a JQUery plugin, tht plugin javascript file must be before the custom 
 * script.</p>
 * <p>CSS style files can also be passed, if required.</p>
 * 
 * @author rui.figueira
 */
@Retention(RUNTIME)
public @interface JQueryResources {

	/**
	 * Javascript files paths, as found in the classpath. Order is important, as
	 * precedence is respected.
	 * 
	 * @return javascript file paths
	 */
	String[] value();
	

	/**
	 * CSS style files paths, as found in the classpath. Order is important, as
	 * precedence is respected.
	 * 
	 * @return CSS style file paths
	 */
	String[] styles() default {};
}
