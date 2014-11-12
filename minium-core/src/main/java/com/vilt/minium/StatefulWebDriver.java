package com.vilt.minium;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
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
import com.google.common.base.Preconditions;

public class StatefulWebDriver implements WebDriver, JavascriptExecutor, HasInputDevices, TakesScreenshot {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatefulWebDriver.class);

    private final class StatefulTargetLocator implements TargetLocator {

        private String nameOrHandle;

        private Object frame;

        private StatefulTargetLocator() {
            this.nameOrHandle = webDriver.getWindowHandle();
        }

        @Override
        public WebDriver window(String nameOrHandle) {
            if (!Objects.equal(nameOrHandle, this.nameOrHandle)) {
                webDriver.switchTo().window(nameOrHandle);
                LOGGER.trace("Switched window from {} to {}", this.nameOrHandle, nameOrHandle);
            } else if (this.frame != null) {
                // if we don't actually switch window because we're already there,
                // at least we need to go to the top of the page
                webDriver.switchTo().defaultContent();
                LOGGER.trace("Switched to default content of window {}", this.nameOrHandle);
            }
            this.nameOrHandle = nameOrHandle;
            this.frame = null;
            return StatefulWebDriver.this;
        }

        @Override
        public WebDriver frame(WebElement frameElement) {
            if (!frameElement.equals(frame)) {
                webDriver.switchTo().frame(frameElement);
                logSwitchFrame(frameElement);
            }
            this.frame = frameElement;
            return StatefulWebDriver.this;
        }

        @Override
        public WebDriver frame(String nameOrId) {
            if (!nameOrId.equals(frame)) {
                webDriver.switchTo().frame(nameOrId);
                logSwitchFrame(nameOrId);
            }
            this.frame = nameOrId;
            return StatefulWebDriver.this;
        }

        @Override
        public WebDriver frame(int index) {
            if (!(frame instanceof Integer) || ((Integer) frame).intValue() != index) {
                webDriver.switchTo().frame(index);
                logSwitchFrame(index);
            }
            this.frame = index;
            return StatefulWebDriver.this;
        }

        @Override
        public WebDriver defaultContent() {
            if (this.frame != null) {
                webDriver.switchTo().defaultContent();
                LOGGER.trace("Switched to default content of window {}", this.nameOrHandle);
            }
            this.frame = null;
            return StatefulWebDriver.this;
        }

        @Override
        public WebDriver parentFrame() {
            // TODO replace frame field with a stack
            throw new UnsupportedOperationException();
        }

        @Override
        public Alert alert() {
            return webDriver.switchTo().alert();
        }

        @Override
        public WebElement activeElement() {
            return webDriver.switchTo().activeElement();
        }

        public String getWindowHandle() {
            return nameOrHandle;
        }

        protected void logSwitchFrame(Object frame) {
            if (this.frame == null) {
                LOGGER.trace("Switched to frame {} of window {}", frame, this.nameOrHandle);
            } else {
                LOGGER.trace("Switched from frame {} to frame {} of window {}", this.frame, frame, this.nameOrHandle);
            }
        }

    }

    protected final WebDriver webDriver;

    private final StatefulTargetLocator statefulTargetLocator;

    public StatefulWebDriver(WebDriver webDriver) {
        Preconditions.checkNotNull(webDriver);
        this.webDriver = webDriver;
        this.statefulTargetLocator = new StatefulTargetLocator();
    }

    @Override
    public void get(String url) {
        webDriver.get(url);
    }

    @Override
    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return webDriver.getCurrentUrl();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return webDriver.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return webDriver.findElement(by);
    }

    @Override
    public String getPageSource() {
        return webDriver.getPageSource();
    }

    @Override
    public void close() {
        webDriver.close();
    }

    @Override
    public void quit() {
        webDriver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return webDriver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return statefulTargetLocator.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return statefulTargetLocator;
    }

    @Override
    public Navigation navigate() {
        return webDriver.navigate();
    }

    @Override
    public Options manage() {
        return webDriver.manage();
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return ((TakesScreenshot) webDriver).getScreenshotAs(target);
    }

    @Override
    public Keyboard getKeyboard() {
        return ((HasInputDevices) webDriver).getKeyboard();
    }

    @Override
    public Mouse getMouse() {
        return ((HasInputDevices) webDriver).getMouse();
    }

    @Override
    public Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) webDriver).executeScript(script, args);
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        return ((JavascriptExecutor) webDriver).executeAsyncScript(script, args);
    }
}
