/*
 * Copyright (C) 2013 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vilt.minium;

/**
 * The Interface PositionWebElements.
 *
 * @param <T> the generic type
 * @author Rui
 */
@JQueryResources("minium/js/position.js")
public interface PositionWebElements<T extends CoreWebElements<T>> extends WebElements {
	
	/**
	 * Filters elements above this element.
	 *
	 * @param elems elements to filter
	 * @return filtered elements
	 */
	public T above(WebElements elems);
	
	/**
	 * Returns elements above this element that match the expression.
	 *
	 * @param expr the expression to use for matching elements.
	 * @return elements
	 */
	public T above(String expr);
	
	/**
	 * Filters elements at the left of this element.
	 *
	 * @param elems elements to filter
	 * @return filtered elements
	 */
	public T leftOf(WebElements fromElems);
	
	/**
	 * Returns elements at the left this element that match the expression.
	 *
	 * @param expr the expression to use for matching elements.
	 * @return elements
	 */
	public T leftOf(String expr);
	
	/**
	 * Filters elements below this element.
	 *
	 * @param elems elements to filter
	 * @return filtered elements
	 */
	public T below(WebElements fromElems);

	/**
	 * Returns elements below this element that match the expression.
	 *
	 * @param expr the expression to use for matching elements.
	 * @return elements
	 */
	public T below(String expr);

	/**
	 * Filters elements at the right of this element.
	 *
	 * @param elems elements to filter
	 * @return filtered elements
	 */
	public T rightOf(WebElements fromElems);
	
	/**
	 * Returns elements at the right of this element that match the expression.
	 *
	 * @param expr the expression to use for matching elements.
	 * @return elements
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
