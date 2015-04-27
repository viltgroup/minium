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
import minium.actions.internal.BeforeInteractionEvent;
import minium.actions.internal.DefaultInteractionListener;
import minium.actions.internal.WaitPredicates;
import minium.actions.internal.Waits;
import minium.web.actions.EnsureInteractionListener;

public class DefaultEnsureInteractionListener extends DefaultInteractionListener implements EnsureInteractionListener {

    private final Elements elems;
    private final boolean existence;
    private String preset;

    public DefaultEnsureInteractionListener(Elements elems, boolean existence) {
        this.elems = elems;
        this.existence = existence;
    }

    @Override
    public EnsureInteractionListener withWaitingPreset(String preset) {
        this.preset = preset;
        return this;
    }

    @Override
    protected void onBeforeEvent(BeforeInteractionEvent event) {
        Waits.waitForPredicate(elems, preset, existence ? WaitPredicates.forExistence() : WaitPredicates.forUnexistence());
        super.onBeforeEvent(event);
    }

}
