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
package minium.web.internal.drivers;

import static org.mockito.Mockito.spy;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import minium.web.WebElements;
import minium.web.internal.drivers.DefaultJavascriptInvoker.ResponseType;
import minium.web.internal.expression.ExpressionWebElementExpressionizer;
import minium.web.internal.expression.Expressionizer;
import minium.web.internal.expression.VariableGenerator;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.logging.Logs;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class MockWebDriver implements WebDriver, HasInputDevices, JavascriptExecutor {

    public class MockWhen {

        private final String expression;

        public MockWhen(String expression) {
            this.expression = expression;
        }

        public void thenReturn(WebElement ... nativeElems) {
            expressionToWebElements.put(expression, ImmutableList.copyOf(nativeElems));
        }
    }

    class MockKeyboard implements Keyboard {
        @Override
        public void sendKeys(CharSequence... keysToSend) {
        }

        @Override
        public void pressKey(CharSequence keyToPress) {
        }

        @Override
        public void releaseKey(CharSequence keyToRelease) {
        }
    }

    class MockMouse implements Mouse {

        @Override
        public void click(Coordinates where) {
        }

        @Override
        public void doubleClick(Coordinates where) {
        }

        @Override
        public void mouseDown(Coordinates where) {
        }

        @Override
        public void mouseUp(Coordinates where) {
        }

        @Override
        public void mouseMove(Coordinates where) {
        }

        @Override
        public void mouseMove(Coordinates where, long xOffset, long yOffset) {
        }

        @Override
        public void contextClick(Coordinates where) {
        }
    }

    class MockNavigation implements Navigation {

        @Override
        public void back() {
        }

        @Override
        public void forward() {
        }

        @Override
        public void to(String url) {
            MockWebDriver.this.url = url;
        }

        @Override
        public void to(URL url) {
            MockWebDriver.this.url = url.toString();
        }

        @Override
        public void refresh() {
        }
    }

    class MockTargetLocator implements TargetLocator {

        @Override
        public WebDriver frame(int index) {
            return MockWebDriver.this;
        }

        @Override
        public WebDriver frame(String nameOrId) {
            return MockWebDriver.this;
        }

        @Override
        public WebDriver frame(WebElement frameElement) {
            return MockWebDriver.this;
        }

        @Override
        public WebDriver parentFrame() {
            return MockWebDriver.this;
        }

        @Override
        public WebDriver window(String nameOrHandle) {
            return MockWebDriver.this;
        }

        @Override
        public WebDriver defaultContent() {
            return MockWebDriver.this;
        }

        @Override
        public WebElement activeElement() {
            return null;
        }

        @Override
        public Alert alert() {
            throw new NoAlertPresentException();
        }
    }

    class MockOptions implements Options {

        @Override
        public void addCookie(Cookie cookie) {
        }

        @Override
        public void deleteCookieNamed(String name) {
        }

        @Override
        public void deleteCookie(Cookie cookie) {
        }

        @Override
        public void deleteAllCookies() {
        }

        @Override
        public Set<Cookie> getCookies() {
            return Collections.emptySet();
        }

        @Override
        public Cookie getCookieNamed(String name) {
            return null;
        }

        @Override
        public Timeouts timeouts() {
            return null;
        }

        @Override
        public ImeHandler ime() {
            return null;
        }

        @Override
        public Window window() {
            return mockedWindow;
        }

        @Override
        public Logs logs() {
            return null;
        }
    }

    class MockedWindow implements Window {

        private Dimension targetSize = new Dimension(1920, 1080);
        private Point targetPosition = new Point(0, 0);

        @Override
        public void setSize(Dimension targetSize) {
            this.targetSize = targetSize;
        }

        @Override
        public void setPosition(Point targetPosition) {
            this.targetPosition  = targetPosition;
        }

        @Override
        public Dimension getSize() {
            return targetSize;
        }

        @Override
        public Point getPosition() {
            return targetPosition;
        }

        @Override
        public void maximize() {
        }

        @Override
        public void fullscreen() {
        }
    }

    private String url;
    private MockKeyboard mockKeyboard = spy(new MockKeyboard());
    private MockMouse mockMouse = spy(new MockMouse());
    private MockTargetLocator mockTargetLocator = spy(new MockTargetLocator());
    private MockNavigation mockNavigation = spy(new MockNavigation());
    private MockOptions mockOptions = spy(new MockOptions());
    private MockedWindow mockedWindow = spy(new MockedWindow());
    private Expressionizer expressionizer = new Expressionizer.Composite().add(new ExpressionWebElementExpressionizer());
    private Map<String, List<WebElement>> expressionToWebElements = Maps.newHashMap();

    @Override
    public Keyboard getKeyboard() {
        return mockKeyboard;
    }

    @Override
    public Mouse getMouse() {
        return mockMouse;
    }

    @Override
    public void get(String url) {
        this.url = url;
    }

    @Override
    public String getCurrentUrl() {
        return url;
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public List<WebElement> findElements(By by) {
        return Collections.emptyList();
    }

    @Override
    public WebElement findElement(By by) {
        return null;
    }

    @Override
    public String getPageSource() {
        return "";
    }

    @Override
    public void close() {
    }

    @Override
    public void quit() {
    }

    @Override
    public Set<String> getWindowHandles() {
        return Collections.singleton("default");
    }

    @Override
    public String getWindowHandle() {
        return "default";
    }

    @Override
    public TargetLocator switchTo() {
        return  mockTargetLocator;
    }

    @Override
    public Navigation navigate() {
        return mockNavigation;
    }

    @Override
    public Options manage() {
        return mockOptions;
    }

    @Override
    public final Object executeScript(String script, Object... args) {
        Object obj = doExecuteScript(script, args);
        if (obj == null) {
            return getResults(ResponseType.NULL);
        } else if (obj instanceof List<?>) {
            return getResults(ResponseType.ARRAY, ((List<?>) obj).toArray());
        } else if (obj instanceof Object[]) {
            return getResults(ResponseType.ARRAY, (Object[]) obj);
        } else if (obj instanceof Number) {
            return getResults(ResponseType.NUMBER, obj);
        } else if (obj instanceof String) {
            return getResults(ResponseType.STRING, obj);
        } else if (obj instanceof Boolean) {
            return getResults(ResponseType.BOOLEAN, obj);
        }
        throw new IllegalStateException();
    }

    private List<Object> getResults(ResponseType type, Object ... vals) {
        List<Object> results = Lists.newArrayList();
        results.add(type.toString());
        results.addAll(ImmutableList.copyOf(vals));
        return results;
    }

    @Override
    public final Object executeAsyncScript(String script, Object... args) {
        return executeScript(script, args);
    }

    protected Object doExecuteScript(String script, Object ... args) {
        String exprToEvaluate = (String) args[0];
        for (Entry<String, List<WebElement>> entry : expressionToWebElements.entrySet()) {
            String expr = entry.getKey();
            // first check if trying to get size
            if (Objects.equal(exprToEvaluate, expr + ".size()")) {
                return entry.getValue().size();
            } else if (Objects.equal(exprToEvaluate, expr)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public MockWhen when(WebElements elems) {
        return when(expressionizer.apply(elems).getJavascript(new VariableGenerator.Impl()));
    }

    public MockWhen when(String expr) {
        return new MockWhen(expr);
    }

    public void reset() {
        expressionToWebElements.clear();
    }
}
