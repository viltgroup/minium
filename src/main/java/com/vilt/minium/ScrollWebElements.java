package com.vilt.minium;


/**
 * Makes available javascript functions for element scrolling
 * (<a href="https://developer.mozilla.org/en-US/docs/DOM/element.scrollIntoView">
 * element.scrollIntoView</a>)
 *
 * @param <T> the generic type
 * @author Rui
 */
@JQueryResources("minium/js/scroll.js")
public interface ScrollWebElements<T extends WebElements> extends WebElements {
	
	/**
	 * Scrolls the element into view. By default, the element is scrolled to align 
	 * with the top of the scroll area.
	 */
	public void scrollIntoView();
	
	/**
	 * Scrolls the element into view.
	 * 
	 * @param alignWithTop If <code>true</code>, the scrolled element is aligned with 
	 * the top of the scroll area. If <code>false</code>, it is aligned with the bottom.
	 */
	public void scrollIntoView(boolean alignWithTop);
}
