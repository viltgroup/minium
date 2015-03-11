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
     * @param index An integer indicating the 0-based position of the element.
     *        If negative, it will be counting backwards from the last element in the set.
     * @return this {@link Elements}
     */
    public T eq(int index);

    /**
     *
     * @return this {@link Elements}
     */
    public T first();

    /**
     *
     * @return this {@link Elements}
     */
    public T last();

    /**
     *
     * @return size of the evaluated set
     */
    public int size();
}

