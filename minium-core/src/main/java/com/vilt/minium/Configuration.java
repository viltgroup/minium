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

import java.util.concurrent.TimeUnit;

import com.vilt.minium.actions.InteractionListener;

public interface Configuration {
    
    public static class TimeoutAndInterval {

        private final Duration timeout;
        private final Duration interval;
        
        public TimeoutAndInterval(Duration timeout, Duration interval) {
            this.timeout = timeout;
            this.interval = interval;
        }
        
        public Duration timeout() {
            return timeout;
        }
        
        public Duration interval() {
            return interval;
        }
    }
    
    public interface WaitingPresets {
        public void add(String preset, Duration timeout, Duration interval);
        public void remove(String preset);
        public TimeoutAndInterval get(String preset);
    }

    public interface InteractionListeners extends Iterable<InteractionListener> {
        public void add(InteractionListener interactionListener);
        public void remove(InteractionListener interactionListener);
    }

    public interface ExceptionHandlers extends Iterable<ExceptionHandler> {
        public void add(ExceptionHandler exceptionHandler);
        public void remove(ExceptionHandler exceptionHandler);
    }
    
    /**
     * Gets the default timeout.
     *
     * @return the default timeout
     */
    public abstract Duration getDefaultTimeout();

    /**
     * Default timeout.
     *
     * @param defaultTimeout the default timeout
     * @return the configuration
     */
    public abstract Configuration defaultTimeout(Duration defaultTimeout);

    /**
     * Default timeout.
     *
     * @param time the time
     * @param unit the unit
     * @return the configuration
     */
    public abstract Configuration defaultTimeout(long time, TimeUnit unit);

    /**
     * Gets the default interval.
     *
     * @return the default interval
     */
    public abstract Duration getDefaultInterval();

    /**
     * Default interval.
     *
     * @param defaultInterval the default interval
     * @return the configuration
     */
    public abstract Configuration defaultInterval(Duration defaultInterval);

    /**
     * Default interval.
     *
     * @param time the time
     * @param unit the unit
     * @return the configuration
     */
    public abstract Configuration defaultInterval(long time, TimeUnit unit);

    public abstract WaitingPresets waitingPresets();
    
    public abstract InteractionListeners interactionListeners();

    public abstract ExceptionHandlers exceptionHandlers();

}