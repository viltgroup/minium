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
package minium.web.internal.actions;

import minium.Dimension;
import minium.Elements;
import minium.Offsets.Offset;

/**
 * The Class MouseInteraction.
 */
public abstract class MouseInteraction extends AbstractWebInteraction {

    protected Offset offset;

    /**
     * Instantiates a new mouse interaction.
     *
     * @param elems the elems
     * @param offset
     */
    public MouseInteraction(Elements elems, Offset offset) {
        super(elems);
        this.offset = offset;
    }

    protected Dimension getSize() {
        org.openqa.selenium.Dimension size = getFirstElement().getSize();
        return new Dimension(size.width, size.height);
    }

}
