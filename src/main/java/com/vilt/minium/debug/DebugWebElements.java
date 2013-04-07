package com.vilt.minium.debug;

import java.util.concurrent.TimeUnit;

import com.vilt.minium.JQueryResources;
import com.vilt.minium.WebElements;

/**
 * The Interface DebugWebElements.
 *
 * @author Rui
 */
@JQueryResources({ "minium/js/jquery-ui.effect-highlight.min.js", "minium/js/debug.js" })
public interface DebugWebElements extends WebElements {

	/**
	 * Highlight.
	 */
	public void highlight();

	/**
	 * Highlight.
	 *
	 * @param color the color
	 */
	public void highlight(String color);
	
	/**
	 * Highlight.
	 *
	 * @param time the time
	 * @param units the units
	 */
	public void highlight(long time, TimeUnit units);
	
	/**
	 * Highlight.
	 *
	 * @param color the color
	 * @param time the time
	 * @param units the units
	 */
	public void highlight(String color, long time, TimeUnit units);
	

	/**
	 * Highlight and count.
	 *
	 * @return the int
	 */
	public int highlightAndCount();

	
}
