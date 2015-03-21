/*
 * Copyright (C) 2015 The Minium Authors
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

/**
 * Provides wait interaction methods for Minium expressions.
 *
 * @author rui.figueira
 *
 * @param <T> this {@link Interactable} for chainable calls
 */
public interface WaitInteractable<T extends Interactable<?>> extends Interactable<T> {

    /**
     * Waits for the existence of the elements expression, that is, that it evaluates into a
     * non-empty set before a timeout occurs. It will fail with a {@link TimeoutException}
     * if no element matches this expression.
     *
     * @return this {@link Interactable}
     * @throws TimeoutException in case timeout occurs
     */
    public T waitForExistence() throws TimeoutException;

    /**
     * Equivalent to {@code elems.withWaitingPreset(waitingPreset).waitForExistence()}.
     * @param waitingPreset waiting preset to use when waiting for the existence of this
     * elements expression
     *
     * @return this {@link Interactable}
     * @throws TimeoutException in case timeout occurs
     */
    public T waitForExistence(String waitingPreset) throws TimeoutException;

    /**
     * Waits for the unexistence of the elements expression, that is, that it evaluates into a
     * empty set before a timeout occurs. It will fail with a {@link TimeoutException}
     * if some element matches this expression.
     *
     * @return this {@link Interactable}
     * @throws TimeoutException in case timeout occurs
     */
    public T waitForUnexistence() throws TimeoutException;

    /**
     * Equivalent to {@code elems.withWaitingPreset(waitingPreset).waitForUnexistence()}.
     * @param waitingPreset waiting preset to use when waiting for the unexistence of this
     * elements expression
     *
     * @return this {@link Interactable}
     * @throws TimeoutException in case timeout occurs
     */
    public T waitForUnexistence(String waitingPreset) throws TimeoutException;

    /**
     * Waits for the existence of the elements expression, that is, that it evaluates into a
     * non-empty set before a timeout occurs. It will return true in case it succeeds, false
     * otherwise. No {@link TimeoutException} is thrown in this method.
     *
     * @return true if it evaluates into some element, false otherwise
     */
    public boolean checkForExistence();

    /**
     * Equivalent to {@code elems.withWaitingPreset(waitingPreset).checkForExistence()}.
     * @param waitingPreset waiting preset to use when waiting for the unexistence of this
     * elements expression
     *
     * @return true if it evaluates into some element, false otherwise
     */
    public boolean checkForExistence(String waitingPreset);

    /**
     * Waits for the unexistence of the elements expression, that is, that it evaluates into a
     * empty set before a timeout occurs. It will return true in case it succeeds, false
     * otherwise. No {@link TimeoutException} is thrown in this method.
     *
     * @return true if it doesn't evaluates into some element, false otherwise
     */
    public boolean checkForUnexistence();

    /**
     * Equivalent to {@code elems.withWaitingPreset(waitingPreset).checkForUnexistence()}.
     * @param waitingPreset waiting preset to use when waiting for the unexistence of this
     * elements expression
     *
     * @return true if it doesn't evaluates into some element, false otherwise
     */
    public boolean checkForUnexistence(String waitingPreset);

    /**
     * Waits for the specified time interval.
     *
     * @param time time to wait
     * @param timeUnit time units for the specified time
     * @return this {@link Interactable}
     */
    public T waitTime(long time, TimeUnit timeUnit);
}