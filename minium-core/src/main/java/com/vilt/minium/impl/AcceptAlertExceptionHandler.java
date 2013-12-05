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

import static com.google.common.base.Predicates.instanceOf;
import static com.google.common.base.Throwables.getCausalChain;
import static com.google.common.collect.FluentIterable.from;

import org.openqa.selenium.Alert;
import org.openqa.selenium.UnhandledAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vilt.minium.ExceptionHandler;
import com.vilt.minium.TargetLocatorWebElements;
import com.vilt.minium.WebElements;

public class AcceptAlertExceptionHandler implements ExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(AcceptAlertExceptionHandler.class);

    @Override
    public boolean handle(WebElements elems, Throwable e) {
        if (from(getCausalChain(e)).anyMatch(instanceOf(UnhandledAlertException.class))) {
            handleAlert(((TargetLocatorWebElements<?>) elems).alert());
            return true;
        }
        return false;
    }

    protected void handleAlert(Alert alert) {
        log.debug("Accepting alert box with message: {}", alert.getText());
        alert.accept();
    }

}
