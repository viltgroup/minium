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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.vilt.minium.impl.WaitPredicates.whileEmpty;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.vilt.minium.Configuration;
import com.vilt.minium.Configuration.WaitingPreset;
import com.vilt.minium.CoreWebElements;
import com.vilt.minium.Duration;
import com.vilt.minium.TimeoutException;
import com.vilt.minium.actions.Interaction;
import com.vilt.minium.actions.InteractionEvent;
import com.vilt.minium.actions.InteractionEvent.Type;
import com.vilt.minium.actions.InteractionListener;
import com.vilt.minium.impl.DocumentRootWebElementsImpl;
import com.vilt.minium.impl.WebElementsDriverProvider;
import com.vilt.minium.impl.WebElementsWait;

/**
 * The Class DefaultInteraction.
 */
public abstract class DefaultInteraction implements Interaction {

    private static final Logger logger = LoggerFactory.getLogger(DefaultInteraction.class);

    private List<InteractionListener> listeners = Lists.newArrayList();
    private CoreWebElements<?> source;
    private Duration timeout;
    private Duration interval;
    private String preset;

    /**
     * Instantiates a new default interaction.
     *
     * @param elems the elems
     */
    public DefaultInteraction(CoreWebElements<?> elems) {
        if (elems != null) {
            this.source = elems;
            if (!(this.source instanceof DocumentRootWebElementsImpl)) {
                this.source = this.source.displayed();
            }
        }
    }

    /**
     * Sets the timeout.
     *
     * @param timeout the new timeout
     */
    public void setTimeout(Duration timeout) {
        this.timeout = timeout;
    }

    /**
     * Gets the timeout.
     *
     * @return the timeout
     */
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
        if (source != null) {
            if (preset != null) {
                wait(source, preset, whileEmpty());
            } else {
                wait(source, timeout, interval, whileEmpty());
            }
        }
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.Interaction#perform()
     */
    @Override
    public void perform() {
        perform(true);
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.Interaction#registerListener(com.vilt.minium.actions.InteractionListener)
     */
    @Override
    public void registerListener(InteractionListener listener) {
        listeners.add(listener);
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.Interaction#unregisterListener(com.vilt.minium.actions.InteractionListener)
     */
    @Override
    public void unregisterListener(InteractionListener listener) {
        listeners.remove(listener);
    }

    /**
     * Gets the source.
     *
     * @return the source
     */
    protected CoreWebElements<?> getSource() {
        return source;
    }

    /**
     * Do perform.
     */
    protected abstract void doPerform();

    /**
     * Gets the first.
     *
     * @param elems the elems
     * @return the first
     */
    protected CoreWebElements<?> getFirst(CoreWebElements<?> elems) {
        CoreWebElements<?> first = elems.displayed().first();
        return first;
    }

    /**
     * Gets the first element.
     *
     * @param elems the elems
     * @return the first element
     */
    protected WebElement getFirstElement(CoreWebElements<?> elems) {
        CoreWebElements<?> first = getFirst(elems);
        return first.get(0);
    }

    /**
     * New actions.
     *
     * @param elem the elem
     * @return the actions
     */
    protected Actions newActions(WebElement elem) {
        return new Actions(((WrapsDriver) elem).getWrappedDriver());
    }

    /**
     * Gets the first element.
     *
     * @return the first element
     */
    protected WebElement getFirstElement() {
        return getFirstElement(source);
    }

    /**
     * Gets the actions.
     *
     * @return the actions
     */
    protected Actions getActions() {
        return newActions(getFirstElement(source));
    }

    /**
     * Trigger.
     *
     * @param type the type
     */
    protected boolean trigger(Type type, Throwable e) {
        return trigger(getAllListeners(), type, e);
    }

    /**
     * Trigger reverse.
     *
     * @param type the type
     */
    protected boolean triggerReverse(Type type, Throwable e) {
        return trigger(Lists.reverse(getAllListeners()), type, e);
    }

    /**
     * Trigger.
     *
     * @param listeners the listeners
     * @param type the type
     */
    protected boolean trigger(List<InteractionListener> listeners, Type type, Throwable e) {
        if (listeners.isEmpty()) return false;
        InteractionEvent event = createInteractionEvent(type, e);
        for (InteractionListener listener : listeners) {
            listener.onEvent(event);
        }
        if (event instanceof AfterFailInteractionEvent) return ((AfterFailInteractionEvent) event).isRetry();
        return false;
    }

    private void perform(boolean canRetry) {
        trigger(Type.BEFORE_WAIT, null);
        waitToPerform();
        trigger(Type.BEFORE, null);
        try {
            doPerform();
            triggerReverse(Type.AFTER_SUCCESS, null);
        } catch (RuntimeException e) {
            boolean retry = triggerReverse(Type.AFTER_FAIL, e);
            if (retry && canRetry) {
                logger.debug("Interaction was marked as retriable, let's retry it");
                perform(false);
            } else {
                throw e;
            }
        }
    }

    private InteractionEvent createInteractionEvent(Type type, Throwable e) {
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
        if (source != null) {
            Iterable<InteractionListener> globalListeners = source.as(WebElementsDriverProvider.class).configure().interactionListeners();
            allListeners.addAll(Lists.newArrayList(globalListeners));
        }
        allListeners.addAll(listeners);

        return allListeners;
    }

    protected boolean isSourceDocumentRoot() {
        return getSource() instanceof DocumentRootWebElementsImpl;
    }

    protected void wait(CoreWebElements<?> webElements, String preset, Predicate<? super CoreWebElements<?>> predicate) {
        WaitingPreset waitingPreset = configure().waitingPreset(preset);
        wait(webElements, waitingPreset.timeout(), waitingPreset.interval(), predicate);
    }

    protected void wait(CoreWebElements<?> webElements, Duration timeout, Duration interval, Predicate<? super CoreWebElements<?>> predicate) {
        if (timeout == null) {
            timeout = configure().defaultTimeout();
        }
        if (interval == null) {
            interval = configure().defaultInterval();
        }

        WebElementsWait wait = getWait(timeout, interval);

        Function<? super CoreWebElements<?>, Boolean> function = Functions.forPredicate(predicate);
        wait.until(function);
    }

    protected void waitOrTimeout(CoreWebElements<?> webElements, String preset, Predicate<? super CoreWebElements<?>> predicate) {
        WaitingPreset waitingPreset = configure().waitingPreset(preset);
        waitOrTimeout(webElements, waitingPreset.timeout(), waitingPreset.interval(), predicate);
    }

    protected void waitOrTimeout(CoreWebElements<?> webElements, Duration timeout, Duration interval, Predicate<? super CoreWebElements<?>> predicate) {
        if (timeout == null) {
            timeout = configure().defaultTimeout();
        }
        if (interval == null) {
            interval = configure().defaultInterval();
        }

        WebElementsWait wait = getWait(timeout, interval);

        Function<? super CoreWebElements<?>, Boolean> function = Functions.forPredicate(predicate);

        try {
            wait.until(function);
        } catch (TimeoutException e) {
            // ignore
        }
    }

    private WebElementsWait getWait(Duration timeout, Duration interval) {
        return new WebElementsWait(getSource(), timeout, interval);
    }

    private Configuration configure() {
        return getSource().as(WebElementsDriverProvider.class).rootWebDriver().configure();
    }
}
