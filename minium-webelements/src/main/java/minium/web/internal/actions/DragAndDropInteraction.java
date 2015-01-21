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

import minium.Dimension;
import minium.Elements;
import minium.Offsets.Offset;
import minium.Point;

import org.openqa.selenium.WebElement;

/**
 * The Class DragAndDropInteraction.
 */
public class DragAndDropInteraction extends MouseInteraction {

    private final Elements target;
    private final Offset targetOffset;

    /**
     * Instantiates a new drag and drop interaction.
     *
     * @param source the source
     * @param target the target
     */
    public DragAndDropInteraction(Elements source, Offset sourceOffset, Elements target, Offset targetOffset) {
        super(source, sourceOffset);
        this.target = target;
        this.targetOffset = targetOffset;
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
     */
    @Override
    protected void doPerform() {
        WebElement targetElem = getFirstElement(target);
        if (targetOffset == null) {
            getActions().dragAndDrop(getFirstElement(), targetElem).perform();
        } else {
            org.openqa.selenium.Dimension size = targetElem.getSize();
            Dimension dimension = new Dimension(size.width, size.height);
            Point offsetPoint = targetOffset.offset(dimension);
            getActions().dragAndDropBy(getFirstElement(), offsetPoint.x(), offsetPoint.y()).perform();
        }
    }
}
