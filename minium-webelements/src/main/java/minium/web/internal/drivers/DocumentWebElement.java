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
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import minium.web.DocumentWebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsDriver;

import com.google.common.base.Objects;

public class DocumentWebElement implements WebElement, WrapsDriver, Locatable {

    private final WebElement webElement;
    private final InternalDocumentWebDriver webDriver;

    public DocumentWebElement(WebElement webElement, DocumentWebDriver webDriver) {
        checkNotNull(webElement);
        checkNotNull(webDriver);

        this.webElement = webElement;
        this.webDriver = (InternalDocumentWebDriver) webDriver;
    }

    @Override
    public void click() {
        webDriver.ensureSwitch();
        webElement.click();
    }

    @Override
    public void submit() {
        webDriver.ensureSwitch();
        webElement.submit();
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        webDriver.ensureSwitch();
        webElement.sendKeys(keysToSend);
    }

    @Override
    public void clear() {
        webDriver.ensureSwitch();
        webElement.clear();
    }

    @Override
    public String getTagName() {
        webDriver.ensureSwitch();
        return webElement.getTagName();
    }

    @Override
    public String getAttribute(String name) {
        webDriver.ensureSwitch();
        return webElement.getAttribute(name);
    }

    @Override
    public boolean isSelected() {
        webDriver.ensureSwitch();
        return webElement.isSelected();
    }

    @Override
    public boolean isEnabled() {
        webDriver.ensureSwitch();
        return webElement.isEnabled();
    }

    @Override
    public String getText() {
        webDriver.ensureSwitch();
        return webElement.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        webDriver.ensureSwitch();
        return webElement.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        webDriver.ensureSwitch();
        return webElement.findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        webDriver.ensureSwitch();
        return webElement.isDisplayed();
    }

    @Override
    public Point getLocation() {
        webDriver.ensureSwitch();
        return webElement.getLocation();
    }

    @Override
    public Dimension getSize() {
        webDriver.ensureSwitch();
        return webElement.getSize();
    }

    @Override
    public String getCssValue(String propertyName) {
        webDriver.ensureSwitch();
        return webElement.getCssValue(propertyName);
    }

    @Override
    public Coordinates getCoordinates() {
        webDriver.ensureSwitch();
        return ((Locatable) webElement).getCoordinates();
    }

    @Override
    public DocumentWebDriver getWrappedDriver() {
        return webDriver;
    }

    public WebElement getWrappedWebElement() {
        return webElement;
    }
    
    @Override
    public <X> X getScreenshotAs(OutputType<X> type) throws WebDriverException {
        webDriver.ensureSwitch();
        return webElement.getScreenshotAs(type);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DocumentWebElement)) return false;
        return Objects.equal(webElement, ((DocumentWebElement) obj).webElement);
    }

    @Override
    public int hashCode() {
        return webElement.hashCode();
    }

    @Override
    public String toString() {
        return toStringHelper(DocumentWebElement.class.getSimpleName())
                .addValue(webElement)
                .addValue(webDriver)
                .toString();
    }
}
