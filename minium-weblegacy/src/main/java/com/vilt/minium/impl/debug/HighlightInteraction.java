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

import java.util.concurrent.TimeUnit;

import minium.actions.Duration;
import minium.web.internal.actions.AsyncTimeElapsedInteraction;

import com.vilt.minium.DefaultWebElements;
import com.vilt.minium.debug.DebugWebElements;

/**
 * The Class HighlightInteraction.
 */
public class HighlightInteraction extends AsyncTimeElapsedInteraction {

    /**
     * Instantiates a new highlight interaction.
     *
     * @param elems the elems
     */
    public HighlightInteraction(DefaultWebElements elems) {
        this(elems, new Duration(2, TimeUnit.SECONDS));
    }

    public HighlightInteraction(DefaultWebElements elems, Duration duration) {
        super(elems, duration);
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
     */
    @Override
    protected void doPerform() {
        super.doPerform();
        ((DebugWebElements) getSource()).highlight();
    }
}
