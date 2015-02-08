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

import minium.Elements;
import minium.actions.TimeoutException;
import minium.actions.WaitOnTimeoutInteractionListener;

import com.google.common.base.Preconditions;

public class DefaultWaitOnTimeoutInteractionListener extends DefaultInteractionListener implements WaitOnTimeoutInteractionListener {

    private Elements whenElements;
    private Elements unlessElements;
    private Elements forExistenceElements;
    private Elements forUnexistenceElements;
    private String waitingPreset;

    public DefaultWaitOnTimeoutInteractionListener() {
    }

    @Override
    public WaitOnTimeoutInteractionListener when(Elements elems) {
        whenElements = Preconditions.checkNotNull(elems);
        return this;
    }

    @Override
    public WaitOnTimeoutInteractionListener unless(Elements elems) {
        unlessElements = Preconditions.checkNotNull(elems);
        return this;
    }

    @Override
    public WaitOnTimeoutInteractionListener forExistence(Elements elems) {
        forExistenceElements = Preconditions.checkNotNull(elems);
        return this;
    }

    @Override
    public WaitOnTimeoutInteractionListener forUnexistence(Elements elems) {
        forUnexistenceElements = Preconditions.checkNotNull(elems);
        return this;
    }

    @Override
    public WaitOnTimeoutInteractionListener withWaitingPreset(String preset) {
        waitingPreset = preset;
        return this;
    }

    @Override
    protected void onAfterFailEvent(AfterFailInteractionEvent event) {
        Preconditions.checkState(whenElements == null || unlessElements == null, "You can't specify both when and unless methods");
        Preconditions.checkState(whenElements != null || unlessElements != null, "You need to specify when or unless");
        Preconditions.checkState(forExistenceElements != null && forUnexistenceElements == null, "You can't specify both forExistenceElements and forUnexistenceElements");

        if (event.getException() instanceof TimeoutException) {
            boolean willWait = true;

            // we need to check without waiting if whenElements / unlessElements condition is met:
            // - if condition is not met, we will not retry
            // - if condition is met, we wait (with waiting preset) until forExistenceElements / forUnexistenceElements
            //   condition is met. Only then we retry the timed out interaction

            if (unlessElements != null) {
                willWait = WaitPredicates.forUnexistence().apply(unlessElements);
            } else if (whenElements != null) {
                willWait = WaitPredicates.forExistence().apply(whenElements);
            }
            if (!willWait) return;

            if (forExistenceElements == null && forUnexistenceElements == null) {
                if (unlessElements != null) forExistenceElements = unlessElements;
                if (whenElements != null) forUnexistenceElements = whenElements;
            }

            boolean retry = false;
            if (forExistenceElements != null) {
                retry = Waits.waitForPredicateOrTimeout(forExistenceElements, waitingPreset, WaitPredicates.forExistence());
            } else {
                retry = Waits.waitForPredicateOrTimeout(forUnexistenceElements, waitingPreset, WaitPredicates.forUnexistence());
            }

            event.setRetry(retry);
        }
    }

}
