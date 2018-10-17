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

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.interactions.Actions;

import com.google.common.collect.Iterables;

import minium.Elements;
import minium.actions.internal.AbstractInteraction;
import minium.web.internal.InternalWebElements;
import minium.web.internal.drivers.DocumentWebElement;

/**
 * The Class DefaultInteraction.
 */
public abstract class AbstractWebInteraction extends AbstractInteraction {

    public AbstractWebInteraction(Elements elems) {
        super(elems);
    }

    protected boolean isSourceDocumentRoot() {
        return getSource().as(InternalWebElements.class).isDocumentRoots();
    }

    /**
     * Gets the first element.
     *
     * @param elems the elems
     * @return the first element
     */
    protected WebElement getFirstElement(Elements elems) {
        DocumentWebElement documentWebElement = Iterables.getFirst(elems.as(InternalWebElements.class).wrappedNativeElements(), null);
        return documentWebElement == null ? null : documentWebElement.getWrappedWebElement();
    }

    /**
     * New actions.
     *
     * @param elem the elem
     * @return the actions
     */
    protected Actions newActions(WebElement elem) {
        return new Actions(((WrapsDriver) elem).getWrappedDriver());
    }

    /**
     * Gets the first element.
     *
     * @return the first element
     */
    protected WebElement getFirstElement() {
        return getFirstElement(getSource());
    }

    /**
     * Gets the actions.
     *
     * @return the actions
     */
    protected Actions getActions() {
        return newActions(getFirstElement(getSource()));
    }
}
