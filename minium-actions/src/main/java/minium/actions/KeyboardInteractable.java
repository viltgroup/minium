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
package minium.actions;

/**
 * Keyboard interactions can be performed using this interactable interface. It "hides"
 * keyboard interactions under its methods. It mimics most of keyboard actions in
 * org.openqa.selenium.interactions.Actions.
 *
 * @author rui.figueira
 *
 * @param <T> this {@link Interactable} for chainable calls
 */
public interface KeyboardInteractable<T extends Interactable<?>> extends Interactable<T> {

    /**
     * Clears all input text in the first matched field.
     *
     * @return this {@link Interactable}
     */
    public T clear();

    /**
     * Performs a modifier key press. Does not release the modifier key - subsequent interactions
     * may assume it's kept pressed. Note that the modifier key is never released implicitly -
     * either <code>keyUp(theKey)</code> or <code>sendKeys(Keys.NULL)</code> must be called to
     * release the modifier.
     *
     * @param keys either {@code Keys.SHIFT}, {@code Keys.ALT}> or {@code Keys.CONTROL}.
     *  If the provided key is none of those, {@link IllegalArgumentException} is thrown
     * @return this {@link Interactable}
     */
    public T keyDown(Keys keys);

    /**
     * Performs a modifier key press after focusing on an element.
     *
     * @param keys either {@code Keys.SHIFT}, {@code Keys.ALT}> or {@code Keys.CONTROL}.
     *  If the provided key is none of those, {@link IllegalArgumentException} is thrown
     * @return this {@link Interactable}
     */
    public T keyUp(Keys keys);

    /**
     * Sends keys to this element.
     *
     * @param keys keys to send
     * @return this {@link Interactable}
     */
    public T sendKeys(CharSequence ... keys);

    /**
     * Sends keys to this element.
     *
     * @param keys keys to send
     * @return this {@link Interactable}
     */
    public T sendKeys(CharSequence keys);

    /**
     * Same as {@link #sendKeys(CharSequence)}
     *
     * @param text text to type
     * @return this {@link Interactable}
     */
    public T type(CharSequence text);

    /**
     * Same as {@link #clear()} followed by {@link #sendKeys(CharSequence)}
     *
     * @param text text to type
     * @return this {@link Interactable}
     */
    public T fill(CharSequence text);
}
