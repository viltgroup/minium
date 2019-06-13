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
package minium.cucumber;

import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import cucumber.api.Scenario;
import minium.actions.internal.AfterInteractionEvent;
import minium.actions.internal.DefaultInteractionListener;
import minium.web.EvalWebElements;
import minium.web.internal.HasNativeWebDriver;
import minium.web.internal.actions.GetInteraction;

public class GetInteractionListener extends DefaultInteractionListener {
    private Scenario scenario;

    public GetInteractionListener(Scenario s) {
        this.scenario = s;
    }

    @Override
    protected void onAfterEvent(AfterInteractionEvent event) {
        if (event.getInteraction() instanceof GetInteraction) {
            GetInteraction interaction = (GetInteraction) event.getInteraction();
            String performance = (String) event.getSource().as(EvalWebElements.class).eval("return window.performance");
            WebDriver webdriver = (WebDriver) event.getSource().as(HasNativeWebDriver.class).nativeWebDriver();
            List<LogEntry> jsErrors = webdriver.manage().logs().get(LogType.BROWSER).filter(Level.SEVERE);
            scenario.write("{ \"url\": \"" + interaction.getUrl() + "\", \"data\": " + performance + "\", \"jsErrors\": " + jsErrors + "}");
        }
    }
}
