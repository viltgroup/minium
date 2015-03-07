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

import minium.AsIs;

/**
 * <p>Objects that can perform interactions are known as Interactable. Typically, all Elements / WebElements
 * are interactable. Interactable interfaces normally "hide" interactions behind methods like
 * .click() or .fill().</p>
 *
 * <p>This is the root interface for all other {@link Interactable} interfaces such as {@link MouseInteractable} or
 * {@link KeyboardInteractable}. It makes available generic methods to perform interactions on the corresponding
 * elements.</p>
 *
 * @author rui.figueira
 *
 * @param <T> this {@link Interactable} for chainable calls
 */
public interface Interactable<T extends Interactable<?>> extends AsIs {

    /**
     * Performs the specified interaction on the corresponding elements.
     *
     * @param interaction the interaction to perform
     */
    public abstract T perform(Interaction interaction);

    /**
     * Performs the specified asynchronous interaction on the corresponding elements, and waits until it completes.
     *
     * @param interaction the asynchronous interaction
     */
    public abstract T performAndWait(AsyncInteraction interaction);
}
