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

import static java.lang.String.format;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;
import com.vilt.minium.impl.ConfigurationImpl;
import com.vilt.minium.impl.WebElementsFactory;
import com.vilt.minium.impl.WebElementsFactoryHelper;

public class WebElementsDriver<T extends CoreWebElements<T>> implements WebElementsFinder<T>, WebDriver, JavascriptExecutor, HasInputDevices, TakesScreenshot {

    final Logger logger = LoggerFactory.getLogger(WebElementsDriver.class);

    protected final WebDriver wd;
    protected final WebElementsFactory factory;
    protected final Configuration configuration;
    protected String windowHandle;

    /**
     * Instantiates a new web elements driver.
     *
     * @param wd the wd
     * @param factory the factory
     * @param configuration the configuration
     */
    protected WebElementsDriver(WebDriver wd, WebElementsFactory factory, Configuration configuration) {
        this(wd, factory, configuration, wd.getWindowHandle());
    }

    /**
     * Instantiates a new web elements driver.
     *
     * @param wd the wd
     * @param factory the factory
     * @param configuration the configuration
     * @param handle the handle
     */
    protected WebElementsDriver(WebDriver wd, WebElementsFactory factory, Configuration configuration, String handle) {
        this.wd = wd;
        this.factory = factory;
        this.configuration = configuration;
        this.windowHandle = handle;
    }

    /**
     * Instantiates a new web elements driver.
     *
     * @param wd the wd
     * @param elementsInterface the elements interface
     * @param moreInterfaces the more interfaces
     */
    public WebElementsDriver(WebDriver wd, Class<T> elementsInterface, Class<?>... moreInterfaces) {
        this(wd, new WebElementsFactory(elementsInterface, moreInterfaces), new ConfigurationImpl());
    }

    /**
     * Configuration.
     *
     * @return the configuration
     */
    @Deprecated
    public Configuration configuration() {
        return configuration;
    }

    public Configuration configure() {
        return configuration;
    }

    /** {@inheritDoc} */
    @Override
    public void get(String url) {
        ensureSwitch();
        wd.get(url);
    }

    /** {@inheritDoc} */
    @Override
    public String getCurrentUrl() {
        ensureSwitch();
        return wd.getCurrentUrl();
    }

    /** {@inheritDoc} */
    @Override
    public String getTitle() {
        ensureSwitch();
        return wd.getTitle();
    }

    /** {@inheritDoc} */
    @Override
    public List<WebElement> findElements(By by) {
        ensureSwitch();
        return wd.findElements(by);
    }

    /** {@inheritDoc} */
    @Override
    public WebElement findElement(By by) {
        ensureSwitch();
        return wd.findElement(by);
    }

    /** {@inheritDoc} */
    @Override
    public String getPageSource() {
        ensureSwitch();
        return wd.getPageSource();
    }

    /** {@inheritDoc} */
    @Override
    public void close() {
        ensureSwitch();
        wd.close();
    }

    /** {@inheritDoc} */
    @Override
    public void quit() {
        wd.quit();
    }

    /** {@inheritDoc} */
    @Override
    public Set<String> getWindowHandles() {
        return wd.getWindowHandles();
    }

    /** {@inheritDoc} */
    @Override
    public String getWindowHandle() {
        return windowHandle;
    }

    /** {@inheritDoc} */
    @Override
    public TargetLocator switchTo() {
        ensureSwitch();
        return wd.switchTo();
    }

    /** {@inheritDoc} */
    @Override
    public Navigation navigate() {
        ensureSwitch();
        return wd.navigate();
    }

    /** {@inheritDoc} */
    @Override
    public Options manage() {
        ensureSwitch();
        return wd.manage();
    }

    /** {@inheritDoc} */
    @Override
    public Keyboard getKeyboard() {
        ensureSwitch();
        return ((HasInputDevices) wd).getKeyboard();
    }

    /** {@inheritDoc} */
    @Override
    public Mouse getMouse() {
        ensureSwitch();
        return ((HasInputDevices) wd).getMouse();
    }

    /** {@inheritDoc} */
    @Override
    public Object executeScript(String script, Object... args) {
        ensureSwitch();
        return ((JavascriptExecutor) wd).executeScript(script, args);
    }

    /** {@inheritDoc} */
    @Override
    public Object executeAsyncScript(String script, Object... args) {
        ensureSwitch();

        wd.manage().timeouts().setScriptTimeout(configuration.defaultTimeout().getTime(), configuration.defaultTimeout().getUnit());
        return ((JavascriptExecutor) wd).executeAsyncScript(script, args);
    }

    /** {@inheritDoc} */
    @Override
    public <X> X getScreenshotAs(OutputType<X> type) throws WebDriverException {
        ensureSwitch();
        return ((TakesScreenshot) wd).<X> getScreenshotAs(type);
    }

    /** {@inheritDoc} */
    @Override
    public T find(String selector) {
        return webElements().find(selector);
    }

    /** {@inheritDoc} */
    @Override
    public T find(T expr) {
        return webElements().find(expr);
    }

    /**
     * Web elements.
     *
     * @return the t
     */
    public T webElements() {
        return WebElementsFactoryHelper.createRootWebElements(factory, this);
    }

    /**
     * Checks if is closed.
     *
     * @return true, if is closed
     */
    public boolean isClosed() {
        return !wd.getWindowHandles().contains(windowHandle);
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof WebElementsDriver))
            return false;

        WebElementsDriver<?> other = (WebElementsDriver<?>) obj;

        String windowHandle = getWindowHandle();
        String otherWindowHandle = other.getWindowHandle();

        return Objects.equal(windowHandle, otherWindowHandle);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return getWindowHandle().hashCode();
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return format("wd");
    }

    /**
     * Ensure switch. This should not be used.
     */
    public void ensureSwitch() {
        String windowHandleOrNull = safeGetWindowHandle();

        if (windowHandleOrNull == null || !StringUtils.equals(windowHandle, windowHandleOrNull)) {
            wd.switchTo().window(windowHandle);
            logger.debug("Switched to window with handle '{}'", windowHandle);
        }
        wd.switchTo().defaultContent();
    }

    /**
     * Gets the wrapped web driver.
     *
     * @return the wrapped web driver
     */
    public WebDriver getWrappedWebDriver() {
        return wd;
    }

    protected String safeGetWindowHandle() {
        try {
            return wd.getWindowHandle();
        } catch (Exception e) {
            return null;
        }
    }

}
