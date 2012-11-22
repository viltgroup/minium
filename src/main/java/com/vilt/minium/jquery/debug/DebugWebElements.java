package com.vilt.minium.jquery.debug;

import java.util.concurrent.TimeUnit;

import com.vilt.minium.WebElements;
import com.vilt.minium.jquery.JQueryResources;

/**
 * 
 * @author Rui
 *
 * @param <T>
 */
@JQueryResources({ "minium/js/jquery-ui.js", "minium/js/debug.js" })
public interface DebugWebElements extends WebElements {

	/**
	 * 
	 * @return
	 */
	public void highlight();

	/**
	 * 
	 * @param color
	 * @return
	 */
	public void highlight(String color);
	
	/**
	 * 
	 * @param time
	 * @param units
	 * @return
	 */
	public void highlight(long time, TimeUnit units);
	
	/**
	 * 
	 * @param color
	 * @param time
	 * @param units
	 * @return
	 */
	public void highlight(String color, long time, TimeUnit units);
	

	/**
	 * 
	 * @return
	 */
	public int highlightAndCount();

	
}
