/*
 * Copyright (C) 2015 The Minium Authors
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
package minium.web;

import minium.BasicElements;
import minium.FindElements;

public interface BasicWebElements<T extends WebElements> extends WebElements, BasicElements<T>, FindElements<T> {

    @Override
    public T find(String selector);

    public T find(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/attr/#attr-attributeName -->
     * Get the value of an attribute for the first element in the set of matched elements.
     *
     * @param attributeName The name of the attribute to get.
     * @return result of jQuery .attr() method
     * @see <a href="http://api.jquery.com/attr/#attr-attributeName">jQuery .attr() method</a>
     * <!-- end-minium-doc -->
     */
    public String attr(String attributeName);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/css/#css-propertyName -->
     * Get the value of style properties for the first element in the set of matched elements.
     *
     * @param propertyName A CSS property.
     * @return result of jQuery .css() method
     * @see <a href="http://api.jquery.com/css/#css-propertyName">jQuery .css() method</a>
     * <!-- end-minium-doc -->
     */
    public String css(String propertyName);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/html/ -->
     * Get the HTML contents of the first element in the set of matched elements.
     *
     * @return result of jQuery .html() method
     * @see <a href="http://api.jquery.com/html/">jQuery .html() method</a>
     * <!-- end-minium-doc -->
     */
    public String html();

    /**
     * <!-- begin-minium-doc http://api.jquery.com/attr/#prop-propertyName -->
     * Get the value of a property for the first element in the set of matched elements.
     *
     * @param propertyName The name of the property to get.
     * @return result of jQuery .prop() method
     * @see <a href="http://api.jquery.com/attr/#prop-propertyName">jQuery .prop() method</a>
     * <!-- end-minium-doc -->
     */
    public Object prop(String propertyName);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/data/#data-key -->
     * Return the value at the named data store for the first element in the jQuery collection,
     * as set by data(name, value) or by an HTML5 data-* attribute.
     *
     * @param key Name of the data stored.
     * @return result of jQuery .data() method
     * @see <a href="http://api.jquery.com/data/#data-key">jQuery .data() method</a>
     * <!-- end-minium-doc -->
     */
    public Object data(String key);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/data/#data-key -->
     * Return the value at the named data store for the first element in the jQuery collection,
     * as set by data(name, value) or by an HTML5 data-* attribute.
     *
     * @return result of jQuery .data() method
     * @see <a href="http://api.jquery.com/data/#data">jQuery .data() method</a>
     * <!-- end-minium-doc -->
     */
    public Object data();

    /**
     * <!-- begin-minium-doc http://api.jquery.com/text/ -->
     * Get the combined text contents of each element in the set of matched elements, including their descendants.
     *
     * @return result of jQuery .text() method
     * @see <a href="http://api.jquery.com/text/">jQuery .text() method</a>
     * <!-- end-minium-doc -->
     */
    public String text();

    /**
     * <!-- begin-minium-doc http://api.jquery.com/val/ -->
     * Get the current value of the first element in the set of matched elements.
     *
     * @return result of jQuery .val() method
     * @see <a href="http://api.jquery.com/val/">jQuery .val() method</a>
     * <!-- end-minium-doc -->
     */
    public String val();

    /**
     * <!-- begin-minium-doc http://api.jquery.com/add/#add-selector -->
     * Add elements to the set of matched elements.
     *
     * @param selector A string representing a selector expression to find additional elements to add to the set of matched elements.
     * @return result of jQuery .add() method
     * @see <a href="http://api.jquery.com/add/#add-selector">jQuery .add() method</a>
     * <!-- end-minium-doc -->
     */
    public T add(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/add/#add-elements -->
     * Add elements to the set of matched elements.
     *
     * @param elems One or more elements to add to the set of matched elements.
     * @return result of jQuery .add() method
     * @see <a href="http://api.jquery.com/add/#add-elements">jQuery .add() method</a>
     * <!-- end-minium-doc -->
     */
    public T add(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/addBack/ -->
     * Add the previous set of elements on the stack to the current set, optionally filtered by a selector.
     *
     * @return result of jQuery .addBack() method
     * @see <a href="http://api.jquery.com/addBack/">jQuery .addBack() method</a>
     * <!-- end-minium-doc -->
     */
    public T addBack();

    /**
     * <!-- begin-minium-doc http://api.jquery.com/addBack/#addBack-selector -->
     * Add the previous set of elements on the stack to the current set, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match the current set of elements against.
     * @return result of jQuery .addBack() method
     * @see <a href="http://api.jquery.com/addBack/#addBack-selector">jQuery .addBack() method</a>
     * <!-- end-minium-doc -->
     */
    public T addBack(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/children/ -->
     * Get the children of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @return result of jQuery .children() method
     * @see <a href="http://api.jquery.com/children/">jQuery .children() method</a>
     * <!-- end-minium-doc -->
     */
    public T children();

    /**
     * <!-- begin-minium-doc http://api.jquery.com/children/#children-selector -->
     * Get the children of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return result of jQuery .children() method
     * @see <a href="http://api.jquery.com/children/#children-selector">jQuery .children() method</a>
     * <!-- end-minium-doc -->
     */
    public T children(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/children/#children-selector -->
     * Get the children of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param elems A string containing a selector expression to match elements against.
     * @return result of jQuery .children() method
     * @see <a href="http://api.jquery.com/children/#children-selector">jQuery .children() method</a>
     * <!-- end-minium-doc -->
     */
    public T children(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/closest/#closest-selector -->
     * For each element in the set, get the first element that matches the selector by testing the element itself and traversing up through its ancestors in the DOM tree.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return result of jQuery .closest() method
     * @see <a href="http://api.jquery.com/closest/#closest-selector">jQuery .closest() method</a>
     * <!-- end-minium-doc -->
     */
    public T closest(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/closest/#closest-jQuery-object -->
     * For each element in the set, get the first element that matches the selector by testing the element itself and traversing up through its ancestors in the DOM tree.
     *
     * @param elems A jQuery object to match elements against.
     * @return result of jQuery .closest() method
     * @see <a href="http://api.jquery.com/closest/#closest-jQuery-object">jQuery .closest() method</a>
     * <!-- end-minium-doc -->
     */
    public T closest(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/contents/ -->
     * Get the children of each element in the set of matched elements, including text and comment nodes.
     *
     * @return result of jQuery .contents() method
     * @see <a href="http://api.jquery.com/contents/">jQuery .contents() method</a>
     * <!-- end-minium-doc -->
     */
    public T contents();

    /**
     * <!-- begin-minium-doc http://api.jquery.com/end/ -->
     * End the most recent filtering operation in the current chain and return the set of matched elements to its previous state.
     *
     * @return result of jQuery .end() method
     * @see <a href="http://api.jquery.com/end/">jQuery .end() method</a>
     * <!-- end-minium-doc -->
     */
    public T end();

    /**
     * <!-- begin-minium-doc http://api.jquery.com/filter/#filter-selector -->
     * Reduce the set of matched elements to those that match the selector or pass the function's test.
     *
     * @param selector A string containing a selector expression to match the current set of elements against.
     * @return result of jQuery .filter() method
     * @see <a href="http://api.jquery.com/filter/#filter-selector'>jQuery .filter() method</a>
     * <!-- end-minium-doc -->
     */
    public T filter(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/filter/#filter-jQuery-object -->
     * Reduce the set of matched elements to those that match the selector or pass the function's test.
     *
     * @param elems An existing jQuery object to match the current set of elements against.
     * @return result of jQuery .filter() method
     * @see <a href="http://api.jquery.com/filter/#filter-jQuery-object'>jQuery .filter() method</a>
     * <!-- end-minium-doc -->
     */
    public T filter(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/has/#has-selector -->
     * Reduce the set of matched elements to those that have a descendant that matches the selector or DOM element.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return result of jQuery .has() method
     * @see <a href="http://api.jquery.com/has/#has-selector">jQuery .has() method</a>
     * <!-- end-minium-doc -->
     */
    public T has(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/has/#has-selector -->
     * Reduce the set of matched elements to those that have a descendant that matches the selector or DOM element.
     *
     * @param elems A string containing a selector expression to match elements against.
     * @return result of jQuery .has() method
     * @see <a href="http://api.jquery.com/has/#has-selector">jQuery .has() method</a>
     * <!-- end-minium-doc -->
     */
    public T has(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/is/#is-selector -->
     * Check the current matched set of elements against a selector, element, or jQuery object and return true if at least one of these elements matches the given arguments.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return result of jQuery .is() method
     * @see <a href="http://api.jquery.com/is/#is-selector">jQuery .is() method</a>
     * <!-- end-minium-doc -->
     */
    public boolean is(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/is/#is-jQuery-object -->
     * Check the current matched set of elements against a selector, element, or jQuery object and return true if at least one of these elements matches the given arguments.
     *
     * @param elems An existing jQuery object to match the current set of elements against.
     * @return result of jQuery .is() method
     * @see <a href="http://api.jquery.com/is/#is-jQuery-object">jQuery .is() method</a>
     * <!-- end-minium-doc -->
     */
    public boolean is(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/next/ -->
     * Get the immediately following sibling of each element in the set of matched elements. If a selector is provided, it retrieves the next sibling only if it matches that selector.
     *
     * @return result of jQuery .next() method
     * @see <a href="http://api.jquery.com/next/">jQuery .next() method</a>
     * <!-- end-minium-doc -->
     */
    public T next();

    /**
     * <!-- begin-minium-doc http://api.jquery.com/next/#next-selector -->
     * Get the immediately following sibling of each element in the set of matched elements. If a selector is provided, it retrieves the next sibling only if it matches that selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return result of jQuery .next() method
     * @see <a href="http://api.jquery.com/next/#next-selector">jQuery .next() method</a>
     * <!-- end-minium-doc -->
     */
    public T next(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/next/#next-selector -->
     * Get the immediately following sibling of each element in the set of matched elements. If a selector is provided, it retrieves the next sibling only if it matches that selector.
     *
     * @param elems A string containing a selector expression to match elements against.
     * @return result of jQuery .next() method
     * @see <a href="http://api.jquery.com/next/#next-selector">jQuery .next() method</a>
     * <!-- end-minium-doc -->
     */
    public T next(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/nextAll/ -->
     * Get all following siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @return result of jQuery .nextAll() method
     * @see <a href="http://api.jquery.com/nextAll/">jQuery .nextAll() method</a>
     * <!-- end-minium-doc -->
     */
    public T nextAll();

    /**
     * <!-- begin-minium-doc http://api.jquery.com/nextAll/#nextAll-selector -->
     * Get all following siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return result of jQuery .nextAll() method
     * @see <a href="http://api.jquery.com/nextAll/#nextAll-selector">jQuery .nextAll() method</a>
     * <!-- end-minium-doc -->
     */
    public T nextAll(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/nextAll/#nextAll-selector -->
     * Get all following siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param elems A string containing a selector expression to match elements against.
     * @return result of jQuery .nextAll() method
     * @see <a href="http://api.jquery.com/nextAll/#nextAll-selector">jQuery .nextAll() method</a>
     * <!-- end-minium-doc -->
     */
    public T nextAll(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/nextUntil/#nextUntil-selector-filter -->
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object passed.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching following sibling elements.
     * @return result of jQuery .nextUntil() method
     * @see <a href="http://api.jquery.com/nextUntil/#nextUntil-selector-filter">jQuery .nextUntil() method</a>
     * <!-- end-minium-doc -->
     */
    public T nextUntil(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/nextUntil/#nextUntil-selector-filter -->
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object passed.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching following sibling elements.
     * @param filter A string containing a selector expression to match elements against.
     * @return result of jQuery .nextUntil() method
     * @see <a href="http://api.jquery.com/nextUntil/#nextUntil-selector-filter">jQuery .nextUntil() method</a>
     * <!-- end-minium-doc -->
     */
    public T nextUntil(String selector, String filter);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/nextUntil/#nextUntil-element-filter -->
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object passed.
     *
     * @param elems A DOM node or jQuery object indicating where to stop matching following sibling elements.
     * @return result of jQuery .nextUntil() method
     * @see <a href="http://api.jquery.com/nextUntil/#nextUntil-element-filter">jQuery .nextUntil() method</a>
     * <!-- end-minium-doc -->
     */
    public T nextUntil(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/nextUntil/#nextUntil-element-filter -->
     * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object passed.
     *
     * @param elems A DOM node or jQuery object indicating where to stop matching following sibling elements.
     * @param filter A string containing a selector expression to match elements against.
     * @return result of jQuery .nextUntil() method
     * @see <a href="http://api.jquery.com/nextUntil/#nextUntil-element-filter">jQuery .nextUntil() method</a>
     * <!-- end-minium-doc -->
     */
    public T nextUntil(WebElements elems, String filter);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/not/#not-selector -->
     * Remove elements from the set of matched elements.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return result of jQuery .not() method
     * @see <a href="http://api.jquery.com/not/#not-selector">jQuery .not() method</a>
     * <!-- end-minium-doc -->
     */
    public T not(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/not/#not-jQuery-object -->
     * Remove elements from the set of matched elements.
     *
     * @param elems An existing jQuery object to match the current set of elements against.
     * @return result of jQuery .not() method
     * @see <a href="http://api.jquery.com/not/#not-jQuery-object">jQuery .not() method</a>
     * <!-- end-minium-doc -->
     */
    public T not(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/offsetParent/ -->
     * Get the closest ancestor element that is positioned.
     *
     * @return result of jQuery .offsetParent() method
     * @see <a href="http://api.jquery.com/offsetParent/">jQuery .offsetParent() method</a>
     * <!-- end-minium-doc -->
     */
    public T offsetParent();

    /**
     * <!-- begin-minium-doc http://api.jquery.com/parent/ -->
     * Get the parent of each element in the current set of matched elements, optionally filtered by a selector.
     *
     * @return result of jQuery .parent() method
     * @see <a href="http://api.jquery.com/parent/">jQuery .parent() method</a>
     * <!-- end-minium-doc -->
     */
    public T parent();

    /**
     * <!-- begin-minium-doc http://api.jquery.com/parent/#parent-selector -->
     * Get the parent of each element in the current set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return result of jQuery .parent() method
     * @see <a href="http://api.jquery.com/parent/#parent-selector">jQuery .parent() method</a>
     * <!-- end-minium-doc -->
     */
    public T parent(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/parent/#parent-selector -->
     * Get the parent of each element in the current set of matched elements, optionally filtered by a selector.
     *
     * @param elems A string containing a selector expression to match elements against.
     * @return result of jQuery .parent() method
     * @see <a href="http://api.jquery.com/parent/#parent-selector">jQuery .parent() method</a>
     * <!-- end-minium-doc -->
     */
    public T parent(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/parents/ -->
     * Get the ancestors of each element in the current set of matched elements, optionally filtered by a selector.
     *
     * @return result of jQuery .parents() method
     * @see <a href="http://api.jquery.com/parents/">jQuery .parents() method</a>
     * <!-- end-minium-doc -->
     */
    public T parents();

    /**
     * <!-- begin-minium-doc http://api.jquery.com/parents/#parents-selector -->
     * Get the ancestors of each element in the current set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return result of jQuery .parents() method
     * @see <a href="http://api.jquery.com/parents/#parents-selector">jQuery .parents() method</a>
     * <!-- end-minium-doc -->
     */
    public T parents(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/parents/#parents-selector -->
     * Get the ancestors of each element in the current set of matched elements, optionally filtered by a selector.
     *
     * @param elems A string containing a selector expression to match elements against.
     * @return result of jQuery .parents() method
     * @see <a href="http://api.jquery.com/parents/#parents-selector">jQuery .parents() method</a>
     * <!-- end-minium-doc -->
     */
    public T parents(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/parentsUntil/#parentsUntil-selector-filter -->
     * Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jQuery object.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching ancestor elements.
     * @return result of jQuery .parentsUntil() method
     * @see <a href="http://api.jquery.com/parentsUntil/#parentsUntil-selector-filter">jQuery .parentsUntil() method</a>
     * <!-- end-minium-doc -->
     */
    public T parentsUntil(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/parentsUntil/#parentsUntil-selector-filter -->
     * Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jQuery object.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching ancestor elements.
     * @param filter A string containing a selector expression to match elements against.
     * @return result of jQuery .parentsUntil() method
     * @see <a href="http://api.jquery.com/parentsUntil/#parentsUntil-selector-filter">jQuery .parentsUntil() method</a>
     * <!-- end-minium-doc -->
     */
    public T parentsUntil(String selector, String filter);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/parentsUntil/#parentsUntil-element-filter -->
     * Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jQuery object.
     *
     * @param elems A DOM node or jQuery object indicating where to stop matching ancestor elements.
     * @return result of jQuery .parentsUntil() method
     * @see <a href="http://api.jquery.com/parentsUntil/#parentsUntil-element-filter">jQuery .parentsUntil() method</a>
     * <!-- end-minium-doc -->
     */
    public T parentsUntil(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/parentsUntil/#parentsUntil-element-filter -->
     * Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jQuery object.
     *
     * @param elems A DOM node or jQuery object indicating where to stop matching ancestor elements.
     * @param filter A string containing a selector expression to match elements against.
     * @return result of jQuery .parentsUntil() method
     * @see <a href="http://api.jquery.com/parentsUntil/#parentsUntil-element-filter">jQuery .parentsUntil() method</a>
     * <!-- end-minium-doc -->
     */
    public T parentsUntil(WebElements elems, String filter);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/prev/ -->
     * Get the immediately preceding sibling of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @return result of jQuery .prev() method
     * @see <a href="http://api.jquery.com/prev/">jQuery .prev() method</a>
     * <!-- end-minium-doc -->
     */
    public T prev();

    /**
     * <!-- begin-minium-doc http://api.jquery.com/prev/#prev-selector -->
     * Get the immediately preceding sibling of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return result of jQuery .prev() method
     * @see <a href="http://api.jquery.com/prev/#prev-selector">jQuery .prev() method</a>
     * <!-- end-minium-doc -->
     */
    public T prev(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/prev/#prev-selector -->
     * Get the immediately preceding sibling of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param elems A string containing a selector expression to match elements against.
     * @return result of jQuery .prev() method
     * @see <a href="http://api.jquery.com/prev/#prev-selector">jQuery .prev() method</a>
     * <!-- end-minium-doc -->
     */
    public T prev(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/prevAll/ -->
     * Get all preceding siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @return result of jQuery .prevAll() method
     * @see <a href="http://api.jquery.com/prevAll/">jQuery .prevAll() method</a>
     * <!-- end-minium-doc -->
     */
    public T prevAll();

    /**
     * <!-- begin-minium-doc http://api.jquery.com/prevAll/#prevAll-selector -->
     * Get all preceding siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return result of jQuery .prevAll() method
     * @see <a href="http://api.jquery.com/prevAll/#prevAll-selector">jQuery .prevAll() method</a>
     * <!-- end-minium-doc -->
     */
    public T prevAll(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/prevAll/#prevAll-selector -->
     * Get all preceding siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param elems A string containing a selector expression to match elements against.
     * @return result of jQuery .prevAll() method
     * @see <a href="http://api.jquery.com/prevAll/#prevAll-selector">jQuery .prevAll() method</a>
     * <!-- end-minium-doc -->
     */
    public T prevAll(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/prevUntil/#prevUntil-selector-filter -->
     * Get all preceding siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching preceding sibling elements.
     * @return result of jQuery .prevUntil() method
     * @see <a href="http://api.jquery.com/prevUntil/#prevUntil-selector-filter">jQuery .prevUntil() method</a>
     * <!-- end-minium-doc -->
     */
    public T prevUntil(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/prevUntil/#prevUntil-selector-filter -->
     * Get all preceding siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object.
     *
     * @param selector A string containing a selector expression to indicate where to stop matching preceding sibling elements.
     * @param filter A string containing a selector expression to match elements against.
     * @return result of jQuery .prevUntil() method
     * @see <a href="http://api.jquery.com/prevUntil/#prevUntil-selector-filter">jQuery .prevUntil() method</a>
     * <!-- end-minium-doc -->
     */
    public T prevUntil(String selector, String filter);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/prevUntil/#prevUntil-element-filter -->
     * Get all preceding siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object.
     *
     * @param elems A DOM node or jQuery object indicating where to stop matching preceding sibling elements.
     * @return result of jQuery .prevUntil() method
     * @see <a href="http://api.jquery.com/prevUntil/#prevUntil-element-filter">jQuery .prevUntil() method</a>
     * <!-- end-minium-doc -->
     */
    public T prevUntil(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/prevUntil/#prevUntil-element-filter -->
     * Get all preceding siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object.
     *
     * @param elems A DOM node or jQuery object indicating where to stop matching preceding sibling elements.
     * @param filter A string containing a selector expression to match elements against.
     * @return result of jQuery .prevUntil() method
     * @see <a href="http://api.jquery.com/prevUntil/#prevUntil-element-filter">jQuery .prevUntil() method</a>
     * <!-- end-minium-doc -->
     */
    public T prevUntil(WebElements elems, String filter);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/siblings/ -->
     * Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @return result of jQuery .siblings() method
     * @see <a href="http://api.jquery.com/siblings/">jQuery .siblings() method</a>
     * <!-- end-minium-doc -->
     */
    public T siblings();

    /**
     * <!-- begin-minium-doc http://api.jquery.com/siblings/#siblings-selector -->
     * Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param selector A string containing a selector expression to match elements against.
     * @return result of jQuery .siblings() method
     * @see <a href="http://api.jquery.com/siblings/#siblings-selector">jQuery .siblings() method</a>
     * <!-- end-minium-doc -->
     */
    public T siblings(String selector);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/siblings/#siblings-selector -->
     * Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
     *
     * @param elems A string containing a selector expression to match elements against.
     * @return result of jQuery .siblings() method
     * @see <a href="http://api.jquery.com/siblings/#siblings-selector">jQuery .siblings() method</a>
     * <!-- end-minium-doc -->
     */
    public T siblings(WebElements elems);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/slice/#slice-start-end -->
     * Reduce the set of matched elements to a subset specified by a range of indices.
     *
     * @param start An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
     * @return result of jQuery .slice() method
     * @see <a href="http://api.jquery.com/slice/#slice-start-end">jQuery .slice() method</a>
     * <!-- end-minium-doc -->
     */
    public T slice(int start);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/slice/#slice-start-end -->
     * Reduce the set of matched elements to a subset specified by a range of indices.
     *
     * @param start An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
     * @param end An integer indicating the 0-based position at which the elements stop being selected. If negative, it indicates an offset from the end of the set. If omitted, the range continues until the end of the set.
     * @return result of jQuery .slice() method
     * @see <a href="http://api.jquery.com/slice/#slice-start-end">jQuery .slice() method</a>
     * <!-- end-minium-doc -->
     */
    public T slice(int start, int end);

    /**
     * <!-- begin-minium-doc http://api.jquery.com/hasClass/#hasClass-className -->
     * Determine whether any of the matched elements are assigned the given class.
     *
     * @param cssClass The class name to search for.
     * @return result of jQuery .hasClass() method
     * @see <a href="http://api.jquery.com/hasClass/#hasClass-className">jQuery .hasClass() method</a>
     * <!-- end-minium-doc -->
     */
    public boolean hasClass(String cssClass);

}
