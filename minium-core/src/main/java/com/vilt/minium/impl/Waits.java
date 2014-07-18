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

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.vilt.minium.Configuration;
import com.vilt.minium.Configuration.WaitingPreset;
import com.vilt.minium.CoreWebElements;
import com.vilt.minium.Duration;

public class Waits {

    public static void waitForPredicate(CoreWebElements<?> webElements, String preset, Predicate<? super CoreWebElements<?>> predicate) {
        Configuration configuration = ((WebElementsDriverProvider<?>) webElements).configure();
        WaitingPreset waitingPreset = configuration.waitingPreset(preset);
        waitForPredicate(webElements, waitingPreset.timeout(), waitingPreset.interval(), predicate);
    }

    public static void waitForPredicate(CoreWebElements<?> webElements, Duration timeout, Duration interval, Predicate<? super CoreWebElements<?>> predicate) {
        Configuration configuration = ((WebElementsDriverProvider<?>) webElements).configure();
        if (timeout == null) {
            timeout = configuration.defaultTimeout();
        }
        if (interval == null) {
            interval = configuration.defaultInterval();
        }

        WebElementsWait wait = getWait(webElements, timeout, interval);

        Function<? super CoreWebElements<?>, Boolean> function = Functions.forPredicate(predicate);
        wait.until(function);
    }

    protected static WebElementsWait getWait(CoreWebElements<?> webElements, Duration timeout, Duration interval) {
        return new WebElementsWait(webElements, timeout, interval);
    }
}
