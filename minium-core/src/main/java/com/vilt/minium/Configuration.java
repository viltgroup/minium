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

    public interface WaitingPreset {
        WaitingPreset timeout(Duration timeout);
        WaitingPreset interval(Duration interval);
        WaitingPreset timeout(long time, TimeUnit unit);
        WaitingPreset interval(long time, TimeUnit unit);
        WaitingPreset reset();

        Duration timeout();
        Duration interval();

        Configuration done();
    }

    public interface InteractionListeners extends Iterable<InteractionListener> {
        InteractionListeners add(InteractionListener interactionListener);
        InteractionListeners remove(InteractionListener interactionListener);

        Configuration done();
    }

    public interface ExceptionHandlers extends Iterable<ExceptionHandler> {
        ExceptionHandlers add(ExceptionHandler exceptionHandler);
        ExceptionHandlers remove(ExceptionHandler exceptionHandler);

        Configuration done();
    }

    /**
     * Gets the default timeout.
     *
     * @return the default timeout
     */
    Duration defaultTimeout();

    /**
     * Default timeout.
     *
     * @param defaultTimeout the default timeout
     * @return the configuration
     */
    Configuration defaultTimeout(Duration defaultTimeout);

    /**
     * Default timeout.
     *
     * @param time the time
     * @param unit the unit
     * @return the configuration
     */
    Configuration defaultTimeout(long time, TimeUnit unit);

    /**
     * Gets the default interval.
     *
     * @return the default interval
     */
    Duration defaultInterval();

    /**
     * Default interval.
     *
     * @param defaultInterval the default interval
     * @return the configuration
     */
    Configuration defaultInterval(Duration defaultInterval);

    /**
     * Default interval.
     *
     * @param time the time
     * @param unit the unit
     * @return the configuration
     */
    Configuration defaultInterval(long time, TimeUnit unit);

    WaitingPreset waitingPreset(String preset);

    InteractionListeners interactionListeners();

    ExceptionHandlers exceptionHandlers();

}