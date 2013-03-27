package com.vilt.minium;


/**
 * 
 * @author Rui
 */
@JQueryResources("minium/js/filters.js")
public interface FiltersWebElements<T extends FiltersWebElements<T>> extends WebElements {

	/**
	 * 
	 * @param label
	 * @return
	 */
	public T withLabel(final String label);
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public T withAttr(final String name, final String value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public T withValue(final String value);

	/**
	 * 
	 * @param value
	 * @return
	 */
	public T withName(final String name);

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
