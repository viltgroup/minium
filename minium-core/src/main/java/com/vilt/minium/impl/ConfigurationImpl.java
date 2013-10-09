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
package com.vilt.minium.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vilt.minium.Configuration;
import com.vilt.minium.Duration;
import com.vilt.minium.ExceptionHandler;
import com.vilt.minium.actions.AfterFailInteractionEvent;
import com.vilt.minium.actions.InteractionListener;
import com.vilt.minium.impl.actions.DefaultInteractionListener;

/**
 * The Class Configuration.
 */
public class ConfigurationImpl implements Configuration {
    
	private class ExceptionHandlersInteractionListenerAdapter extends DefaultInteractionListener {
		
		@Override
		protected void onAfterFailEvent(AfterFailInteractionEvent event) {
			BaseWebElementsImpl<?> source = (BaseWebElementsImpl<?>) event.getSource();
			if (source == null) return;
			for (ExceptionHandler handler : exceptionHandlers) {
				handler.handle(source, event.getException());
			}
		}
	}
	
	private class WaitingPresetsImpl implements WaitingPresets {

	    private Map<String, TimeoutAndInterval> durationPresets = Maps.newHashMap();

        @Override
        public void add(String preset, Duration timeout, Duration interval) {
            durationPresets.put(preset, new TimeoutAndInterval(timeout, interval));
        }

        @Override
        public void remove(String preset) {
            durationPresets.remove(preset);
        }

        @Override
        public TimeoutAndInterval get(String preset) {
            return durationPresets.get(preset);
        }
	}

	private class InteractionListenersImpl implements InteractionListeners {

	    private List<InteractionListener> interactionListeners = Lists.newArrayList();
	    
        @Override
        public Iterator<InteractionListener> iterator() {
            return Iterators.unmodifiableIterator(interactionListeners.iterator());
        }

        @Override
        public void add(InteractionListener interactionListener) {
            interactionListeners.add(interactionListener);
        }

        @Override
        public void remove(InteractionListener interactionListener) {
            interactionListeners.remove(interactionListener);
        }
	}

	private class ExceptionHandlersImpl implements ExceptionHandlers {
	    
	    private List<ExceptionHandler> exceptionHandlers = Lists.newArrayList();
	    
	    @Override
	    public Iterator<ExceptionHandler> iterator() {
	        return Iterators.unmodifiableIterator(exceptionHandlers.iterator());
	    }
	    
	    @Override
	    public void add(ExceptionHandler exceptionHandler) {
	        exceptionHandlers.add(exceptionHandler);
	    }
	    
	    @Override
	    public void remove(ExceptionHandler exceptionHandler) {
	        exceptionHandlers.remove(exceptionHandler);
	    }
	}
	
	private TimeoutAndInterval defaultTimeoutAndInterval  = 
	        new TimeoutAndInterval(new Duration(5, TimeUnit.SECONDS), new Duration(1, TimeUnit.SECONDS));

	private WaitingPresets waitingPresets = new WaitingPresetsImpl();
	private InteractionListeners interactionListeners = new InteractionListenersImpl();
	private ExceptionHandlers exceptionHandlers = new ExceptionHandlersImpl();
	
	public ConfigurationImpl() {
		interactionListeners.add(new ExceptionHandlersInteractionListenerAdapter());
	}
	
	/* (non-Javadoc)
     * @see com.vilt.minium.Conf#getDefaultTimeout()
     */
	@Override
    public Duration getDefaultTimeout() {
		return defaultTimeoutAndInterval.timeout();
	}

	/* (non-Javadoc)
     * @see com.vilt.minium.Conf#defaultTimeout(com.vilt.minium.Duration)
     */
	@Override
    public Configuration defaultTimeout(Duration defaultTimeout) {
		checkNotNull(defaultTimeout);
		this.defaultTimeoutAndInterval = new TimeoutAndInterval(defaultTimeout, defaultTimeoutAndInterval.interval());
		return this;
	}

	/* (non-Javadoc)
     * @see com.vilt.minium.Conf#defaultTimeout(long, java.util.concurrent.TimeUnit)
     */
	@Override
    public Configuration defaultTimeout(long time, TimeUnit unit) {
		return defaultTimeout(new Duration(time, unit));
	}

	/* (non-Javadoc)
     * @see com.vilt.minium.Conf#getDefaultInterval()
     */
	@Override
    public Duration getDefaultInterval() {
		return defaultTimeoutAndInterval.interval();
	}

	/* (non-Javadoc)
     * @see com.vilt.minium.Conf#defaultInterval(com.vilt.minium.Duration)
     */
	@Override
    public Configuration defaultInterval(Duration defaultInterval) {
		checkNotNull(defaultInterval);
		this.defaultTimeoutAndInterval = new TimeoutAndInterval(defaultTimeoutAndInterval.timeout(), defaultInterval);
		return this;
	}

	/* (non-Javadoc)
     * @see com.vilt.minium.Conf#defaultInterval(long, java.util.concurrent.TimeUnit)
     */
	@Override
    public Configuration defaultInterval(long time, TimeUnit unit) {
		return defaultInterval(new Duration(time, unit));
	}
	
	@Override
	public WaitingPresets waitingPresets() {
	    return waitingPresets;
	}
	
	@Override
	public InteractionListeners interactionListeners() {
	    return interactionListeners;
	}

	@Override
	public ExceptionHandlers exceptionHandlers() {
	    return exceptionHandlers;
	}
}
