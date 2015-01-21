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
package com.vilt.minium.impl.tips;

import static java.util.concurrent.TimeUnit.SECONDS;
import minium.actions.Duration;
import minium.actions.Interaction;
import minium.web.internal.actions.AfterInteractionEvent;
import minium.web.internal.actions.BeforeInteractionEvent;
import minium.web.internal.actions.DefaultInteractionListener;
import minium.web.internal.actions.MouseInteraction;
import minium.web.internal.actions.SelectionInteraction;

import com.vilt.minium.DefaultWebElements;

/**
 * The listener interface for receiving tipInteraction events. The class that is
 * interested in processing a tipInteraction event implements this interface,
 * and the object created with that class is registered with a component using
 * the component's <code>addTipInteractionListener<code> method. When
 * the tipInteraction event occurs, that object's appropriate
 * method is invoked.
 *
 * @see TipInteractionEvent
 */
public class TipInteractionListener extends DefaultInteractionListener {

    private String tip;
    private Duration duration;
    private TipInteraction tipInteraction;

    /**
     * Instantiates a new tip interaction listener.
     *
     * @param tip
     *            the tip
     */
    public TipInteractionListener(String tip) {
        this(tip, new Duration(5, SECONDS));
    }

    /**
     * Instantiates a new tip interaction listener.
     *
     * @param tip
     *            the tip
     * @param time
     *            the time
     * @param timeUnit
     *            the time unit
     */
    public TipInteractionListener(String tip, Duration duration) {
        this.tip = tip;
        this.duration = duration;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.vilt.minium.actions.DefaultInteractionListener#onBeforeEvent(com.
     * vilt.minium.actions.InteractionEvent)
     */
    @Override
    protected void onBeforeEvent(BeforeInteractionEvent event) {
        tipInteraction = new TipInteraction(event.getSource().as(DefaultWebElements.class), tip, duration);
        tipInteraction.perform();

        if (waitBefore(event.getInteraction())) {
            tipInteraction.waitUntilCompleted();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.vilt.minium.actions.DefaultInteractionListener#onAfterEvent(com.vilt
     * .minium.actions.InteractionEvent)
     */
    @Override
    protected void onAfterEvent(AfterInteractionEvent event) {
        if (!waitBefore(event.getInteraction())) {
            tipInteraction.waitUntilCompleted();
        }
    }

    /**
     * Wait before.
     *
     * @param interaction
     *            the interaction
     * @return true, if successful
     */
    protected boolean waitBefore(Interaction interaction) {
        return interaction instanceof MouseInteraction || interaction instanceof SelectionInteraction;
    }
}
