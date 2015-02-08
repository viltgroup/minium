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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Set;

import minium.Elements;
import minium.FreezableElements;
import minium.IterableElements;
import minium.actions.Configuration;
import minium.actions.Configuration.WaitingPreset;
import minium.actions.Duration;
import minium.actions.HasConfiguration;
import minium.actions.Interaction;
import minium.actions.InteractionEvent;
import minium.actions.InteractionEvent.Type;
import minium.actions.InteractionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * The Class DefaultInteraction.
 */
public abstract class AbstractInteraction implements Interaction {

    private static final Logger logger = LoggerFactory.getLogger(AbstractInteraction.class);

    private Set<InteractionListener> listeners = Sets.newLinkedHashSet();
    private Elements source;
    private Duration timeout;
    private Duration interval;
    private String preset;

    public AbstractInteraction(Elements elems) {
        if (elems != null) {
            this.source = elems.is(FreezableElements.class) ? elems.as(FreezableElements.class).freeze() : elems;
        }
    }

    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public Duration getInterval() {
        return interval;
    }

    public void setInterval(Duration interval) {
        this.interval = interval;
    }

    public void setWaitingPreset(String preset) {
        this.preset = preset;
    }

    public String getWaitingPreset() {
        return preset;
    }

    @Override
    public void waitToPerform() {
        waitFor(WaitPredicates.forExistence());
    }

    /*
     * (non-Javadoc)
     *
     * @see com.vilt.minium.actions.Interaction#perform()
     */
    @Override
    public void perform() {
        perform(true);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.vilt.minium.actions.Interaction#registerListener(com.vilt.minium.actions.InteractionListener)
     */
    @Override
    public void registerListener(InteractionListener listener) {
        listeners.add(listener);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.vilt.minium.actions.Interaction#unregisterListener(com.vilt.minium.actions.InteractionListener)
     */
    @Override
    public void unregisterListener(InteractionListener listener) {
        listeners.remove(listener);
    }

    protected void waitFor(Predicate<? super Elements> predicate) {
        if (source != null) {
            if (preset != null) {
                wait(source, preset, predicate);
            } else {
                wait(source, timeout, interval, predicate);
            }
        }
    }

    protected void waitOrTimeoutFor(Predicate<? super Elements> predicate) {
        if (source != null) {
            if (preset != null) {
                waitOrTimeout(source, preset, predicate);
            } else {
                waitOrTimeout(source, timeout, interval, predicate);
            }
        }
    }

    /**
     * Gets the source.
     *
     * @return the source
     */
    protected Elements getSource() {
        return source;
    }

    /**
     * Do perform.
     */
    protected abstract void doPerform();

    /**
     * Gets the first.
     *
     * @param elems
     *            the elems
     * @return the first
     */
    protected Elements getFirst(Elements elems) {
        @SuppressWarnings("unchecked")
        IterableElements<Elements> iterableElems = elems.as(IterableElements.class);
        return Iterables.getFirst(iterableElems, null);
    }

    /**
     * Trigger.
     *
     * @param type
     *            the type
     */
    protected boolean trigger(InteractionEvent.Type type, Throwable e) {
        return trigger(getAllListeners(), type, e);
    }

    /**
     * Trigger reverse.
     *
     * @param type
     *            the type
     */
    protected boolean triggerReverse(Type type, Throwable e) {
        return trigger(Lists.reverse(getAllListeners()), type, e);
    }

    /**
     * Trigger.
     *
     * @param listeners
     *            the listeners
     * @param type
     *            the type
     */
    protected boolean trigger(List<InteractionListener> listeners, Type type, Throwable e) {
        if (listeners.isEmpty())
            return false;
        InteractionEvent event = createInteractionEvent(type, e);
        for (InteractionListener listener : listeners) {
            listener.onEvent(event);
        }
        if (event instanceof AfterFailInteractionEvent)
            return ((AfterFailInteractionEvent) event).isRetry();
        return false;
    }

    private void perform(boolean canRetry) {
        trigger(Type.BEFORE_WAIT, null);
        try {
            waitToPerform();
            trigger(Type.BEFORE, null);
            doPerform();
            triggerReverse(Type.AFTER_SUCCESS, null);
        } catch (RuntimeException e) {
            // always call after fail
            boolean retry = triggerReverse(Type.AFTER_FAIL, e);

            // we first need to check if thread has been interrupted
            if (Thread.interrupted()) {
                Thread.currentThread().interrupt();
                throw e;
            }

            // otherwise, retry if the case
            if (retry && canRetry) {
                logger.debug("Interaction was marked as retriable, let's retry it");
                perform(false);
            } else {
                throw e;
            }
        }
    }

    protected InteractionEvent createInteractionEvent(Type type, Throwable e) {
        checkNotNull(type);
        switch (type) {
        case BEFORE_WAIT:
            return new BeforeWaitInteractionEvent(source, this);
        case BEFORE:
            return new BeforeInteractionEvent(source, this);
        case AFTER_FAIL:
            return new AfterFailInteractionEvent(source, this, e);
        case AFTER_SUCCESS:
            return new AfterSuccessInteractionEvent(source, this);
        }

        // shouldn't occurr
        throw new IllegalArgumentException("Type must be not null and valid");
    }

    private List<InteractionListener> getAllListeners() {
        List<InteractionListener> allListeners = Lists.newArrayList();
        if (source != null && source.is(HasConfiguration.class)) {
            Iterable<InteractionListener> globalListeners = source.as(HasConfiguration.class).configure().interactionListeners();
            allListeners.addAll(Lists.newArrayList(globalListeners));
        }
        allListeners.addAll(listeners);

        return allListeners;
    }

    protected void wait(Elements webElements, String preset, Predicate<? super Elements> predicate) {
        WaitingPreset waitingPreset = configure().waitingPreset(preset);
        wait(webElements, waitingPreset.timeout(), waitingPreset.interval(), predicate);
    }

    protected void wait(Elements elems, Duration timeout, Duration interval, Predicate<? super Elements> predicate) {
        Waits.waitForPredicate(elems, timeout, interval, predicate);

    }

    protected void waitOrTimeout(Elements webElements, String preset, Predicate<? super Elements> predicate) {
        WaitingPreset waitingPreset = configure().waitingPreset(preset);
        waitOrTimeout(webElements, waitingPreset.timeout(), waitingPreset.interval(), predicate);
    }

    protected void waitOrTimeout(Elements elems, Duration timeout, Duration interval, Predicate<? super Elements> predicate) {
        Waits.waitForPredicateOrTimeout(elems, timeout, interval, predicate);
    }

    private Configuration configure() {
        return getSource().as(HasConfiguration.class).configure();
    }
}
