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
package com.vilt.minium;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vilt.minium.actions.AfterFailInteractionEvent;
import com.vilt.minium.actions.InteractionListener;
import com.vilt.minium.impl.BaseWebElementsImpl;
import com.vilt.minium.impl.actions.DefaultInteractionListener;

/**
 * The Class Configuration.
 */
public class Configuration implements Serializable {

    private class TimeoutAndInterval {

        private final Duration timeout;
        private final Duration interval;
        
        public TimeoutAndInterval(Duration timeout, Duration interval) {
            this.timeout = timeout;
            this.interval = interval;
        }
        
        public Duration getTimeout() {
            return timeout;
        }
        
        public Duration getInterval() {
            return interval;
        }
    }
    
	private class ExceptionHandlersInteractionListenerAdapter extends DefaultInteractionListener {
		
		@Override
		protected void onAfterFailEvent(AfterFailInteractionEvent event) {
			BaseWebElementsImpl<?> source = (BaseWebElementsImpl<?>) event.getSource();
			if (source == null) return;
			for (ExceptionHandler handler : globalExceptionHandlers) {
				handler.handle(source, event.getException());
			}
		}
	}
	
	private static final long serialVersionUID = -6096136074620998211L;

	private TimeoutAndInterval defaultTimeoutAndInterval  = 
	        new TimeoutAndInterval(new Duration(5, TimeUnit.SECONDS), new Duration(1, TimeUnit.SECONDS));

	private Map<String, TimeoutAndInterval> durationPresets = Maps.newHashMap();
	
	/** The global listeners. */
	protected final List<InteractionListener> globalListeners = Lists.newArrayList();
	protected final List<ExceptionHandler> globalExceptionHandlers = Lists.newArrayList();

	public Configuration() {
		registerInteractionListener(new ExceptionHandlersInteractionListenerAdapter());
	}
	
	/**
	 * Gets the default timeout.
	 *
	 * @return the default timeout
	 */
	public Duration getDefaultTimeout() {
		return defaultTimeoutAndInterval.getTimeout();
	}

	/**
	 * Default timeout.
	 *
	 * @param defaultTimeout the default timeout
	 * @return the configuration
	 */
	public Configuration defaultTimeout(Duration defaultTimeout) {
		checkNotNull(defaultTimeout);
		this.defaultTimeoutAndInterval = new TimeoutAndInterval(defaultTimeout, defaultTimeoutAndInterval.getInterval());
		return this;
	}

	/**
	 * Default timeout.
	 *
	 * @param time the time
	 * @param unit the unit
	 * @return the configuration
	 */
	public Configuration defaultTimeout(long time, TimeUnit unit) {
		return defaultTimeout(new Duration(time, unit));
	}

	/**
	 * Gets the default interval.
	 *
	 * @return the default interval
	 */
	public Duration getDefaultInterval() {
		return defaultTimeoutAndInterval.getInterval();
	}

	/**
	 * Default interval.
	 *
	 * @param defaultInterval the default interval
	 * @return the configuration
	 */
	public Configuration defaultInterval(Duration defaultInterval) {
		checkNotNull(defaultInterval);
		this.defaultTimeoutAndInterval = new TimeoutAndInterval(defaultTimeoutAndInterval.getTimeout(), defaultInterval);
		return this;
	}

	/**
	 * Default interval.
	 *
	 * @param time the time
	 * @param unit the unit
	 * @return the configuration
	 */
	public Configuration defaultInterval(long time, TimeUnit unit) {
		return defaultInterval(new Duration(time, unit));
	}
	
	public void addWaitingPreset(String key, Duration timeout, Duration interval) {
	    durationPresets.put(key, new TimeoutAndInterval(timeout, interval));
	}
	
	public Duration getWaitingPresetTimeout(String key) {
	    TimeoutAndInterval timeoutAndInterval = durationPresets.get(key);
	    return timeoutAndInterval == null ? null : timeoutAndInterval.getTimeout();
	}

	public Duration getWaitingPresetInterval(String key) {
	    TimeoutAndInterval timeoutAndInterval = durationPresets.get(key);
	    return timeoutAndInterval == null ? null : timeoutAndInterval.getInterval();
	}
	
	/**
	 * Register interaction listener.
	 *
	 * @param listener the listener
	 */
	public void registerInteractionListener(InteractionListener listener) {
		globalListeners.add(listener);
	}

	/**
	 * Unregister interaction listener.
	 *
	 * @param listener the listener
	 */
	public void unregisterInteractionListener(InteractionListener listener) {
		globalListeners.remove(listener);
	}

	/**
	 * Register exception handler.
	 *
	 * @param handle the exception handler
	 */
	public void registerExceptionHandler(ExceptionHandler handler) {
		globalExceptionHandlers.add(handler);
	}
	
	/**
	 * Unregister exception handler.
	 *
	 * @param handle the exception handler
	 */
	public void unregisterExceptionHandler(ExceptionHandler handler) {
		globalExceptionHandlers.remove(handler);
	}
	
	/**
	 * Gets the global listeners.
	 *
	 * @return the global listeners
	 */
	public List<InteractionListener> getGlobalListeners() {
		return globalListeners;
	}
	
	public List<ExceptionHandler> getGlobalExceptionHandlers() {
		return globalExceptionHandlers;
	}
}
