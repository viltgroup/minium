package com.vilt.minium.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsDriver;

import com.vilt.minium.WebElementsDriver;

public class DelegateWebElement implements WebElement, WrapsDriver, Locatable {

	private WebElement webElement;
	private WebElementsDriver<?> webDriver;

	public DelegateWebElement(WebElement webElement, WebElementsDriver<?> webDriver) {
		checkNotNull(webElement);
		checkNotNull(webDriver);
		
		this.webElement = webElement;
		this.webDriver = webDriver;
	}
	
	public void click() {
		webDriver.ensureSwitch();
		webElement.click();
	}

	public void submit() {
		webDriver.ensureSwitch();
		webElement.submit();
	}

	public void sendKeys(CharSequence... keysToSend) {
		webDriver.ensureSwitch();
		webElement.sendKeys(keysToSend);
	}

	public void clear() {
		webDriver.ensureSwitch();
		webElement.clear();
	}

	public String getTagName() {
		webDriver.ensureSwitch();
		return webElement.getTagName();
	}

	public String getAttribute(String name) {
		webDriver.ensureSwitch();
		return webElement.getAttribute(name);
	}

	public boolean isSelected() {
		webDriver.ensureSwitch();
		return webElement.isSelected();
	}

	public boolean isEnabled() {
		webDriver.ensureSwitch();
		return webElement.isEnabled();
	}

	public String getText() {
		webDriver.ensureSwitch();
		return webElement.getText();
	}

	public List<WebElement> findElements(By by) {
		webDriver.ensureSwitch();
		return webElement.findElements(by);
	}

	public WebElement findElement(By by) {
		webDriver.ensureSwitch();
		return webElement.findElement(by);
	}

	public boolean isDisplayed() {
		webDriver.ensureSwitch();
		return webElement.isDisplayed();
	}

	public Point getLocation() {
		webDriver.ensureSwitch();
		return webElement.getLocation();
	}

	public Dimension getSize() {
		webDriver.ensureSwitch();
		return webElement.getSize();
	}

	public String getCssValue(String propertyName) {
		webDriver.ensureSwitch();
		return webElement.getCssValue(propertyName);
	}

	
	@Override
	@Deprecated
	public Point getLocationOnScreenOnceScrolledIntoView() {
		webDriver.ensureSwitch();
		return ((Locatable) webElement).getLocationOnScreenOnceScrolledIntoView();
	}

	@Override
	public Coordinates getCoordinates() {
		webDriver.ensureSwitch();
		return ((Locatable) webElement).getCoordinates();
	}

	@Override
	public WebElementsDriver<?> getWrappedDriver() {
		return webDriver;
	}
	
	public WebElement getWrappedWebElement() {
		return webElement;
	}
	
	@Override
	public boolean equals(Object obj) {
		return webElement.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return webElement.hashCode();
	}
	
	@Override
	public String toString() {
		return webElement.toString();
	}
}
