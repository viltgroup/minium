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
package minium.web.internal.actions;

import minium.Elements;
import minium.actions.TimeoutException;
import minium.actions.internal.AfterFailInteractionEvent;
import minium.actions.internal.WaitPredicates;
import minium.actions.internal.Waits;
import minium.web.actions.OnTimeoutInteractionListener;

import com.google.common.base.Preconditions;

public class DefaultOnTimeoutInteractionListener extends AbstractOnExceptionInteractionListener implements OnTimeoutInteractionListener {

    private Elements whenElements;
    private Elements unlessElements;
    private Elements forExistenceElements;
    private Elements forUnexistenceElements;
    private String waitingPreset;

    @SuppressWarnings("unchecked")
    public DefaultOnTimeoutInteractionListener() {
        super(TimeoutException.class, org.openqa.selenium.TimeoutException.class);
    }

    @Override
    public OnTimeoutInteractionListener when(Elements elems) {
        whenElements = Preconditions.checkNotNull(elems);
        return this;
    }

    @Override
    public OnTimeoutInteractionListener unless(Elements elems) {
        unlessElements = Preconditions.checkNotNull(elems);
        return this;
    }

    @Override
    public OnTimeoutInteractionListener waitForExistence(Elements elems) {
        forExistenceElements = Preconditions.checkNotNull(elems);
        return this;
    }

    @Override
    public OnTimeoutInteractionListener waitForUnexistence(Elements elems) {
        forUnexistenceElements = Preconditions.checkNotNull(elems);
        return this;
    }

    @Override
    public OnTimeoutInteractionListener withWaitingPreset(String preset) {
        waitingPreset = preset;
        return this;
    }

    @Override
    protected boolean handle(AfterFailInteractionEvent event) {
        Preconditions.checkState(whenElements == null || unlessElements == null, "You can't specify both when and unless methods");
        Preconditions.checkState(whenElements != null || unlessElements != null, "You need to specify when or unless");
        Preconditions.checkState(forExistenceElements == null || forUnexistenceElements == null, "You can't specify both forExistenceElements and forUnexistenceElements");

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
        if (!willWait) return false;

        if (forExistenceElements == null && forUnexistenceElements == null) {
            if (unlessElements != null) forExistenceElements = unlessElements;
            if (whenElements != null) forUnexistenceElements = whenElements;
        }

        boolean canRetry = false;
        if (forExistenceElements != null) {
            canRetry = Waits.waitForPredicateOrTimeout(forExistenceElements, waitingPreset, WaitPredicates.forExistence());
        } else {
            canRetry = Waits.waitForPredicateOrTimeout(forUnexistenceElements, waitingPreset, WaitPredicates.forUnexistence());
        }
        return canRetry;
    }

}
