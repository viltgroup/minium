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

import minium.ConditionalElements;
import minium.Elements;

/**
 * Extends {@link ConditionalElements} by adding methods that receive a CSS
 * selector instead of {@link Elements}. First, CSS selector is evaluated using
 * <code>$(selector)</code> and then the same logic applies as in
 * {@link ConditionalElements}
 */
public interface ConditionalWebElements<T extends WebElements> extends
        WebElements, ConditionalElements<T> {

    /**
     * Intersects both evaluated sets. For instance, if <code>this</code>
     * evaluates into <code>{ A, B, C }</code> and <code>selector</code>
     * evaluates into <code>{ B, C, D }</code>, then
     * <code>this.and(selector)</code> evaluates into <code>{ B, C }</code>
     *
     * @param selector
     *            a CSS selector
     * @return new {@link Elements} that corresponds to the intersection of
     *         <code>this</code> and elements represented by
     *         <code>selector</code>
     */
    public T and(String selector);

    /**
     * Represents the union both evaluated sets. For instance, if
     * <code>this</code> evaluates into <code>{ A, B, C }</code> and
     * <code>selector</code> evaluates into <code>{ B, C, D }</code>, then
     * <code>this.or(selector)</code> evaluates into <code>{ A, B, C, D }</code>
     *
     * @param selector
     *            a CSS selector
     * @return new {@link Elements} that corresponds to the union of
     *         <code>this</code> and elements represented by
     *         <code>selector</code>
     */
    public T or(String selector);

    /**
     * Evaluates into the elements represented by <code>selector</code> if and
     * only if <code>this</code> evaluates into a non-empty set, otherwise
     * returns an empty set.
     * <p>
     * For instance, if <code>this</code> evaluates into <code>{ A, B }</code>
     * and <code>selector</code> evaluates into <code>{ B, C }</code> then
     * <code>this.then(selector)</code> evaluates into <code>{ B, C }</code>.
     * <p>
     * If <code>this</code> evaluates into an empty set, then
     * <code>this.then(selector)</code> evaluates into an empty set.
     *
     * @param selector
     *            a CSS selector
     * @return new {@link Elements} that evaluates into elements represented by
     *         <code>selector</code> only and only if <code>this</code>
     *         evaluates into a non-empty set, otherwise returns an empty set.
     */
    public T then(String selector);

    /**
     * Evaluates into <code>this</code> if and only if elements represented by
     * <code>selector</code> evaluates into a non-empty set, otherwise returns
     * an empty set. Basically, <code>someElems.when(selector)</code> is
     * equivalent to <code>$(selector).then(someElems)</code>.
     *
     * @param selector
     *            a CSS selector
     * @return new {@link Elements} that evaluates into <code>this</code> only
     *         and only if elements represented by <code>selector</code>
     *         evaluates into a non-empty set, otherwise returns an empty set.
     */
    public T when(String selector);

    /**
     * Evaluates into <code>this</code> if and only if elements represented by
     * <code>selector</code> evaluates into a empty set, otherwise returns an
     * empty set.
     * <p>
     * For instance, if <code>this</code> evaluates into <code>{ A, B }</code>
     * and <code>selector</code> evaluates into <code>{ B, C }</code> then
     * <code>this.unless(selector)</code> evaluates into an empty set.
     * <p>
     * If <code>selector</code> evaluates into an empty set, then
     * <code>this.unless(selector)</code> evaluates into <code>{ A, B }</code>.
     *
     * @param selector
     *            a CSS selector
     * @return new {@link Elements} that evaluates into <code>this</code> only
     *         and only if elements represented by <code>selector</code>
     *         evaluates into a empty set, otherwise returns an empty set.
     */
    public T unless(String selector);
}
