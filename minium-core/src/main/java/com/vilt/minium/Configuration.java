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
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.vilt.minium.actions.InteractionListener;

/**
 * The Class Configuration.
 */
public class Configuration implements Serializable {

	private static final long serialVersionUID = -6096136074620998211L;

	private Duration defaultTimeout  = new Duration(5, TimeUnit.SECONDS);
	private Duration defaultInterval = new Duration(1, TimeUnit.SECONDS);

	/** The global listeners. */
	protected final List<InteractionListener> globalListeners = Lists.newArrayList();

	
	/**
	 * Gets the default timeout.
	 *
	 * @return the default timeout
	 */
	public Duration getDefaultTimeout() {
		return defaultTimeout;
	}

	/**
	 * Default timeout.
	 *
	 * @param defaultTimeout the default timeout
	 * @return the configuration
	 */
	public Configuration defaultTimeout(Duration defaultTimeout) {
		checkNotNull(defaultTimeout);
		this.defaultTimeout = defaultTimeout;
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
		return defaultInterval;
	}

	/**
	 * Default interval.
	 *
	 * @param defaultInterval the default interval
	 * @return the configuration
	 */
	public Configuration defaultInterval(Duration defaultInterval) {
		checkNotNull(defaultInterval);
		this.defaultInterval = defaultInterval;
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
	 * Gets the global listeners.
	 *
	 * @return the global listeners
	 */
	public List<InteractionListener> getGlobalListeners() {
		return globalListeners;
	}
}
