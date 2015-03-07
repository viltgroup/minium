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

import java.util.Arrays;
import java.util.Set;

import minium.Elements;
import minium.actions.HasInteractionListeners;
import minium.actions.Interactable;
import minium.actions.InteractionListener;
import minium.internal.Chainable;
import minium.internal.HasParent;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.common.collect.Sets;

public class DefaultHasInteractionListeners<T extends Interactable<?>> extends Chainable<T> implements HasInteractionListeners<T> {
    private Set<InteractionListener> listeners = Sets.newLinkedHashSet();

    @Override
    public T immediately() {
        return withWaitingPreset("immediate");
    }

    @Override
    public T withWaitingPreset(String waitingPreset) {
        return with(new WaitingPresetInteractionListener(waitingPreset));
    }

    @Override
    public T with(InteractionListener listener) {
        return with(new InteractionListener[] { listener });
    }

    @Override
    public T with(InteractionListener... listeners) {
        this.listeners.addAll(Arrays.asList(listeners));
        return myself();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<InteractionListener> interactionListeners() {
        Builder<InteractionListener> builder = ImmutableSet.<InteractionListener>builder();
        if (this.is(HasParent.class)) {
            Elements parent = this.as(HasParent.class).parent();
            if (parent.is(HasInteractionListeners.class)) {
                builder.addAll(parent.as(HasInteractionListeners.class).interactionListeners());
            }
        }
        return builder.addAll(listeners).build();
    }

}