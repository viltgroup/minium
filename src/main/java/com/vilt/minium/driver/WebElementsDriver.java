package com.vilt.minium.driver;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.HasInputDevices;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keyboard;
import org.openqa.selenium.Mouse;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.base.Objects;
import com.vilt.minium.WebElements;
import com.vilt.minium.impl.WebElementsFactory;
import com.vilt.minium.impl.WebElementsFactoryHelper;
import com.vilt.minium.jquery.JQueryWebElements;

/**
 * 
 * @author Rui
 * 
 * @param <T>
 */
public class WebElementsDriver<T extends WebElements> implements WebDriver, JavascriptExecutor, HasInputDevices {

	protected final WebDriver wd;
	protected final WebElementsFactory factory;
	protected final Configuration configuration;
	private String windowHandle;

	public WebElementsDriver(WebDriver wd, WebElementsFactory factory) {
		this(wd, factory, wd.getWindowHandle());
	}
	
	protected WebElementsDriver(WebDriver wd, WebElementsFactory factory, String handle) {
		this.wd = wd;
		this.factory = factory;
		this.configuration = new Configuration();
		this.windowHandle = handle;
	}

	public WebElementsDriver(WebDriver wd, Class<T> elementsInterface, Class<?> ... moreInterfaces) {
		this(wd, new WebElementsFactory(elementsInterface, moreInterfaces));
	}

	public Configuration configuration() {
		return configuration;
	}

	public void get(String url) {
		ensureSwitch();
		wd.get(url);
	}

	public String getCurrentUrl() {
		ensureSwitch();
		return wd.getCurrentUrl();
	}

	public String getTitle() {
		ensureSwitch();
		return wd.getTitle();
	}

	public List<WebElement> findElements(By by) {
		ensureSwitch();
		return wd.findElements(by);
	}

	public WebElement findElement(By by) {
		ensureSwitch();
		return wd.findElement(by);
	}

	public String getPageSource() {
		ensureSwitch();
		return wd.getPageSource();
	}

	public void close() {
		ensureSwitch();
		wd.close();
	}

	public void quit() {
		ensureSwitch();
		wd.quit();
	}

	public Set<String> getWindowHandles() {
		return wd.getWindowHandles();
	}

	public String getWindowHandle() {
		return windowHandle;
	}

	public TargetLocator switchTo() {
		ensureSwitch();
		return wd.switchTo();
	}

	public Navigation navigate() {
		ensureSwitch();
		return wd.navigate();
	}

	public Options manage() {
		ensureSwitch();
		return wd.manage();
	}

	public Keyboard getKeyboard() {
		ensureSwitch();
		return ((HasInputDevices) wd).getKeyboard();
	}

	public Mouse getMouse() {
		ensureSwitch();
		return ((HasInputDevices) wd).getMouse();
	}

	public Object executeScript(String script, Object... args) {
		ensureSwitch();
		return ((JavascriptExecutor) wd).executeScript(script, args);
	}

	public Object executeAsyncScript(String script, Object... args) {
		ensureSwitch();
		return ((JavascriptExecutor) wd).executeAsyncScript(script, args);
	}

	public T webElements() {
		return WebElementsFactoryHelper.<T>createRootWebElements(factory, this);
	}

	@SuppressWarnings("unchecked")
	public T webElements(String selector) {
		return (T) ((JQueryWebElements<T>) webElements()).find(selector);
	}

	public void ensureSwitch() {
		if (!StringUtils.equals(windowHandle, wd.getWindowHandle())) {
			System.out.printf("Switching to %s\n", windowHandle);
			wd.switchTo().window(windowHandle);
		}
		wd.switchTo().defaultContent();
	}

	public WebDriver getWrappedWebDriver() {
		return wd;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof WebElementsDriver))
			return false;

		WebElementsDriver<?> other = (WebElementsDriver<?>) obj;
		
		String windowHandle = getWindowHandle();		
		String otherWindowHandle = other.getWindowHandle();
		
		return Objects.equal(windowHandle, otherWindowHandle);
	}
	
	@Override
	public int hashCode() {
		return getWindowHandle().hashCode();
	}
}
