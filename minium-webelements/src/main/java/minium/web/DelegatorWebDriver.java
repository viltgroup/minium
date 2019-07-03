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
package minium.web;

import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.Logs;

import com.google.common.base.Preconditions;

import minium.web.utils.PerformanceUtils;

public class DelegatorWebDriver extends Observable implements WebDriver, JavascriptExecutor, HasInputDevices, TakesScreenshot {

    class DelegatorMouse implements Mouse {

        @Override
        public void click(Coordinates where) {
            ensureWebDriver();
            delegateMouse().click(where);
        }

        @Override
        public void doubleClick(Coordinates where) {
            ensureWebDriver();
            delegateMouse().doubleClick(where);
        }

        @Override
        public void mouseDown(Coordinates where) {
            ensureWebDriver();
            delegateMouse().mouseDown(where);
        }

        @Override
        public void mouseUp(Coordinates where) {
            ensureWebDriver();
            delegateMouse().mouseUp(where);
        }

        @Override
        public void mouseMove(Coordinates where) {
            ensureWebDriver();
            delegateMouse().mouseMove(where);
        }

        @Override
        public void mouseMove(Coordinates where, long xOffset, long yOffset) {
            ensureWebDriver();
            delegateMouse().mouseMove(where, xOffset, yOffset);
        }

        @Override
        public void contextClick(Coordinates where) {
            ensureWebDriver();
            delegateMouse().contextClick(where);
        }

        protected Mouse delegateMouse() {
            return ((HasInputDevices) delegate).getMouse();
        }
    }

    class DelegatorKeyboard implements Keyboard {

        @Override
        public void sendKeys(CharSequence... keysToSend) {
            ensureWebDriver();
            delegateKeyboard().sendKeys(keysToSend);
        }

        @Override
        public void pressKey(CharSequence keyToPress) {
            ensureWebDriver();
            delegateKeyboard().pressKey(keyToPress);
        }

        @Override
        public void releaseKey(CharSequence keyToRelease) {
            ensureWebDriver();
            delegateKeyboard().releaseKey(keyToRelease);
        }

        protected Keyboard delegateKeyboard() {
            return ((HasInputDevices) delegate).getKeyboard();
        }
    }

    class DelegatorTargetLocator implements TargetLocator {

        @Override
        public WebDriver frame(int index) {
            ensureWebDriver();
            delegate.switchTo().frame(index);
            return delegate;
        }

        @Override
        public WebDriver frame(String nameOrId) {
            ensureWebDriver();
            delegate.switchTo().frame(nameOrId);
            return delegate;
        }

        @Override
        public WebDriver frame(WebElement frameElement) {
            ensureWebDriver();
            delegate.switchTo().frame(frameElement);
            return delegate;
        }

        @Override
        public WebDriver parentFrame() {
            ensureWebDriver();
            delegate.switchTo().parentFrame();
            return delegate;
        }

        @Override
        public WebDriver window(String nameOrHandle) {
            ensureWebDriver();
            delegate.switchTo().window(nameOrHandle);
            return delegate;
        }

        @Override
        public WebDriver defaultContent() {
            ensureWebDriver();
            delegate.switchTo().defaultContent();
            return delegate;
        }

        @Override
        public WebElement activeElement() {
            ensureWebDriver();
            return delegate.switchTo().activeElement();
        }

        @Override
        public Alert alert() {
            ensureWebDriver();
            return delegate.switchTo().alert();
        }
    }

    class DelegatorNavigation implements Navigation {

        @Override
        public void back() {
            ensureWebDriver();
            delegate.navigate().back();
        }

        @Override
        public void forward() {
            ensureWebDriver();
            delegate.navigate().forward();
        }

        @Override
        public void to(String url) {
            ensureWebDriver();
            delegate.navigate().to(url);
        }

        @Override
        public void to(URL url) {
            ensureWebDriver();
            delegate.navigate().to(url);
        }

        @Override
        public void refresh() {
            ensureWebDriver();
            delegate.navigate().refresh();
        }
    }

    class DelegatorOptions implements Options {

        @Override
        public void addCookie(Cookie cookie) {
            ensureWebDriver();
            delegate.manage().addCookie(cookie);
        }

        @Override
        public void deleteCookieNamed(String name) {
            ensureWebDriver();
            delegate.manage().deleteCookieNamed(name);
        }

        @Override
        public void deleteCookie(Cookie cookie) {
            ensureWebDriver();
            delegate.manage().deleteCookie(cookie);
        }

        @Override
        public void deleteAllCookies() {
            ensureWebDriver();
            delegate.manage().deleteAllCookies();
        }

        @Override
        public Set<Cookie> getCookies() {
            ensureWebDriver();
            return delegate.manage().getCookies();
        }

        @Override
        public Cookie getCookieNamed(String name) {
            ensureWebDriver();
            return delegate.manage().getCookieNamed(name);
        }

        @Override
        public Timeouts timeouts() {
            return new DelegatorTimeouts();
        }

        @Override
        public ImeHandler ime() {
            return new DelegatorImeHandler();
        }

        @Override
        public Window window() {
            return new DelegatorWindow();
        }

