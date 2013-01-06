package com.vilt.minium;

import org.openqa.selenium.Alert;

/**
 * The Interface TargetLocatorWebElements.
 *
 * @param <T> the generic type
 * @author Rui
 */
public interface TargetLocatorWebElements<T extends WebElements> extends WebElements {
	
	/**
	 * Frame.
	 *
	 * @param selector the selector
	 * @return the t
	 */
	public T frame(String selector);

	/**
	 * Frame.
	 *
	 * @param filter the filter
	 * @return the t
	 */
	public T frame(T filter);
	
	/**
	 * Frame.
	 *
	 * @return the t
	 */
	public T frame();
	
	/**
	 * Window.
	 *
	 * @param filter the filter
	 * @return the t
	 */
	public T window(T filter);
	
	/**
	 * Window.
	 *
	 * @param filter the filter
	 * @param freeze the freeze
	 * @return the t
	 */
	public T window(T filter, boolean freeze);

	/**
	 * Window.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T window(String expr);
	
	/**
	 * Window.
	 *
	 * @param expr the expr
	 * @param freeze the freeze
	 * @return the t
	 */
	public T window(String expr, boolean freeze);
	
	/**
	 * Window.
	 *
	 * @return the t
	 */
	public T window();
	
	/**
	 * Window.
	 *
	 * @param newWindow the new window
	 * @return the t
	 */
	public T window(boolean newWindow);

	public T root();
	public T root(boolean freeze);
	
	/**
	 * Alert.
	 *
	 * @return the alert
	 */
	public Alert alert();

	
}
