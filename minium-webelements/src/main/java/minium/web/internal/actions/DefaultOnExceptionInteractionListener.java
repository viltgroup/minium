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

import static com.google.common.base.Throwables.getCausalChain;
import static com.google.common.collect.FluentIterable.from;

import java.util.Set;

import minium.actions.Interaction;
import minium.actions.InteractionListener;
import minium.actions.internal.AbstractInteraction;
import minium.actions.internal.AfterFailInteractionEvent;
import minium.actions.internal.DefaultInteractionListener;
import minium.web.actions.OnExceptionInteractionListener;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;

public class DefaultOnExceptionInteractionListener extends DefaultInteractionListener implements OnExceptionInteractionListener {

    private Set<Class<? extends Throwable>> exceptions;

    // by default, we retry
    private boolean retry = true;

    @SuppressWarnings("unchecked")
    public DefaultOnExceptionInteractionListener(Class<? extends Throwable> ... exceptions) {
        this.exceptions = ImmutableSet.copyOf(exceptions);
    }

    protected boolean handle(AfterFailInteractionEvent event) {
        Interaction interaction = event.getInteraction();
        if (interaction instanceof AbstractInteraction) {
            ((AbstractInteraction) interaction).refreeze();
        }
        return true;
    }

    @Override
    protected final void onAfterFailEvent(AfterFailInteractionEvent event) {
        boolean canHandleException = from(getCausalChain(event.getException())).anyMatch(new Predicate<Throwable>() {
            @Override
            public boolean apply(Throwable throwable) {
                return exceptions.contains(throwable.getClass());
            }
        });
        if (canHandleException) {
            boolean handle = handle(event);
            if (handle && retry) {
                event.setRetry(retry);
            }
        }
    }

    @Override
    public InteractionListener thenAbort() {
        retry = false;
        return this;
    }

    @Override
    public InteractionListener thenRetry() {
        retry = true;
        return this;
    }
}
