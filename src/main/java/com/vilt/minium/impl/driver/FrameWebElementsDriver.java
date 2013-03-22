package com.vilt.minium.impl.driver;

import static java.lang.String.format;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;
import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.impl.DelegateWebElement;
import com.vilt.minium.impl.WebElementsFactory;

public class FrameWebElementsDriver<T extends WebElements> extends WebElementsDriver<T> {

	final Logger logger = LoggerFactory.getLogger(FrameWebElementsDriver.class);
	
	private final WebElement elem;
	private WebElementsDriver<T> parentWebDriver;

	public FrameWebElementsDriver(WebElementsDriver<T> wd, WebElementsFactory factory, WebElement elem) {
		super(wd.getWrappedWebDriver(), factory, wd.configuration());
		this.parentWebDriver = wd;
		this.elem = elem;
	}

	@Override
	public void ensureSwitch() {
		parentWebDriver.ensureSwitch();
		// we reposition the WebDriver to the corresponding frame
		if (getNativeWebElement() != null) {
			// workaround as described in http://code.google.com/p/selenium/issues/detail?id=1969#c13
			String id = getFrameId();
			
			try {
				wd.switchTo().frame(id);
				logger.debug("Switched to frame with id '{}'", id, windowHandle);
			} catch (Exception e) {
				wd.switchTo().frame(getNativeWebElement());
			}
		}
	}

	protected String getFrameId() {
		String id = getNativeWebElement().getAttribute("id");
		if (StringUtils.isEmpty(id)) {
			// if no id is set, then we generate a random one and set the iframe with it
			id = UUID.randomUUID().toString();
			((JavascriptExecutor) wd).executeScript("arguments[0].setAttribute('id', arguments[1]);", getNativeWebElement(), id);					
		}
		return id;
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
		return Objects.hashCode(getNativeWebElement(), getNativeWebElement());
	}
	
	@Override
	public String toString() {
		return format("frame(id='%s')", getNativeWebElement().getAttribute("id"));
	}
}
