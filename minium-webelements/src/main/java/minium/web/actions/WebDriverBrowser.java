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
package minium.web.actions;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import minium.internal.Module;
import minium.internal.Modules;
import minium.web.WebElements;
import minium.web.internal.WebElementsFactory;
import minium.web.internal.WebElementsFactory.Builder;
import minium.web.internal.WebModules;
import minium.web.internal.actions.InternalBrowser;

public class WebDriverBrowser<T extends WebElements> implements Browser<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverBrowser.class);

    private final InternalBrowser<T> browser;

    public WebDriverBrowser(WebDriver webDriver, Class<T> intf) {
        this(webDriver, intf, null);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public WebDriverBrowser(WebDriver webDriver, Class<T> intf, Module module) {
        Module combinedModules = module == null ?
                WebModules.defaultModule(webDriver) :
                Modules.combine(WebModules.baseModule(webDriver), module);
        LOGGER.debug("Creating a WebDriverBrowser for modules: {}", combinedModules);

        Builder<T> builder = new WebElementsFactory.Builder<>();
        combinedModules.configure(builder);
        WebElementsFactory<T> factory = builder.build();
        Set<Class<?>> providedInterfaces = factory.getProvidedInterfaces();

        LOGGER.trace("Provided interfaces: {}", providedInterfaces);

        this.browser = new InternalBrowser(factory);
    }

    @Override
    public T root() {
        return browser.root();
    }

    // CHECKSTYLE:OFF
    @Override
    public T $(String selector) {
        return browser.$(selector);
    }

    @Override
    public T $(WebElements... elems) {
        return browser.$(elems);
    }
    // CHECKSTYLE:ON

    @Override
    public void get(String url) {
        browser.get(url);
    }

    @Override
    public String getCurrentUrl() {
        return browser.getCurrentUrl();
    }

    @Override
    public String getPerformance() {
        return browser.getPerformance();
    }

    @Override
    public String getTitle() {
        return browser.getTitle();
    }

    @Override
    public void close() {
        browser.close();
    }

    @Override
    public void quit() {
        browser.quit();
    }

    @Override
    public Navigation navigate() {
        return browser.navigate();
    }

    @Override
    public WebConfiguration configure() {
        return browser.configure();
    }

    @Override
    public Screenshot screenshot() {
        return browser.screenshot();
    }

    @Override
    public String toString() {
        return browser.toString();
    }
}
