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
import io.platypus.Mixin;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import minium.actions.Configuration;
import minium.actions.Duration;
import minium.actions.InteractionListener;

import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * The Class Configuration.
 */
public class DefaultConfiguration extends Mixin.Impl implements Configuration {

    private class WaitingPresetImpl implements WaitingPreset {

        private final String preset;

        public WaitingPresetImpl(String preset) {
            this.preset = preset;
        }

        @Override
        public WaitingPreset timeout(Duration timeout) {
            timeoutPresets.put(preset, timeout);
            return this;
        }

        @Override
        public WaitingPreset interval(Duration interval) {
            intervalPresets.put(preset, interval);
            return this;
        }

        @Override
        public WaitingPreset timeout(long time, TimeUnit unit) {
            return timeout(new Duration(time, unit));
        }

        @Override
        public WaitingPreset interval(long time, TimeUnit unit) {
            return interval(new Duration(time, unit));
        }

        @Override
        public Duration timeout() {
            Duration timeout = timeoutPresets.get(preset);
            return timeout == null ? defaultTimeout() : timeout;
        }

        @Override
        public Duration interval() {
            Duration interval = intervalPresets.get(preset);
            return interval == null ? defaultInterval() : interval;
        }

        @Override
        public WaitingPreset reset() {
            timeoutPresets.remove(preset);
            intervalPresets.remove(preset);
            return this;
        }

        @Override
        public Configuration done() {
            return DefaultConfiguration.this;
        }
    }

    private class InteractionListenersImpl implements InteractionListeners {

        private Set<InteractionListener> interactionListeners = Sets.newLinkedHashSet();

        @Override
        public Iterator<InteractionListener> iterator() {
            return Iterators.unmodifiableIterator(interactionListeners.iterator());
        }

        @Override
        public InteractionListeners add(InteractionListener interactionListener) {
            interactionListeners.add(interactionListener);
            return this;
        }

        @Override
        public InteractionListeners remove(InteractionListener interactionListener) {
            interactionListeners.remove(interactionListener);
            return this;
        }

        @Override
        public Configuration done() {
            return DefaultConfiguration.this;
        }
    }

    private Duration defaultTimeout = new Duration(5, TimeUnit.SECONDS);
    private Duration defaultInterval  = new Duration(1, TimeUnit.SECONDS);

    private Map<String, Duration> timeoutPresets = Maps.newHashMap();
    private Map<String, Duration> intervalPresets = Maps.newHashMap();
    private InteractionListeners interactionListeners = new InteractionListenersImpl();

    public DefaultConfiguration() {
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.Conf#getDefaultTimeout()
     */
    @Override
    public Duration defaultTimeout() {
        return defaultTimeout;
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.Conf#defaultTimeout(com.vilt.minium.Duration)
     */
    @Override
    public Configuration defaultTimeout(Duration defaultTimeout) {
        checkNotNull(defaultTimeout);
        this.defaultTimeout = defaultTimeout;
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
    public Duration defaultInterval() {
        return defaultInterval;
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.Conf#defaultInterval(com.vilt.minium.Duration)
     */
    @Override
    public Configuration defaultInterval(Duration defaultInterval) {
        checkNotNull(defaultInterval);
        this.defaultInterval = defaultInterval;
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
    public WaitingPreset waitingPreset(String preset) {
        return new WaitingPresetImpl(preset);
    }

    @Override
    public InteractionListeners interactionListeners() {
        return interactionListeners;
    }

}
