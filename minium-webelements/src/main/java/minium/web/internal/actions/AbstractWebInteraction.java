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
import minium.actions.internal.AbstractInteraction;
import minium.web.DocumentRoots;
import minium.web.internal.InternalWebElements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;

import com.google.common.collect.Iterables;

/**
 * The Class DefaultInteraction.
 */
public abstract class AbstractWebInteraction extends AbstractInteraction {

    public AbstractWebInteraction(Elements elems) {
        super(elems);
    }

    protected boolean isSourceDocumentRoot() {
        return getSource().is(DocumentRoots.class);
    }

    /**
     * Gets the first element.
     *
     * @param elems the elems
     * @return the first element
     */
    protected WebElement getFirstElement(Elements elems) {
        return Iterables.getFirst(elems.as(InternalWebElements.class).wrappedNativeElements(), null);
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
