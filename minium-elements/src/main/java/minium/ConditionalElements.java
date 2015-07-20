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
package minium;

/**
 * Logic operations over elements. We consider a elements expression to evaluate
 * into a "true" value when its evaluated set is non-empty, and to be "false"
 * when it is empty.
 */
public interface ConditionalElements<T extends Elements> extends Elements {

    /**
     * Intersects both evaluated sets. For instance, if <code>this</code>
     * evaluates into <code>{ A, B, C }</code> and <code>elems</code> evaluates
     * into <code>{ B, C, D }</code>, then <code>this.and(elems)</code>
     * evaluates into <code>{ B, C }</code>
     *
     * @param elems
     *            elements to intersect with
     * @return new {@link Elements} that corresponds to the intersection of
     *         <code>this</code> and <code>elems</code>
     */
    public abstract T and(Elements elems);

    /**
     * Represents the union both evaluated sets. For instance, if
     * <code>this</code> evaluates into <code>{ A, B, C }</code> and
     * <code>elems</code> evaluates into <code>{ B, C, D }</code>, then
     * <code>this.or(elems)</code> evaluates into <code>{ A, B, C, D }</code>
     *
     * @param elems
     *            elements to union with
     * @return new {@link Elements} that corresponds to the union of
     *         <code>this</code> and <code>elems</code>
     */
    public abstract T or(Elements elems);

    /**
     * Evaluates into <code>elems</code> if and only if <code>this</code>
     * evaluates into a non-empty set, otherwise returns an empty set.
     * <p>
     * For instance, if <code>this</code> evaluates into <code>{ A, B }</code>
     * and <code>elems</code> evaluates into <code>{ B, C }</code> then
     * <code>this.then(elems)</code> evaluates into <code>{ B, C }</code>.
     * <p>
     * If <code>this</code> evaluates into an empty set, then
     * <code>this.then(elems)</code> evaluates into an empty set.
     *
     * @param elems
     *            elements to evaluate into if <code>this</code> is non-empty
     * @return new {@link Elements} that evaluates into <code>elems</code> only
     *         and only if <code>this</code> evaluates into a non-empty set,
     *         otherwise returns an empty set.
     */
    public abstract T then(Elements elems);

    /**
     * Evaluates into <code>this</code> if and only if <code>elems</code>
     * evaluates into a non-empty set, otherwise returns an empty set.
     * Basically, <code>someElems.when(otherElems)</code> is equivalent to
     * <code>otherElems.then(someElems)</code>.
     *
     * @param elems
     *            if non-empty, causes method to evaluate into <code>this</code>
     * @return new {@link Elements} that evaluates into <code>this</code> only
     *         and only if <code>elems</code> evaluates into a non-empty set,
     *         otherwise returns an empty set.
     */
    public abstract T when(Elements elems);

    /**
     * Evaluates into <code>this</code> if and only if <code>elems</code>
     * evaluates into a empty set, otherwise returns an empty set.
     * <p>
     * For instance, if <code>this</code> evaluates into <code>{ A, B }</code>
     * and <code>elems</code> evaluates into <code>{ B, C }</code> then
     * <code>this.unless(elems)</code> evaluates into an empty set.
     * <p>
     * If <code>elems</code> evaluates into an empty set, then
     * <code>this.unless(elems)</code> evaluates into <code>{ A, B }</code>.
     *
     * @param elems
     *            if empty, causes method to evaluate into <code>this</code>
     * @return new {@link Elements} that evaluates into <code>this</code> only
     *         and only if <code>elems</code> evaluates into a empty set,
     *         otherwise returns an empty set.
     */
    public abstract T unless(Elements elems);

}