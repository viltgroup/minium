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
package minium.web.actions;

import minium.actions.Interactable;

/**
 * Web-specific interactions that can be performed using this interactable interface. It "hides"
 * web-specific interactions under its methods.
 *
 * @author rui.figueira
 *
 * @param <T> this {@link Interactable} for chainable calls
 */
public interface WebInteractable<T extends Interactable<?>> extends Interactable<T> {

    /**
     * Submits the corresponding form. Mimics {@link org.openqa.selenium.WebElement#submit()}.
     *
     * @return this {@link Interactable}
     */
    public T submit();

    /**
     * Checks the corresponding input, in case it is unchecked.
     *
     * @return this {@link Interactable}
     */
    public T check();

    /**
     * Unchecks the corresponding input, in case it is checked.
     *
     * @return this {@link Interactable}
     */
    public T uncheck();

    /**
     * Selects the option with the corresponding text.
     *
     * @param text text of the option to select
     * @return this {@link Interactable}
     */
    public T select(String text);

    /**
     * Deselects the option with the corresponding text.
     *
     * @param text text of the option to deselect
     * @return this {@link Interactable}
     */
    public T deselect(String text);

    /**
     * Selects the option with the corresponding value.
     *
     * @param value value of the option to select
     * @return this {@link Interactable}
     */
    public T selectVal(String value);

    /**
     * Deselects the option with the corresponding value.
     *
     * @param value value of the option to deselect
     * @return this {@link Interactable}
     */
    public T deselectVal(String value);

    /**
     * Selects all options (for multi-select field).
     *
     * @return this {@link Interactable}
     */
    public T selectAll();

    /**
     * Deselects all options (for multi-select field).
     *
     * @return this {@link Interactable}
     */
    public T deselectAll();

    /**
     * Scrolls the corresponding element into view.
     *
     * @return this {@link Interactable}
     */
    public T scrollIntoView();

    /**
     * Closes the browser window for the corresponding element.
     *
     * @return this {@link Interactable}
     */
    public T close();
}
