package com.vilt.minium.jquery;

import com.vilt.minium.WebElements;

/**
 * 
 * @author Rui
 */
@JQueryResources("minium/js/position.js")
public interface PositionWebElements<T extends WebElements<T>> extends WebElements<T> {
	
	/**
	 * 
	 * @param fromElems
	 * @return
	 */
	public T above(WebElements<T> fromElems);
	
	public T above(String expr);
	
	/**
	 * 
	 * @param fromElems
	 * @return
	 */
	public T leftOf(WebElements<T> fromElems);
	
	public T leftOf(String expr);
	
	/**
	 * 
	 * @param fromElems
	 * @return
	 */
	public T below(WebElements<T> fromElems);

	public T below(String expr);

	/**
	 * 
	 * @param fromElems
	 * @return
	 */
	public T rightOf(WebElements<T> fromElems);
	
	public T rightOf(String expr);

	/**
	 * 
	 * @param fromElems
	 * @return
	 */
	public T overlaps(WebElements<T> fromElems);
	
	public T overlaps(String expr);
	
}
