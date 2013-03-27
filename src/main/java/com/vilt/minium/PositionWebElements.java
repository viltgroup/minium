package com.vilt.minium;


/**
 * 
 * @author Rui
 */
@JQueryResources("minium/js/position.js")
public interface PositionWebElements<T extends WebElements> extends WebElements {
	
	/**
	 * 
	 * @param fromElems
	 * @return
	 */
	public T above(WebElements fromElems);
	
	public T above(String expr);
	
	/**
	 * 
	 * @param fromElems
	 * @return
	 */
	public T leftOf(WebElements fromElems);
	
	public T leftOf(String expr);
	
	/**
	 * 
	 * @param fromElems
	 * @return
	 */
	public T below(WebElements fromElems);

	public T below(String expr);

	/**
	 * 
	 * @param fromElems
	 * @return
	 */
	public T rightOf(WebElements fromElems);
	
	public T rightOf(String expr);

	/**
	 * 
	 * @param fromElems
	 * @return
	 */
	public T overlaps(WebElements fromElems);
	
	public T overlaps(String expr);
	
}
