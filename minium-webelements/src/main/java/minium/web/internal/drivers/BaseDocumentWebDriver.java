package minium.web.internal.drivers;

import minium.web.DocumentWebDriver;

import java.util.List;
import java.util.Set;

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

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public abstract class BaseDocumentWebDriver implements InternalDocumentWebDriver {

    protected final WebDriver webDriver;

    public BaseDocumentWebDriver(WebDriver webDriver) {
        Preconditions.checkArgument(webDriver != null && !(webDriver instanceof DocumentWebDriver));
        this.webDriver = webDriver;
    }

    @Override
    public void get(String url) {
        ensureSwitch();
        webDriver.get(url);
    }

    @Override
    public String getCurrentUrl() {
        ensureSwitch();
        return webDriver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        ensureSwitch();
        return webDriver.getCurrentUrl();
    }

    @Override
    public List<WebElement> findElements(By by) {
        ensureSwitch();
        return webDriver.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        ensureSwitch();
        return webDriver.findElement(by);
    }

    @Override
    public String getPageSource() {
        ensureSwitch();
        return webDriver.getPageSource();
    }

    @Override
    public void close() {
        ensureSwitch();
        webDriver.close();
    }

    @Override
    public void quit() {
        webDriver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        ensureSwitch();
        return webDriver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        ensureSwitch();
        return webDriver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        ensureSwitch();
        return webDriver.switchTo();
    }

    @Override
    public Navigation navigate() {
        ensureSwitch();
        return webDriver.navigate();
    }

    @Override
    public Options manage() {
        ensureSwitch();
        return webDriver.manage();
    }

    @Override
    public Object executeScript(String script, Object... args) {
        ensureSwitch();
        return ((JavascriptExecutor) webDriver).executeScript(script, args);
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        ensureSwitch();
        return ((JavascriptExecutor) webDriver).executeAsyncScript(script, args);
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
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        Preconditions.checkState(webDriver instanceof TakesScreenshot);
        return ((TakesScreenshot) webDriver).getScreenshotAs(target);
    }

    @Override
    public WebDriver nativeWebDriver() {
        return webDriver;
    }

    @Override
    public int hashCode() {
        return webDriver.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == BaseDocumentWebDriver.class && equalFields((BaseDocumentWebDriver) obj);
    }

    protected boolean equalFields(BaseDocumentWebDriver obj) {
        return Objects.equal(webDriver, obj.webDriver);
    }
}