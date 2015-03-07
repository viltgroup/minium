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

import minium.Elements;
import minium.actions.TimeoutException;
import minium.actions.internal.AfterFailInteractionEvent;
import minium.actions.internal.DefaultInteractionListener;
import minium.actions.internal.WaitForExistenceInteraction;
import minium.actions.internal.WaitingPresetInteractionListener;
import minium.web.internal.InternalWebElements;

import com.google.common.collect.Iterables;

public class RetryAfterWaitingWhileEmptyInteractionListener extends DefaultInteractionListener {

    private final Elements waitElements;
    private final String waitingPreset;

    public RetryAfterWaitingWhileEmptyInteractionListener(Elements source, String waitingPreset) {
        this.waitElements = source;
        this.waitingPreset = waitingPreset;
    }

    @Override
    protected void onAfterFailEvent(AfterFailInteractionEvent event) {
        if (!(event.getException() instanceof TimeoutException)) return;

        if (Iterables.isEmpty(waitElements.as(InternalWebElements.class).wrappedNativeElements())) {
            try {
                WaitForExistenceInteraction interaction = new WaitForExistenceInteraction(waitElements, null);
                interaction.registerListener(new WaitingPresetInteractionListener(waitingPreset));
                interaction.perform();
                event.setRetry(true);
            } catch (Exception e) {
                // we tried...
            }
        }
    }
}
