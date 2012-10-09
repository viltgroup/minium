package com.vilt.minium.impl.elements;

import static com.google.common.collect.FluentIterable.from;
import static java.lang.String.format;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.vilt.minium.TargetLocatorWebElements;
import com.vilt.minium.WaitWebElements;
import com.vilt.minium.WebElements;
import com.vilt.minium.driver.Configuration.Duration;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.impl.DelegateWebElement;
import com.vilt.minium.impl.WebElementsFactory;
import com.vilt.minium.impl.WebElementsFactoryHelper;
import com.vilt.minium.jquery.JQueryWebElements;

public abstract class BaseWebElementsImpl<T extends WebElements<T>> implements WebElements<T>, TargetLocatorWebElements<T>, WaitWebElements<T> {

	private final class ArgToStringFunction implements Function<Object, String> {
		public String apply(Object input) {
			if (input == null) return "null";
			if (input instanceof String) return format("'%s'", StringEscapeUtils.escapeJavaScript((String) input));
			if (input instanceof Boolean) return input.toString();
			if (input instanceof Number) return input.toString();
			if (input instanceof Date) return format("new Date(%d)", ((Date) input).getTime());
			if (input instanceof BaseWebElementsImpl) return getJQueryWebElementsExpression(input);
			
			return format("'%s'", StringEscapeUtils.escapeJavaScript(input.toString()));
		}

		@SuppressWarnings("unchecked")
		private String getJQueryWebElementsExpression(Object input) {
			BaseWebElementsImpl<T> elem = (BaseWebElementsImpl<T>) input;
			
//			HashSet<WebElementsDriver<?>> elemWebDrivers = Sets.<WebElementsDriver<?>>newHashSet(elem.webDrivers());
//			HashSet<WebElementsDriver<?>> thisWebDrivers = Sets.<WebElementsDriver<?>>newHashSet(BaseWebElementsImpl.this.webDrivers());
//			
//			if (Sets.intersection(elemWebDrivers, thisWebDrivers).isEmpty()) {
//			 	throw new IllegalArgumentException("Web elements don't belong to the same web driver...");
//			}
			System.out.println("TODO: check same web driver (or root web element)");
			
			return elem.getExpression();
		}
	}
	
	protected WebElementsFactory factory;

	protected abstract String getExpression();
	
	protected abstract Iterable<WebElement> computeElements(final WebElementsDriver<T> wd);
	
	protected abstract Iterable<WebElementsDriver<T>> webDrivers();
	
	protected abstract WebElementsDriver<T> rootWebDriver();
	
	public void init(WebElementsFactory factory) {
		this.factory = factory;
	}
	
	@SuppressWarnings("unchecked")
	public Object invoke(Method method, Object ... args) {
		String expression = computeExpression(this, method.getName(), args);
		
		if (method.getReturnType().isAssignableFrom(this.getClass())) {
			T webElements = (T) WebElementsFactoryHelper.createExpressionWebElements(factory, (T) this, expression);
			return webElements;
		}
		else {
			Iterable<WebElementsDriver<T>> webDrivers = webDrivers();		
			
			if (method.getReturnType() == Void.TYPE) {
				for (WebElementsDriver<T> wd : webDrivers) {
					factory.getInvoker().invokeExpression(wd, expression, args);
				}
				return null;
			}
			else {
				if (Iterables.size(webDrivers) == 1) {
					WebElementsDriver<T> wd = Iterables.get(webDrivers, 0);
					return factory.getInvoker().invokeExpression(wd, expression, args);
				}
				else {
					throw new IllegalStateException("Not implemented yet");
				}
			}
		}
	}
	
