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