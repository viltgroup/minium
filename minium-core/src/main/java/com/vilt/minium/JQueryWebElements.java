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
 * Declares methods that correspond to jQuery transversing functions, as well as attribute functions.
 *
 * @param <T> the generic type
 */
@JQueryResources({ "minium/js/jquery.min.js", "minium/js/jquery-ext.js" })
public interface JQueryWebElements<T extends WebElements> extends WebElements, WebElementsFinder<T> {

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/attr/#attr-attributeName -->
	 * <!-- end-minium-doc -->
	 */
	public String attr(String attributeName);

	public String[] attrs(String attributeName);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/html/ -->
	 * <!-- end-minium-doc -->
	 */
	public String html();
	
	/**
	 * <!-- begin-minium-doc http://api.jquery.com/text/ -->
	 * <!-- end-minium-doc -->
	 */
	public String text();

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/val/ -->
	 * <!-- end-minium-doc -->
	 */
	public String val();
	
	public String[] vals();
	
	/**
	 * <!-- begin-minium-doc http://api.jquery.com/add/#add-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T add(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/add/#add-elements -->
	 * <!-- end-minium-doc -->
	 */
	public T add(T elems);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/addBack/ -->
	 * <!-- end-minium-doc -->
	 */
	public T addBack();

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/addBack/#addBack-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T addBack(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/children/ -->
	 * <!-- end-minium-doc -->
	 */
	public T children();
	
	/**
	 * <!-- begin-minium-doc http://api.jquery.com/children/#children-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T children(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/children/#children-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T children(T elems);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/closest/#closest-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T closest(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/closest/#closest-jQuery-object -->
	 * <!-- end-minium-doc -->
	 */
	public T closest(T elems);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/contents/ -->
	 * <!-- end-minium-doc -->
	 */
	public T contents();

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/end/ -->
	 * <!-- end-minium-doc -->
	 */
	public T end();

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/eq/ -->
	 * <!-- end-minium-doc -->
	 */
	public T eq(int index);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/filter/#filter-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T filter(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/filter/#filter-jQuery-object -->
	 * <!-- end-minium-doc -->
	 */
	public T filter(T elems);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/find/#find-selector -->
	 * <!-- end-minium-doc -->
	 */
	@Override
	public T find(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/find/#find-jQuery-object -->
	 * <!-- end-minium-doc -->
	 */
	@Override
	public T find(T elems);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/first/ -->
	 * <!-- end-minium-doc -->
	 */
	public T first();

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/has/#has-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T has(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/has/#has-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T has(T elems);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/is/#is-selector -->
	 * <!-- end-minium-doc -->
	 */
	public boolean is(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/is/#is-jQuery-object -->
	 * <!-- end-minium-doc -->
	 */
	public boolean is(T elems);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/last/ -->
	 * <!-- end-minium-doc -->
	 */
	public T last();

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/next/ -->
	 * <!-- end-minium-doc -->
	 */
	public T next();

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/next/#next-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T next(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/next/#next-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T next(T elems);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/nextAll/ -->
	 * <!-- end-minium-doc -->
	 */
	public T nextAll();

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/nextAll/#nextAll-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T nextAll(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/nextAll/#nextAll-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T nextAll(T elems);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/nextUntil/#nextUntil-selector-filter -->
	 * <!-- end-minium-doc -->
	 */
	public T nextUntil(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/nextUntil/#nextUntil-selector-filter -->
	 * <!-- end-minium-doc -->
	 */
	public T nextUntil(String selector, String filter);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/nextUntil/#nextUntil-element-filter -->
	 * <!-- end-minium-doc -->
	 */
	public T nextUntil(T elems);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/nextUntil/#nextUntil-element-filter -->
	 * <!-- end-minium-doc -->
	 */
	public T nextUntil(T elems, String filter);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/not/#not-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T not(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/not/#not-jQuery-object -->
	 * <!-- end-minium-doc -->
	 */
	public T not(T elems);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/offsetParent/ -->
	 * <!-- end-minium-doc -->
	 */
	public T offsetParent();

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/parent/ -->
	 * <!-- end-minium-doc -->
	 */
	public T parent();

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/parent/#parent-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T parent(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/parent/#parent-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T parent(T elems);
	
	/**
	 * <!-- begin-minium-doc http://api.jquery.com/parents/ -->
	 * <!-- end-minium-doc -->
	 */
	public T parents();

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/parents/#parents-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T parents(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/parents/#parents-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T parents(T elems);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/parentsUntil/#parentsUntil-selector-filter -->
	 * <!-- end-minium-doc -->
	 */
	public T parentsUntil(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/parentsUntil/#parentsUntil-selector-filter -->
	 * <!-- end-minium-doc -->
	 */
	public T parentsUntil(String selector, String filter);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/parentsUntil/#parentsUntil-element-filter -->
	 * <!-- end-minium-doc -->
	 */
	public T parentsUntil(T elems);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/parentsUntil/#parentsUntil-element-filter -->
	 * <!-- end-minium-doc -->
	 */
	public T parentsUntil(T elems, String filter);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/prev/ -->
	 * <!-- end-minium-doc -->
	 */
	public T prev();
	
	/**
	 * <!-- begin-minium-doc http://api.jquery.com/prev/#prev-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T prev(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/prev/#prev-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T prev(T elems);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/prevAll/ -->
	 * <!-- end-minium-doc -->
	 */
	public T prevAll();

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/prevAll/#prevAll-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T prevAll(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/prevAll/#prevAll-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T prevAll(T elems);
	
	/**
	 * <!-- begin-minium-doc http://api.jquery.com/prevUntil/#prevUntil-selector-filter -->
	 * <!-- end-minium-doc -->
	 */
	public T prevUntil(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/prevUntil/#prevUntil-selector-filter -->
	 * <!-- end-minium-doc -->
	 */
	public T prevUntil(String selector, String filter);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/prevUntil/#prevUntil-element-filter -->
	 * <!-- end-minium-doc -->
	 */
	public T prevUntil(T elems);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/prevUntil/#prevUntil-element-filter -->
	 * <!-- end-minium-doc -->
	 */
	public T prevUntil(T elems, String filter);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/siblings/ -->
	 * <!-- end-minium-doc -->
	 */
	public T siblings();

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/siblings/#siblings-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T siblings(String selector);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/siblings/#siblings-selector -->
	 * <!-- end-minium-doc -->
	 */
	public T siblings(T elems);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/slice/#slice-start-end -->
	 * <!-- end-minium-doc -->
	 */
	public T slice(int start);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/slice/#slice-start-end -->
	 * <!-- end-minium-doc -->
	 */
	public T slice(int start, int end);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/hasClass/#hasClass-className -->
	 * <!-- end-minium-doc -->
	 */
	public boolean hasClass(String cssClass);

	/**
	 * <!-- begin-minium-doc http://api.jquery.com/size/#size -->
	 * <!-- end-minium-doc -->
	 */
	public int size();

	public <V> V call(String fnName, Object ... args);
}
