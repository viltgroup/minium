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

public interface BasicElements<T extends Elements> extends Elements {

    /**
     * Reduce the set of matched elements to the one at the specified index.
     *
     * @param index
     *            An integer indicating the 0-based position of the element. If
     *            negative, it will be counting backwards from the last element
     *            in the set.
     * @return a new {@link Elements} that evaluates into a single element set
     *         with the element at the corresponding index, or an empty set if
     *         no element is found at that index
     */
    public T eq(int index);

    /**
     * Reduce the set of matched elements to the first one. It's equivalent to
     * <code>this.eq(0)</code>.
     *
     * @return a new {@link Elements} that evaluates into a single element set
     *         with the first element, or an empty set if
     *         <code>this</code> evaluates into an empty set
     */
    public T first();

    /**
     * Reduce the set of matched elements to the last one. It's equivalent to
     * <code>this.eq(-1)</code>.
     *
     * @return a new {@link Elements} that evaluates into a single element set
     *         with the last element, or an empty set if
     *         <code>this</code> evaluates into an empty set
     */
    public T last();

    /**
     * Computes the size of the evaluated set.
     *
     * @return size of the evaluated set
     */
    public int size();
}
