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
package minium.web;

import minium.PositionElements;


/**
 * The Interface PositionWebElements.
 *
 * @param <T> the generic type
 * @author Rui
 */
public interface PositionWebElements<T extends WebElements> extends WebElements, PositionElements<T> {

    /**
     * Returns elements above this element that match the expression.
     *
     * @param expr the expression to use for matching elements.
     * @return elements
     */
    public T above(String expr);

    /**
     * Returns elements at the left this element that match the expression.
     *
     * @param expr the expression to use for matching elements.
     * @return elements
     */
    public T leftOf(String expr);

    /**
     * Returns elements below this element that match the expression.
     *
     * @param expr the expression to use for matching elements.
     * @return elements
     */
    public T below(String expr);

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
     * @param expr the expr
     * @return the t
     */
    public T overlaps(String expr);

}
