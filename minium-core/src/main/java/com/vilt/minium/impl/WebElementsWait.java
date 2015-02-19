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

import org.openqa.selenium.support.ui.FluentWait;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.Duration;
import com.vilt.minium.TimeoutException;

public class WebElementsWait extends FluentWait<CoreWebElements<?>> {

    public WebElementsWait(CoreWebElements<?> input, Duration timeout, Duration interval) {
        super(input);
        withTimeout(timeout.getTime(), timeout.getUnit());
        pollingEvery(interval.getTime(), interval.getUnit());
    }

    @Override
    protected RuntimeException timeoutException(String message, Throwable lasteException) {
        return new TimeoutException(message, lasteException);
    }
}

