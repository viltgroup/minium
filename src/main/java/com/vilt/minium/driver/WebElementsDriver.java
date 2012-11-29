package com.vilt.minium.driver;

import static java.lang.String.format;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;
import com.vilt.minium.WebElements;
import com.vilt.minium.impl.WebElementsFactory;
import com.vilt.minium.impl.WebElementsFactoryHelper;
import com.vilt.minium.impl.utils.Casts;
import com.vilt.minium.jquery.JQueryWebElements;

/**
 * 
 * @author Rui
 * 
 * @param <T>
 */
public class WebElementsDriver<T extends WebElements> implements WebDriver, JavascriptExecutor, HasInputDevices {

	final Logger logger = LoggerFactory.getLogger(WebElementsDriver.class);
	
	protected final WebDriver wd;
	protected final WebElementsFactory factory;
	protected final Configuration configuration;
	protected String windowHandle;

//	private boolean isFirefox;

	public WebElementsDriver(WebDriver wd, WebElementsFactory factory) {
		this(wd, factory, wd.getWindowHandle());
	}
	
	protected WebElementsDriver(WebDriver wd, WebElementsFactory factory, String handle) {
		this.wd = wd;
		this.factory = factory;
		this.configuration = new Configuration();
		this.windowHandle = handle;
		// we need this because of a bug on firefox that hangs when we try to get a window 
		// handle for a closed window...
//		isFirefox = (wd instanceof FirefoxDriver || hasFirefoxCapabilities());

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
		
		wd.manage().timeouts().setScriptTimeout(configuration().getDefaultTimeout().getTime(), configuration().getDefaultTimeout().getUnit());
		return ((JavascriptExecutor) wd).executeAsyncScript(script, args);
	}

	public boolean isClosed() {
		return !wd.getWindowHandles().contains(windowHandle);
	}
	
	public T webElements() {
		return Casts.<T>cast(WebElementsFactoryHelper.createRootWebElements(factory, this));
	}

	public T webElements(String selector) {
		return Casts.<JQueryWebElements<T>>cast(webElements()).find(selector);
	}

	public void ensureSwitch() {
		String windowHandleOrNull = safeGetWindowHandle();
	
		if (windowHandleOrNull == null || !StringUtils.equals(windowHandle, windowHandleOrNull)) {
			wd.switchTo().window(windowHandle);
			logger.debug("Switched to window with handle '{}'", windowHandle);
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
	
	@Override
	public String toString() {
		return format("wd");
	}
	
	private String safeGetWindowHandle() {
//		if (!isFirefox) {
		try {
			return wd.getWindowHandle();
		} catch (Exception e) {
			return null;
		}
//		} else {
//			synchronized(wd) {
//				ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
//				final Future<String> windowHandleFuture = executor.submit(new Callable<String>() {
//					@Override
//					public String call() throws Exception {
//						return wd.getWindowHandle();
//					}
//				});
//			
//				executor.schedule(new Runnable() {
//				     public void run() {
//				    	 windowHandleFuture.cancel(true);
//				     }      
//				 }, 5L, TimeUnit.SECONDS);
//			}
//		}
	}

//	private boolean hasFirefoxCapabilities() {
//		return (wd instanceof HasCapabilities && DesiredCapabilities.firefox().getBrowserName().equalsIgnoreCase(((HasCapabilities) wd).getCapabilities().getBrowserName()));
//	}

}
