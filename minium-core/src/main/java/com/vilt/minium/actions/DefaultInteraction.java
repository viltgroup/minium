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
package com.vilt.minium.actions;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.vilt.minium.Minium.untilNotEmpty;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;

import com.google.common.collect.Lists;
import com.vilt.minium.CoreWebElements;
import com.vilt.minium.Duration;
import com.vilt.minium.WebElements;
import com.vilt.minium.actions.InteractionEvent.Type;

/**
 * The Class DefaultInteraction.
 */
public abstract class DefaultInteraction implements Interaction {

	private List<InteractionListener> listeners = Lists.newArrayList();
	private WebElements source;
	private Duration timeout;
	
	/**
	 * Instantiates a new default interaction.
	 *
	 * @param elems the elems
	 */
	public DefaultInteraction(WebElements elems) {
		this.source = elems;
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
	
	@Override
	public void waitToPerform() {
		if (source != null) {
			source = ((CoreWebElements<?>) source).wait(timeout, untilNotEmpty());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.Interaction#perform()
	 */
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
	public WebElements getSource() {
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
	protected WebElements getFirst(WebElements elems) {
		WebElements first = ((CoreWebElements<?>) elems).visible().first();
		return first;
	}

	/**
	 * Gets the first element.
	 *
	 * @param elems the elems
	 * @return the first element
	 */
	protected WebElement getFirstElement(WebElements elems) {
		WebElements first = getFirst(elems);
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
		waitToPerform();
		
		trigger(Type.BEFORE, null);
		try {
			doPerform();
			triggerReverse(Type.AFTER_SUCCESS, null);
		} catch (RuntimeException e) {
			boolean retry = triggerReverse(Type.AFTER_FAIL, e);
			if (retry && canRetry) {
				perform(false);
			}
			else {
				throw e;
			}
		}
	}

	private InteractionEvent createInteractionEvent(Type type, Throwable e) {
		checkNotNull(type);
		switch (type) {
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
			List<InteractionListener> globalListeners = ((CoreWebElements<?>) source).configuration().getGlobalListeners();			
			allListeners.addAll(globalListeners);
		}
		allListeners.addAll(listeners);
		
		return allListeners;
	}
}
