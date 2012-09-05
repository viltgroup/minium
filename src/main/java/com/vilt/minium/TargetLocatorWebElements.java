package com.vilt.minium;

import org.openqa.selenium.Alert;

/**
 * 
 * @author Rui
 *
 * @param <T>
 */
public interface TargetLocatorWebElements<T extends WebElements<T>> extends WebElements<T> {
	
	/**
	 * 
	 * @param selector
	 * @return
	 */
	public T frame(String selector);
	
	/**
	 * 
	 * @return
	 */
	public T frame();
	
	/**
	 * 
	 * @param nameOrHandle
	 * @return
	 */
	public T window(String nameOrHandle);
	
	/**
	 * 
	 * @return
	 */
	public T window();
	
	/**
	 * 
	 * @return
	 */
	public Alert alert();
	
}
