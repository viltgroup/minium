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
package com.vilt.minium.impl.actions;

import com.google.common.base.Preconditions;
import com.vilt.minium.CoreWebElements;
import com.vilt.minium.TimeoutException;
import com.vilt.minium.actions.RetryOnTimeoutInteractionListener;
import com.vilt.minium.impl.WaitPredicates;
import com.vilt.minium.impl.Waits;

public class DefaultRetryOnTimeoutInteractionListener extends DefaultInteractionListener implements RetryOnTimeoutInteractionListener {

    private CoreWebElements<?> emptyElements;
    private CoreWebElements<?> notEmptyElements;
    private String waitingPreset;

    public DefaultRetryOnTimeoutInteractionListener() {
    }

    @Override
    public RetryOnTimeoutInteractionListener withWaitingPreset(String preset) {
        waitingPreset = preset;
        return this;
    }

    @Override
    public RetryOnTimeoutInteractionListener whenEmpty(CoreWebElements<?> elems) {
        Preconditions.checkState(notEmptyElements == null, "You can't specify both whenEmpty and whenNotEmpty methods");
        emptyElements = Preconditions.checkNotNull(elems);
        return this;
    }

    @Override
    public RetryOnTimeoutInteractionListener whenNotEmpty(CoreWebElements<?> elems) {
        Preconditions.checkState(emptyElements == null, "You can't specify both whenEmpty and whenNotEmpty methods");
        notEmptyElements = Preconditions.checkNotNull(elems);
        return this;
    }

    @Override
    protected void onAfterFailEvent(AfterFailInteractionEvent event) {
        if (event.getException() instanceof TimeoutException) {
            boolean retry = true;
            if (notEmptyElements != null) {
                retry = Waits.waitForPredicateOrTimeout(notEmptyElements, waitingPreset, WaitPredicates.whileEmpty());
            } else if (emptyElements != null) {
                retry = Waits.waitForPredicateOrTimeout(emptyElements, waitingPreset, WaitPredicates.whileNotEmpty());
            }
            event.setRetry(retry);
        }
    }

}
