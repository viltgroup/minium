package minium.web.internal.drivers;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.util.Observable;
import java.util.Observer;

import minium.web.DelegatorWebDriver;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

public class WindowDelegatorWebDriver extends BaseDocumentWebDriver implements Observer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WindowDelegatorWebDriver.class);

    private String windowHandle;

    public WindowDelegatorWebDriver(DelegatorWebDriver webDriver) {
        super(webDriver);
        webDriver.addObserver(this);
        WebDriver delegateWebDriver = webDriver.getDelegate();
        if (delegateWebDriver != null) {
            this.windowHandle = delegateWebDriver.getWindowHandle();
        }
    }

    @Override
    public void ensureSwitch() {
        Preconditions.checkState(windowHandle != null);
        webDriver.switchTo().window(windowHandle);
        LOGGER.trace("Switched to window {}", windowHandle);
    }

    @Override
    public boolean isClosed() {
        Preconditions.checkState(windowHandle != null);
        return !getWindowHandles().contains(windowHandle);
    }

    @Override
    public void quit() {
        super.quit();
        ((DelegatorWebDriver) webDriver).deleteObserver(this);
    }

    @Override
    public String toString() {
        return toStringHelper(WindowDelegatorWebDriver.class.getSimpleName())
            .addValue(webDriver)
            .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), webDriver);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == WindowDelegatorWebDriver.class && equalFields((WindowDelegatorWebDriver) obj);
    }

    protected boolean equalFields(WindowDelegatorWebDriver obj) {
        return super.equalFields(obj) && Objects.equal(webDriver, obj.webDriver);
    }

    @Override
    public void update(Observable o, Object arg) {
        WebDriver webDriver = (WebDriver) arg;
        this.windowHandle = webDriver == null ? null : webDriver.getWindowHandle();
    }
}