        @Override
        public Logs logs() {
            return new DelegatorLogs();
        }
    }

    class DelegatorLogs implements Logs {

        @Override
        public LogEntries get(String logType) {
            ensureWebDriver();
            return delegate.manage().logs().get(logType);
        }

        @Override
        public Set<String> getAvailableLogTypes() {
            ensureWebDriver();
            return delegate.manage().logs().getAvailableLogTypes();
        }
    }

    class DelegatorImeHandler implements ImeHandler {

        @Override
        public List<String> getAvailableEngines() {
            ensureWebDriver();
            return delegateIme().getAvailableEngines();
        }

        @Override
        public String getActiveEngine() {
            ensureWebDriver();
            return delegateIme().getActiveEngine();
        }

        @Override
        public boolean isActivated() {
            ensureWebDriver();
            return delegateIme().isActivated();
        }

        @Override
        public void deactivate() {
            ensureWebDriver();
            delegateIme().deactivate();
        }

        @Override
        public void activateEngine(String engine) {
            ensureWebDriver();
            delegateIme().activateEngine(engine);
        }

        private ImeHandler delegateIme() {
            return delegate.manage().ime();
        }
    }

    class DelegatorWindow implements Window {

        @Override
        public void setSize(Dimension targetSize) {
            ensureWebDriver();
            delegateWindow().setSize(targetSize);
        }

        @Override
        public void setPosition(Point targetPosition) {
            ensureWebDriver();
            delegateWindow().setPosition(targetPosition);
        }

        @Override
        public Dimension getSize() {
            ensureWebDriver();
            return delegateWindow().getSize();
        }

        @Override
        public Point getPosition() {
            ensureWebDriver();
            return delegateWindow().getPosition();
        }

        @Override
        public void maximize() {
            ensureWebDriver();
            delegateWindow().maximize();
        }

        @Override
        public void fullscreen() {
            ensureWebDriver();
            delegateWindow().fullscreen();
        }

        private Window delegateWindow() {
            return delegate.manage().window();
        }
    }

    class DelegatorTimeouts implements Timeouts {

        @Override
        public Timeouts implicitlyWait(long time, TimeUnit unit) {
            ensureWebDriver();
            delegate.manage().timeouts().implicitlyWait(time, unit);
            return this;
        }

        @Override
        public Timeouts setScriptTimeout(long time, TimeUnit unit) {
            ensureWebDriver();
            delegate.manage().timeouts().setScriptTimeout(time, unit);
            return this;
        }

        @Override
        public Timeouts pageLoadTimeout(long time, TimeUnit unit) {
            ensureWebDriver();
            delegate.manage().timeouts().pageLoadTimeout(time, unit);
            return this;
        }
    }

    private WebDriver delegate;

    public DelegatorWebDriver() {
    }

    public void setDelegate(WebDriver delegate) {
        if (this.delegate != delegate) {
            setChanged();
            this.delegate = delegate;
            notifyObservers(delegate);
        }
    }

    public WebDriver getDelegate() {
        return delegate;
    }

    public boolean isValid() {
        if (delegate == null) return false;
        try {
            // IE driver keeps providing an empty window handles set even after the browser is closed
            return delegate.getWindowHandles() != null && delegate.getWindowHandles().size() > 0;
        } catch (WebDriverException e) {
            if (e instanceof UnhandledAlertException) {
                return true;
            }
            return false;
        }
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        ensureWebDriver();
        return ((TakesScreenshot) delegate).getScreenshotAs(target);
    }

    @Override
    public Keyboard getKeyboard() {
        return new DelegatorKeyboard();
    }

    @Override
    public Mouse getMouse() {
        return new DelegatorMouse();
    }

    @Override
    public Object executeScript(String script, Object... args) {
        ensureWebDriver();
        return ((JavascriptExecutor) delegate).executeScript(script, args);
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        ensureWebDriver();
        return ((JavascriptExecutor) delegate).executeAsyncScript(script, args);
    }

    @Override
    public void get(String url) {
        ensureWebDriver();
        delegate.get(url);
    }

    @Override
    public String getCurrentUrl() {
        ensureWebDriver();
        return delegate.getCurrentUrl();
    }

    public String getPerformance() {
        ensureWebDriver();
        return PerformanceUtils.getPerformanceJson(delegate, getCurrentUrl());
    }

    @Override
    public String getTitle() {
        ensureWebDriver();
        return delegate.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        ensureWebDriver();
        return delegate.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        ensureWebDriver();
        return delegate.findElement(by);
    }

    @Override
    public String getPageSource() {
        ensureWebDriver();
        return delegate.getPageSource();
    }

    @Override
    public void close() {
        ensureWebDriver();
        delegate.close();
    }

    @Override
    public void quit() {
        ensureWebDriver();
        delegate.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        ensureWebDriver();
        return delegate.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        ensureWebDriver();
        return delegate.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return new DelegatorTargetLocator();
    }

    @Override
    public Navigation navigate() {
        return new DelegatorNavigation();
    }

    @Override
    public Options manage() {
        return new DelegatorOptions();
    }

    private WebDriver ensureWebDriver() {
        return Preconditions.checkNotNull(delegate);
    }

}
