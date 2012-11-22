package com.vilt.minium.driver.impl;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.google.common.base.Objects;
import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.impl.DelegateWebElement;
import com.vilt.minium.impl.WebElementsFactory;

public class FrameWebElementsDriver<T extends WebElements> extends WebElementsDriver<T> {

	private final WebElement elem;
	private WebElementsDriver<T> parentWebDriver;

	public FrameWebElementsDriver(WebElementsDriver<T> wd, WebElementsFactory factory, WebElement elem) {
		super(wd.getWrappedWebDriver(), factory);
		this.parentWebDriver = wd;
		this.elem = elem;
	}

	@Override
	public void ensureSwitch() {
		parentWebDriver.ensureSwitch();
		// we reposition the WebDriver to the corresponding frame
		if (getNativeWebElement() != null) {
			// workaround as described in http://code.google.com/p/selenium/issues/detail?id=1969#c13
			String id = getNativeWebElement().getAttribute("id");
			if (StringUtils.isEmpty(id)) {
				// if no id is set, then we generate a random one and set the iframe with it
				id = UUID.randomUUID().toString();
				((JavascriptExecutor) wd).executeScript("arguments[0].setAttribute('id', arguments[1]);", getNativeWebElement(), id);					
			}
			
			try {
				wd.switchTo().frame(id);
			} catch (Exception e) {
				wd.switchTo().frame(getNativeWebElement());
			}
		}
	}

	public WebElement getNativeWebElement() {
		return elem instanceof DelegateWebElement ? ((DelegateWebElement) elem).getWrappedWebElement() : elem;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof FrameWebElementsDriver))
			return false;

		return Objects.equal(parentWebDriver, ((FrameWebElementsDriver<?>) obj).parentWebDriver) &&
			   Objects.equal(getNativeWebElement(), ((FrameWebElementsDriver<?>) obj).elem);
	}
	
	@Override
	public int hashCode() {
		return getNativeWebElement().hashCode();
	}
}
