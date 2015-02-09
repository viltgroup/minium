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
package minium.web.internal.actions;

import static com.google.common.base.Predicates.instanceOf;
import static com.google.common.base.Throwables.getCausalChain;
import static com.google.common.collect.FluentIterable.from;
import minium.Elements;
import minium.actions.ExceptionHandler;
import minium.actions.InteractionListener;
import minium.actions.internal.AfterFailInteractionEvent;
import minium.actions.internal.DefaultInteractionListener;
import minium.web.actions.UnhandledAlertInteractionListener;
import minium.web.internal.HasNativeWebDriver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.UnhandledAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultUnhandledAlertInteractionListener extends DefaultInteractionListener implements UnhandledAlertInteractionListener, ExceptionHandler {

    private static final Logger LOGGEER = LoggerFactory.getLogger(DefaultUnhandledAlertInteractionListener.class);

    @Override
    protected void onAfterFailEvent(AfterFailInteractionEvent event) {
        if (handle(event.getSource(), event.getException())) {
            event.setRetry(true);
        }
    }

    @Override
    public boolean handle(Elements elems, Throwable e) {
        if (from(getCausalChain(e)).anyMatch(instanceOf(UnhandledAlertException.class))) {
            handleAlert(elems.as(HasNativeWebDriver.class).nativeWebDriver().switchTo().alert());
            return true;
        }
        return false;
    }

    // by default, we accept the alert
    protected void handleAlert(Alert alert) {
        if (LOGGEER.isDebugEnabled()) {
            LOGGEER.debug("Accepting alert box with message: {}", alert.getText());
        }
        alert.accept();
    }

    @Override
    public InteractionListener accept() {
        return this;
    }

    @Override
    public UnhandledAlertInteractionListener dismiss() {
        return new DefaultUnhandledAlertInteractionListener() {
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
