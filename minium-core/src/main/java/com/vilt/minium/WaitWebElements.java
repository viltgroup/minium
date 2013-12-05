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

import com.google.common.base.Predicate;

/**
 * Provides wait methods for {@link WebElements}.
 *
 * @param <T> {@link WebElements} subclass
 * @author rui.figueira
 */
public interface WaitWebElements<T extends CoreWebElements<T>> extends WebElements {

    /**
     * Waits using the default timeout value provided by
     * {@link Configuration#defaultInterval()} for this {@link WebElements}
     * to satisfy a given predicate, or throws a.
     *
     * @param predicate the predicate to wait for.
     * @return a {@link WebElements} {@link TimeoutException} otherwise.
     */
    public T wait(Predicate<? super T> predicate) throws TimeoutException;

    /**
     * Waits with a specified timeout for this {@link WebElements} to satisfy a
     * given predicate, or throws a {@link TimeoutException} otherwise.
     *
     * @param time the timeout time
     * @param unit the timeout unit
     * @param predicate the predicate to wait for.
     * @return a {@link WebElements}
     */
    public T wait(long time, TimeUnit unit, Predicate<? super T> predicate) throws TimeoutException;

    /**
     * Waits with a specified timeout for this {@link WebElements} to satisfy a
     * given predicate, or throws a {@link TimeoutException} otherwise.
     *
     * @param timeout the timeout. If null, it will use the default timeout
     *        value provided by
     * @param predicate the predicate to wait for.
     * @return a {@link WebElements} {@link  Configuration#defaultInterval()}
     *         .
     */
    public T wait(Duration timeout, Predicate<? super T> predicate) throws TimeoutException;

    /**
     * Waits with a preset timeout and interval for this {@link WebElements} to
     * satisfy a given predicate, or throws a {@link TimeoutException}
     * otherwise.
     *
     * @param preset the waiting Tpreset, as configured in {@link Configuration#waitingPreset(String)}
     * @param predicate the predicate to wait for.
     * @return a {@link WebElements}
     *         .
     */
    public T wait(String preset, Predicate<? super T> predicate) throws TimeoutException;

    /**
     * Waits using the default timeout value provided by
     * {@link Configuration#defaultInterval()} for this {@link WebElements}
     * to satisfy a given predicate, otherwise it just returns.
     *
     * @param predicate the predicate to wait for.
     * @return a {@link WebElements}
     */
    public T waitOrTimeout(Predicate<? super T> predicate);

    /**
     * Waits with a specified timeout for this {@link WebElements} to satisfy a
     * given predicate, otherwise it just returns.
     *
     * @param time the timeout time.
     * @param unit the timeout unit.
     * @param predicate the predicate to wait for.
     * @return a {@link WebElements}
     *
     * @see {@link Configuration#defaultInterval(long, TimeUnit)}.
     */
    public T waitOrTimeout(long time, TimeUnit unit, Predicate<? super T> predicate);

    /**
     * Waits with a specified timeout for this {@link WebElements} to satisfy a
     * given predicate, otherwise it just returns.
     *
     * @param timeout the timeout. If null, it will use the default timeout
     *        value provided by
     * @param predicate the predicate to wait for.
     * @return a {@link WebElements}
     *
     * @see {@link Configuration#defaultInterval(Duration)}.
     */
    public T waitOrTimeout(Duration timeout, Predicate<? super T> predicate);

    /**
     * Waits with a preset timeout and interval for this {@link WebElements} to
     * satisfy a given predicate, otherwise it just returns.
     *
     * @param preset the waiting preset
     * @param predicate the predicate to wait for.
     * @return a {@link WebElements}
     *
     * @see {@link Configuration#defaultInterval(Duration)}.
     */
    public T waitOrTimeout(String preset, Predicate<? super T> predicate);
}
