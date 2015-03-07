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
package minium.web.internal.actions;

import minium.Elements;
import minium.actions.ExceptionHandler;
import minium.actions.internal.AfterFailInteractionEvent;
import minium.web.actions.OnUnhandledAlertInteractionListener;
import minium.web.internal.HasNativeWebDriver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.UnhandledAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultOnUnhandledAlertInteractionListener extends AbstractOnExceptionInteractionListener implements OnUnhandledAlertInteractionListener, ExceptionHandler {

    private static final Logger LOGGEER = LoggerFactory.getLogger(DefaultOnUnhandledAlertInteractionListener.class);

    @SuppressWarnings("unchecked")
    public DefaultOnUnhandledAlertInteractionListener() {
        super(UnhandledAlertException.class);
    }

    @Override
    protected boolean handle(AfterFailInteractionEvent event) {
        return handle(event.getSource(), event.getException());
    }

    @Override
    public boolean handle(Elements elems, Throwable e) {
        handleAlert(elems.as(HasNativeWebDriver.class).nativeWebDriver().switchTo().alert());
        return true;
    }

    // by default, we accept the alert
    protected void handleAlert(Alert alert) {
        if (LOGGEER.isDebugEnabled()) {
            LOGGEER.debug("Accepting alert box with message: {}", alert.getText());
        }
        alert.accept();
    }

    @Override
    public OnUnhandledAlertInteractionListener accept() {
        return this;
    }

    @Override
    public OnUnhandledAlertInteractionListener dismiss() {
        return new DefaultOnUnhandledAlertInteractionListener() {
            @Override
            protected void handleAlert(Alert alert) {
                if (LOGGEER.isDebugEnabled()) {
                    LOGGEER.debug("Dismissing alert box with message: {}", alert.getText());
                }
                alert.dismiss();
            }
        };
    }
}
