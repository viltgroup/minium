/*
 * Copyright (C) 2013 VILT Group
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
