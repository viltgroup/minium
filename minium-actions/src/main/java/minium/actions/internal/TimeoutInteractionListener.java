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
package minium.actions.internal;

import minium.actions.Duration;
import minium.actions.Interaction;

/**
 * The listener interface for receiving timeoutInteraction events.
 * The class that is interested in processing a timeoutInteraction
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addTimeoutInteractionListener<code> method. When
 * the timeoutInteraction event occurs, that object's appropriate
 * method is invoked.
 *
 * @see TimeoutInteractionEvent
 */
public class TimeoutInteractionListener extends DefaultInteractionListener {

    private Duration timeout;

    /**
     * Instantiates a new timeout interaction listener.
     *
     * @param timeout the timeout
     */
    public TimeoutInteractionListener(Duration timeout) {
        this.timeout = timeout;
    }

    @Override
    protected void onBeforeWaitEvent(BeforeWaitInteractionEvent event) {
        Interaction interaction = event.getInteraction();
        if (interaction instanceof AbstractInteraction) {
            AbstractInteraction defInteraction = (AbstractInteraction) interaction;
            if (defInteraction.getTimeout() == null) {
                defInteraction.setTimeout(timeout);
            }
        }
    }
}
