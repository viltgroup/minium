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
package minium.actions.internal;

import java.util.Set;

import minium.Elements;
import minium.actions.AsyncInteraction;
import minium.actions.HasInteractionListeners;
import minium.actions.Interactable;
import minium.actions.Interaction;
import minium.actions.InteractionListener;
import minium.internal.Chainable;

public abstract class AbstractInteractable<T extends Interactable<?>> extends Chainable<T> implements Interactable<T> {

    public AbstractInteractable() {
        super();
    }

    @Override
    public T perform(Interaction interaction) {
        if (this.is(HasInteractionListeners.class)) {
            @SuppressWarnings("unchecked")
            Set<InteractionListener> interactionListeners = this.as(HasInteractionListeners.class).interactionListeners();
            for (InteractionListener listener : interactionListeners) {
                interaction.registerListener(listener);
            }
        }
        interaction.perform();
        return myself();
    }

    @Override
    public T performAndWait(AsyncInteraction interaction) {
        perform(interaction);
        interaction.waitUntilCompleted();
        return myself();
    }

    protected Elements getSource() {
        return this.as(Elements.class);
    }
}