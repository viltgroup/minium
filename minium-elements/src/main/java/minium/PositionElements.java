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
package minium;

import minium.Offsets.HorizontalOffset;
import minium.Offsets.Offset;
import minium.Offsets.VerticalOffset;


/**
 * The Interface PositionElements.
 *
 * @param <T> the generic type
 * @author Rui
 */
public interface PositionElements<T extends Elements> extends Elements {

    /**
     * Filters elements above this element.
     *
     * @param fromElems elements to filter
     * @return filtered elements
     */
    public T above(Elements fromElems);

    /**
     * Filters elements at the left of this element.
     *
     * @param fromElems elements to filter
     * @return filtered elements
     */
    public T leftOf(Elements fromElems);

    /**
     * Filters elements below this element.
     *
     * @param fromElems elements to filter
     * @return filtered elements
     */
    public T below(Elements fromElems);

    /**
     * Filters elements at the right of this element.
     *
     * @param fromElems elements to filter
     * @return filtered elements
     */
    public T rightOf(Elements fromElems);

    /**
     * Overlaps.
     *
     * @param fromElems the from elems
     * @return the t
     */
    public T overlaps(Elements fromElems);

    public T relative(String topleft, String bottomright);

    public T relative(Offset topLeftOffset, Offset bottomRightOffset);

    public T target(String center);

    public T target(Offset center);

    public T target(HorizontalOffset horizontal, VerticalOffset vertical);

}
