package com.vilt.minium;


/**
 * 
 * @author Rui
 */
@JQueryResources("minium/js/scroll.js")
public interface ScrollWebElements<T extends WebElements> extends WebElements {
	public void scrollIntoView();
	public void scrollIntoView(boolean alignWithTop);
}
