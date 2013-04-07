package com.vilt.minium;

import static java.lang.String.format;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.HasInputDevices;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keyboard;
import org.openqa.selenium.Mouse;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;
import com.vilt.minium.impl.WebElementsFactory;
import com.vilt.minium.impl.WebElementsFactoryHelper;
import com.vilt.minium.impl.utils.Casts;

/**
 * The Class WebElementsDriver.
 *
 * @param <T> the generic type
 * @author Rui
 */
public class WebElementsDriver<T extends WebElements> implements WebDriver, JavascriptExecutor, HasInputDevices, TakesScreenshot {

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(WebElementsDriver.class);

	/** The wd. */
	protected final WebDriver wd;

	/** The factory. */
	protected final WebElementsFactory factory;

	/** The configuration. */
	protected final Configuration configuration;

	/** The window handle. */
	protected String windowHandle;

	/**
	 * Instantiates a new web elements driver.
	 *
	 * @param wd the wd
	 * @param factory the factory
	 * @param configuration the configuration
	 */
	protected WebElementsDriver(WebDriver wd, WebElementsFactory factory, Configuration configuration) {
		this(wd, factory, configuration, wd.getWindowHandle());
	}
	
	/**
	 * Instantiates a new web elements driver.
	 *
	 * @param wd the wd
	 * @param factory the factory
	 * @param configuration the configuration
	 * @param handle the handle
	 */
	protected WebElementsDriver(WebDriver wd, WebElementsFactory factory, Configuration configuration, String handle) {
		this.wd = wd;
		this.factory = factory;
		this.configuration = configuration;
		this.windowHandle = handle;
	}

	/**
	 * Instantiates a new web elements driver.
	 *
	 * @param wd the wd
	 * @param elementsInterface the elements interface
	 * @param moreInterfaces the more interfaces
	 */
	public WebElementsDriver(WebDriver wd, Class<T> elementsInterface, Class<?> ... moreInterfaces) {
		this(wd, new WebElementsFactory(elementsInterface, moreInterfaces), new Configuration());
	}

	/**
	 * Configuration.
	 *
	 * @return the configuration
	 */
	public Configuration configuration() {
		return configuration;
	}
	
	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#get(java.lang.String)
	 */
	public void get(String url) {
		ensureSwitch();
		wd.get(url);
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getCurrentUrl()
	 */
	public String getCurrentUrl() {
		ensureSwitch();
		return wd.getCurrentUrl();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getTitle()
	 */
	public String getTitle() {
		ensureSwitch();
		return wd.getTitle();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#findElements(org.openqa.selenium.By)
	 */
	public List<WebElement> findElements(By by) {
		ensureSwitch();
		return wd.findElements(by);
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#findElement(org.openqa.selenium.By)
	 */
	public WebElement findElement(By by) {
		ensureSwitch();
		return wd.findElement(by);
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getPageSource()
	 */
	public String getPageSource() {
		ensureSwitch();
		return wd.getPageSource();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#close()
	 */
	public void close() {
		ensureSwitch();
		wd.close();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#quit()
	 */
	public void quit() {
		ensureSwitch();
		wd.quit();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getWindowHandles()
	 */
	public Set<String> getWindowHandles() {
		return wd.getWindowHandles();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getWindowHandle()
	 */
	public String getWindowHandle() {
		return windowHandle;
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#switchTo()
	 */
	public TargetLocator switchTo() {
		ensureSwitch();
		return wd.switchTo();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#navigate()
	 */
	public Navigation navigate() {
		ensureSwitch();
		return wd.navigate();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#manage()
	 */
	public Options manage() {
		ensureSwitch();
		return wd.manage();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.HasInputDevices#getKeyboard()
	 */
	public Keyboard getKeyboard() {
		ensureSwitch();
		return ((HasInputDevices) wd).getKeyboard();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.HasInputDevices#getMouse()
	 */
	public Mouse getMouse() {
		ensureSwitch();
		return ((HasInputDevices) wd).getMouse();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.JavascriptExecutor#executeScript(java.lang.String, java.lang.Object[])
	 */
	public Object executeScript(String script, Object... args) {
		ensureSwitch();
		return ((JavascriptExecutor) wd).executeScript(script, args);
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.JavascriptExecutor#executeAsyncScript(java.lang.String, java.lang.Object[])
	 */
	public Object executeAsyncScript(String script, Object... args) {
		ensureSwitch();
		
		wd.manage().timeouts().setScriptTimeout(configuration().getDefaultTimeout().getTime(), configuration().getDefaultTimeout().getUnit());
		return ((JavascriptExecutor) wd).executeAsyncScript(script, args);
	}

	/**
	 * Checks if is closed.
	 *
	 * @return true, if is closed
	 */
	public boolean isClosed() {
		return !wd.getWindowHandles().contains(windowHandle);
	}
	
	/* (non-Javadoc)
	 * @see org.openqa.selenium.TakesScreenshot#getScreenshotAs(org.openqa.selenium.OutputType)
	 */
	@Override
	public <X> X getScreenshotAs(OutputType<X> type) throws WebDriverException {
		ensureSwitch();
		return ((TakesScreenshot) wd).<X>getScreenshotAs(type);
	}
	
	/**
	 * Web elements.
	 *
	 * @return the t
	 */
	public T webElements() {
		return Casts.<T>cast(WebElementsFactoryHelper.createRootWebElements(factory, this));
	}

	/**
	 * Web elements.
	 *
	 * @param selector the selector
	 * @return the t
	 */
	public T find(String selector) {
		return Casts.<JQueryWebElements<T>>cast(webElements()).find(selector);
	}

	/**
	 * Ensure switch.
	 */
	public void ensureSwitch() {
		String windowHandleOrNull = safeGetWindowHandle();
	
		if (windowHandleOrNull == null || !StringUtils.equals(windowHandle, windowHandleOrNull)) {
			wd.switchTo().window(windowHandle);
			logger.debug("Switched to window with handle '{}'", windowHandle);
		}
		wd.switchTo().defaultContent();
	}

	/**
	 * Gets the wrapped web driver.
	 *
	 * @return the wrapped web driver
	 */
	public WebDriver getWrappedWebDriver() {
		return wd;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof WebElementsDriver))
			return false;

		WebElementsDriver<?> other = (WebElementsDriver<?>) obj;
		
		String windowHandle = getWindowHandle();		
		String otherWindowHandle = other.getWindowHandle();
		
		return Objects.equal(windowHandle, otherWindowHandle);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getWindowHandle().hashCode();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return format("wd");
	}
	
	private String safeGetWindowHandle() {
		try {
			return wd.getWindowHandle();
		} catch (Exception e) {
			return null;
		}
	}

}
