package com.vilt.minium.jquery;

import com.vilt.minium.WebElements;

/**
 * 
 * @author Rui
 */
@JQueryResources("minium/js/filters.js")
public interface FiltersWebElements<T extends FiltersWebElements<T>> extends WebElements<T> {

	/**
	 * 
	 * @param label
	 * @return
	 */
	public T withLabel(final String label);
	
	/**
	 * 
	 * @param text
	 * @return
	 */
	public T withText(final String text);
	
	/**
	 * 
	 * @param text
	 * @return
	 */
	public T containingText(final String text);
	
	/**
	 * 
	 * @param regex
	 * @return
	 */
	public T matchingText(final String regex);

	/**
	 * 
	 * @param regex
	 * @return
	 */
	public T visible();
		
}
