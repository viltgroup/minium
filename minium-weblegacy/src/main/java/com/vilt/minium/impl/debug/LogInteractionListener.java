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
package com.vilt.minium.impl.debug;

import minium.web.internal.actions.AfterFailInteractionEvent;
import minium.web.internal.actions.AfterSuccessInteractionEvent;
import minium.web.internal.actions.DefaultInteractionListener;

/**
 * The listener interface for receiving logInteraction events.
 * The class that is interested in processing a logInteraction
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addLogInteractionListener<code> method. When
 * the logInteraction event occurs, that object's appropriate
 * method is invoked.
 *
 * @see LogInteractionEvent
 */
public class LogInteractionListener extends DefaultInteractionListener {

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.DefaultInteractionListener#onAfterInteractingEvent(com.vilt.minium.actions.InteractionEvent)
     */
    @Override
    protected void onAfterSuccessEvent(AfterSuccessInteractionEvent event) {
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.DefaultInteractionListener#onAfterFailingEvent(com.vilt.minium.actions.InteractionEvent)
     */
    @Override
    protected void onAfterFailEvent(AfterFailInteractionEvent event) {
    }

}
