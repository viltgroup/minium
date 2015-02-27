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
package minium.web.internal.actions.touch;

import minium.Dimension;
import minium.Elements;
import minium.Offsets.Offset;
import minium.Point;
import minium.web.internal.actions.AbstractWebInteraction;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * The Class TouchInteraction.
 */
public abstract class TouchInteraction extends AbstractWebInteraction {

    /**
     * Instantiates a new touch interaction.
     *
     * @param elems the elems
     */
    public TouchInteraction(Elements elems) {
        super(elems);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.vilt.minium.actions.DefaultInteraction#getActions()
     */
    @Override
    protected TouchActions getActions() {
        return newActions(getFirstElement(getSource()));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.vilt.minium.actions.DefaultInteraction#newActions(org.openqa.selenium
     * .WebElement)
     */
    @Override
    protected TouchActions newActions(WebElement elem) {
        return new TouchActions(((WrapsDriver) elem).getWrappedDriver());
    }

    protected Dimension getSize() {
        org.openqa.selenium.Dimension size = getFirstElement().getSize();
        return new Dimension(size.width, size.height);
    }

    protected Point getTouchPoint(Offset offset) {
        org.openqa.selenium.Point inViewPort = ((Locatable) getFirstElement()).getCoordinates().inViewPort();
        Point viewPort = new Point(inViewPort.x, inViewPort.y);
        Point offsetPoint = offset.offset(getSize());
        Point touchPoint = viewPort.moveBy(offsetPoint.x(), offsetPoint.y());
        return touchPoint;
    }
}
