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
package minium.actions;

import java.util.concurrent.TimeUnit;

import minium.AsIs;

/**
 * Fluent API for Minium configuration. For instance, you can write code like this:
 *
 * <pre>
 * configuration
 *   .defaultTimeout(2, TimeUnit.SECONDS)
 *   .defaultInterval(1, TimeUnit.SECONDS)
 *   .waitingPreset("slow")
 *     .timeout(20, TimeUnit.SECONDS)
 *     .interval(5, TimeUnit.SECONDS)
 *   .done()
 *   .waitingPreset("fast")
 *     .timeout(1, TimeUnit.SECONDS)
 *     .interval(200, TimeUnit.MILLISECONDS)
 *   .done()
 *   .interactionListeners()
 *     .add(slowMotion(2, TimeUnit.SECONDS))
 *     .add(retry())
 *   .done()
 * </pre>
 *
 * @author rui.figueira
 */
public interface Configuration extends AsIs {

    /**
     * A waiting preset is basically a named pair of timeout and interval values. They are very
     * convenient to reflect specific interaction waiting periods (for instance, some tasks may
     * require a bigger timeout period, so one can create a "slow" waiting preset for that.
     *
     * @author rui.figueira
     */
    public interface WaitingPreset {

        /**
         * Sets this waiting preset timeout.
         *
         * @param timeout the timeout period for this waiting preset
         * @return this waiting preset
         */
        WaitingPreset timeout(Duration timeout);

        /**
         * Sets this waiting preset timeout.
         *
         * @param interval the interval period for this waiting preset
         * @return this waiting preset
         */
        WaitingPreset interval(Duration interval);

        /**
         * Sets this waiting preset timeout.
         *
         * @param time the time for this waiting preset timeout
         * @param unit the time unit for this waiting preset timeout
         * @return this waiting preset
         */
        WaitingPreset timeout(long time, TimeUnit unit);

        /**
         * Sets this waiting preset interval.
         *
         * @param time the time for this waiting preset interval
         * @param unit the time unit for this waiting preset interval
         * @return this waiting preset
         */
        WaitingPreset interval(long time, TimeUnit unit);

        /**
         * Resets this waiting preset interval, that is, both timeout and interval
         * periods will be the default values for the corresponding configuration.
         *
         * @return this waiting preset
         */
        WaitingPreset reset();

        /**
         * Gets this waiting preset timeout.
         *
         * @return this waiting preset timeout
         */
        Duration timeout();

        /**
         * Gets this waiting preset interval.
         *
         * @return this waiting preset interval
         */
        Duration interval();

        /**
         * Goes back to the corresponding configuration.
         *
         * @return this {@link Configuration}
         */
        Configuration done();
    }

    /**
     * Handles interaction listeners registration and unregistration, as well as accessing
     * all registered interaction listeners.
     *
     * @author rui.figueira
     */
    public interface InteractionListenerCollection extends Iterable<InteractionListener> {

        /**
         * Adds a new interaction listener. If the same interaction listerer is added twice,
         * it will only be called once ({@code .equals()} is used for comparison).
         *
         * @param interactionListener interaction listener to add
         * @return this interaction listener collection
         */
        InteractionListenerCollection add(InteractionListener interactionListener);

        /**
         * Removes an existing interaction listener ({@code .equals()} is used for comparison).
         * If the listener does not exist, there is no state change.
         *
         * @param interactionListener interaction listener to remove
         * @return this interaction listener collection
         */
        InteractionListenerCollection remove(InteractionListener interactionListener);

        /**
         * Removes all interaction listeners from this configuration.
         *
         * @return this interaction listener collection
         */
        InteractionListenerCollection clear();

        /**
         * Goes back to the corresponding configuration.
         *
         * @return this {@link Configuration}
         */
        Configuration done();
    }

    /**
     * Handles exception handlers registration and unregistration, as well as accessing
     * all registered exception handlers.
     *
     * @author rui.figueira
     */
    public interface ExceptionHandlerCollection extends Iterable<ExceptionHandler> {

        /**
         * Adds a new exception handler. If the same exception handler is added twice,
         * it will only be called once ({@code .equals()} is used for comparison).
         *
         * @param exceptionHandler the exception handler to add
         * @return this exception handler collection
         */
        ExceptionHandlerCollection add(ExceptionHandler exceptionHandler);

        /**
         * Removes an existing exception handler ({@code .equals()} is used for comparison).
         * If the handler does not exist, there is no state change.
         *
         * @param exceptionHandler the exception handler to remove
         * @return this exception handler collection
         */
        ExceptionHandlerCollection remove(ExceptionHandler exceptionHandler);

        /**
         * Removes all exception handler from this configuration.
         *
         * @return this exception handler collection
         */
        ExceptionHandlerCollection clear();

        /**
         * Goes back to the corresponding configuration.
         *
         * @return this {@link Configuration}
         */
        Configuration done();
    }

    /**
     * Gets the default interaction timeout.
     *
     * @return the default interaction timeout
     */
    Duration defaultTimeout();

    /**
     * Sets the default interaction timeout.
     *
     * @param defaultTimeout the default interaction timeout
     * @return this configuration
     */
    Configuration defaultTimeout(Duration defaultTimeout);

    /**
     * Sets the default interaction timeout.
     *
     * @param time the time
     * @param unit the time unit
     * @return this configuration
     */
    Configuration defaultTimeout(long time, TimeUnit unit);

    /**
     * Gets the default interaction interval.
     *
     * @return the default interaction interval
     */
    Duration defaultInterval();

    /**
     * Sets the default interaction interval.
     *
     * @param defaultInterval the default interaction interval
     * @return this configuration
     */
    Configuration defaultInterval(Duration defaultInterval);

    /**
     * Sets the default interaction interval.
     *
     * @param time the time
     * @param unit the time unit
     * @return this configuration
     */
    Configuration defaultInterval(long time, TimeUnit unit);

    /**
     * Gets the waiting preset corresponding to the passed preset value. That waiting
     * can be used to access or update both interval and timeout periods. If that waiting preset
     * was never configure, both interval and timeout periods will be the default ones.
     *
     * @param preset the waiting preset name
     * @return the corresponding waiting preset (never null)
     */
    WaitingPreset waitingPreset(String preset);

    /**
     * Gets the interaction listeners collection so that listeners can be added or removed.
     *
     * @return the interaction listeners collection
     */
    InteractionListenerCollection interactionListeners();

    /**
     * Gets the exception handlers collection so that exception handlers can be added or removed.
     *
     * @return the exception handlers collection
     */
    ExceptionHandlerCollection exceptionHandlers();
}