package minium.web.internal.drivers;

import static com.google.common.base.MoreObjects.toStringHelper;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;

public class WindowWebDriver extends BaseDocumentWebDriver {

    private static final Logger LOGGER = LoggerFactory.getLogger(WindowWebDriver.class);

    private final String windowHandle;

    public WindowWebDriver(WebDriver webDriver) {
        this(webDriver, null);
    }

    public WindowWebDriver(WebDriver webDriver, String windowHandle) {
        super(webDriver);
        this.windowHandle = windowHandle == null ? webDriver.getWindowHandle() : windowHandle;
    }

    @Override
    public void ensureSwitch() {
        webDriver.switchTo().window(windowHandle);
        LOGGER.trace("Switched to window {}", windowHandle);
    }

    @Override
    public boolean isClosed() {
        return !getWindowHandles().contains(windowHandle);
    }

    @Override
    public String toString() {
        return toStringHelper(WindowWebDriver.class.getSimpleName())
            .addValue(windowHandle)
            .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), windowHandle);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == WindowWebDriver.class && equalFields((WindowWebDriver) obj);
    }

    protected boolean equalFields(WindowWebDriver obj) {
        return super.equalFields(obj) && Objects.equal(windowHandle, obj.windowHandle);
    }
}