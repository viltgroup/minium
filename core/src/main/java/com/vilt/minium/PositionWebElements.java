package com.vilt.minium;


/**
 * The Interface PositionWebElements.
 *
 * @param <T> the generic type
 * @author Rui
 */
@JQueryResources("minium/js/position.js")
public interface PositionWebElements<T extends WebElements> extends WebElements {
	
	/**
	 * Above.
	 *
	 * @param fromElems the from elems
	 * @return the t
	 */
	public T above(WebElements fromElems);
	
	/**
	 * Above.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T above(String expr);
	
	/**
	 * Left of.
	 *
	 * @param fromElems the from elems
	 * @return the t
	 */
	public T leftOf(WebElements fromElems);
	
	/**
	 * Left of.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T leftOf(String expr);
	
	/**
	 * Below.
	 *
	 * @param fromElems the from elems
	 * @return the t
	 */
	public T below(WebElements fromElems);

	/**
	 * Below.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T below(String expr);

	/**
	 * Right of.
	 *
	 * @param fromElems the from elems
	 * @return the t
	 */
	public T rightOf(WebElements fromElems);
	
	/**
	 * Right of.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T rightOf(String expr);

	/**
	 * Overlaps.
	 *
	 * @param fromElems the from elems
	 * @return the t
	 */
	public T overlaps(WebElements fromElems);
	
	/**
	 * Overlaps.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T overlaps(String expr);
	
}
