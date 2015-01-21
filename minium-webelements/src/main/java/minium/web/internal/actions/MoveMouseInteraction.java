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
package minium.web.internal.actions;

import minium.Elements;
import minium.Offsets.Offset;
import minium.Point;

import org.openqa.selenium.WebElement;

/**
 * The Class MoveMouseInteraction.
 */
public class MoveMouseInteraction extends MouseInteraction {

    /**
     * Instantiates a new move mouse interaction.
     *
     * @param source the source
     * @param xOffset the x offset
     * @param yOffset the y offset
     */
    public MoveMouseInteraction(Elements source, Offset offset) {
        super(source, offset);
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
     */
    @Override
    protected void doPerform() {
        WebElement source = isSourceDocumentRoot() ? null : getFirstElement();
        if (offset != null) {
            Point offsetPoint = offset.offset(getSize());
            getActions().moveToElement(source, offsetPoint.x(), offsetPoint.y()).perform();
        } else {
            getActions().moveToElement(source).perform();
        }
    }
}