	protected final Iterable<WebElement> computeElements() {
		return Iterables.concat(Iterables.transform(webDrivers(), new Function<WebElementsDriver<T>, Iterable<WebElement>>() {

			@Override
			@Nullable
			public Iterable<WebElement> apply(@Nullable final WebElementsDriver<T> wd) {
				return Iterables.transform(computeElements(wd), new Function<WebElement, WebElement>() {

					@Override
					@Nullable
					public WebElement apply(@Nullable WebElement input) {
						return new DelegateWebElement(input, wd);
					}
				});
			}
		}));
	}
	
	protected String computeExpression(BaseWebElementsImpl<T> parent, String fnName, Object ... args) {
		Collection<String> jsArgs = 
				from(Arrays.asList(args)).
				transform(new ArgToStringFunction()).
				toImmutableList();

		if (parent instanceof BaseRootWebElementsImpl<?> && "find".equals(fnName)) {
			return format("$(%s)", StringUtils.join(jsArgs, ", "));		
		}
		else {
			return format("%s.%s(%s)", parent.getExpression(), fnName, StringUtils.join(jsArgs, ", "));
		}
	}

	@Override
	public final Iterator<WebElement> iterator() {
		return computeElements().iterator();
	}

	@Override
	public WebElement get(int index) {
		return Iterables.get(this, index);
	}

	@Override
	public T wait(Predicate<? super T> predicate) {
		Duration timeout = rootWebDriver().configuration().getDefaultTimeout();
		
		long time = timeout.getTime();
		TimeUnit unit = timeout.getUnit();
		
		return wait(time, unit, predicate);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T wait(long time, TimeUnit unit, Predicate<? super T> predicate) {
		Duration interval = rootWebDriver().configuration().getDefaultInterval();
		Wait<T> wait = new FluentWait<T>((T) this).
				withTimeout(time, unit).
				pollingEvery(interval.getTime(), interval.getUnit());
		
		Function<? super T, Boolean> function = Functions.forPredicate(predicate);
		wait.until(function);
		
		return (T) this;
	}
	
	@Override
	public T waitOrTimeout(Predicate<? super T> predicate) {
		Duration timeout = rootWebDriver().configuration().getDefaultTimeout();
		
		long time = timeout.getTime();
		TimeUnit unit = timeout.getUnit();
		
		return waitOrTimeout(time, unit, predicate);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T waitOrTimeout(long time, TimeUnit unit, Predicate<? super T> predicate) {
		Duration interval = rootWebDriver().configuration().getDefaultInterval();
		Wait<T> wait = new FluentWait<T>(null).
				withTimeout(time, unit).
				pollingEvery(interval.getTime(), interval.getUnit());
		
		Function<? super T, Boolean> function = Functions.forPredicate(predicate);
		
		try {
			wait.until(function);
		}
		catch(TimeoutException e) {
			// ignore
		}
		
		return (T) this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T frame(String selector) {
		return WebElementsFactoryHelper.<T>createIFrameWebElements(factory, (BaseWebElementsImpl<T>) jqueryThis().find(selector).filter("iframe, frame"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public T frame() {
		return WebElementsFactoryHelper.<T>createIFrameWebElements(factory, (BaseWebElementsImpl<T>) jqueryThis().find("iframe, frame").andSelf().filter("iframe, frame"));
	}

	@Override
	public T window(String nameOrHandle) {
		return WebElementsFactoryHelper.<T>createWindowWebElements(factory, this, nameOrHandle);
	}

	@Override
	public T window() {
		return WebElementsFactoryHelper.<T>createWindowWebElements(factory, this);
	}

	@Override
	public Alert alert() {
		Iterable<WebElementsDriver<T>> webDrivers = webDrivers();
		if (Iterables.size(webDrivers) == 1) {
			WebElementsDriver<T> webElementsDriver = Iterables.get(webDrivers, 0);
			return webElementsDriver.switchTo().alert();
		}
		throw new IllegalStateException("Expected only one WebElementsDriver");
	}
	
	@SuppressWarnings("unchecked")
	protected <JQT extends JQueryWebElements<JQT>> JQT jqueryThis() {
		return (JQT) this;
	}
}
