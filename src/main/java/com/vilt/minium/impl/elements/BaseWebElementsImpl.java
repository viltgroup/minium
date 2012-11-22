package com.vilt.minium.impl.elements;

import static com.google.common.collect.FluentIterable.from;
import static java.lang.String.format;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.vilt.minium.MiniumException;
import com.vilt.minium.TargetLocatorWebElements;
import com.vilt.minium.WaitWebElements;
import com.vilt.minium.WebElements;
import com.vilt.minium.WebElementsDriverProvider;
import com.vilt.minium.driver.Configuration.Duration;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.impl.DelegateWebElement;
import com.vilt.minium.impl.WebElementsFactory;
import com.vilt.minium.impl.WebElementsFactoryHelper;
import com.vilt.minium.jquery.Async;
import com.vilt.minium.jquery.JQueryWebElements;

public abstract class BaseWebElementsImpl<T extends WebElements> implements WebElements, TargetLocatorWebElements<T>, WaitWebElements<T>, WebElementsDriverProvider<T> {

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

			if (!elem.relativeRootWebElements().equals(BaseWebElementsImpl.this.relativeRootWebElements())) {
				throw new IllegalArgumentException("WebElements does not belong to the same window / iframe");
			}
			
			return elem.getExpression();
		}
	}
	
	protected WebElementsFactory factory;

	protected abstract String getExpression();
	
	protected abstract Iterable<WebElement> computeElements(final WebElementsDriver<T> wd);
		
	protected abstract WebElementsDriver<T> rootWebDriver();
	
	protected abstract Iterable<WebElementsDriver<T>> candidateWebDrivers();
	
	protected abstract T relativeRootWebElements();
	
	public void init(WebElementsFactory factory) {
		this.factory = factory;
	}
	
	@SuppressWarnings("unchecked")
	public Object invoke(Method method, Object ... args) {
		String expression = computeExpression(this, isAsyncMethod(method), method.getName(), args);
		
		if (method.getReturnType().isAssignableFrom(this.getClass())) {
			T webElements = (T) WebElementsFactoryHelper.createExpressionWebElements(factory, (T) this, expression);
			return webElements;
		}
		else {
			Object result = null;
			
			boolean async = isAsyncMethod(method);
			
			Iterable<WebElementsDriver<T>> webDrivers = candidateWebDrivers();		
			
			if (method.getReturnType() == Void.TYPE) {
				for (WebElementsDriver<T> wd : webDrivers) {
					factory.getInvoker().invokeExpression(wd, async, expression);
				}
			}
			else {
				if (Iterables.size(webDrivers) == 1) {
					WebElementsDriver<T> wd = Iterables.get(webDrivers, 0);
					result = factory.getInvoker().invokeExpression(wd, async, expression);
				}
				else {
					String sizeExpression = computeExpression(this, false, "size");
					WebElementsDriver<T> webDriverWithResults = null;
					
					for (WebElementsDriver<T> wd : webDrivers) {
						long size = (Long) factory.getInvoker().invokeExpression(wd, async, sizeExpression);
						if (size > 0) {
							if (webDriverWithResults == null) {
								webDriverWithResults = wd;
							}
							else {
								throw new MiniumException("Several frames or windows match the same expression, so value cannot be computed");								
							}
						}
					}
					
					if (webDriverWithResults != null) {
						result = factory.getInvoker().invokeExpression(webDriverWithResults, async, expression);
					}
				}
			}

			// let's handle numbers when return type is int
			if (method.getReturnType() == Integer.TYPE) {
				return result == null ? 0 : ((Number) result).intValue();
			}
			else {
				return result;
			}
		}
	}
	
	private boolean isAsyncMethod(Method method) {
		return method.getAnnotation(Async.class) != null;
	}

	protected final Iterable<WebElement> computeElements() {
		return Iterables.concat(Iterables.transform(candidateWebDrivers(), new Function<WebElementsDriver<T>, Iterable<WebElement>>() {

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
	
	protected String computeExpression(BaseWebElementsImpl<T> parent, boolean async, String fnName, Object ... args) {
		List<String> jsArgs = 
				from(Arrays.asList(args)).
				transform(new ArgToStringFunction()).
				toImmutableList();
		
		if (async) {
			jsArgs = Lists.newArrayList(jsArgs);
			jsArgs.add("callback");
		}
		
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
	public Iterable<WebElementsDriver<T>> webDrivers() {
		return candidateWebDrivers();
	}
	
	@Override
	public WebElementsDriver<T> webDriver() {
		Iterable<WebElementsDriver<T>> webDrivers = webDrivers();
		if (Iterables.size(webDrivers) > 1) {
			throw new IllegalStateException("This web elements must only evaluate to one web driver");
		}
		else if (Iterables.isEmpty(webDrivers)) {
			throw new IllegalStateException("This web elements must evaluate to one web driver");
		}
		
		return Iterables.get(webDrivers, 0);
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
		Wait<T> wait = getWait(time, unit);
		
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
		Wait<T> wait = getWait(time, unit);
		
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
	public T frame() {
		return WebElementsFactoryHelper.<T>createIFrameWebElements(factory, (BaseWebElementsImpl<T>) jqueryThis().find("iframe, frame").andSelf().filter("iframe, frame"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public T frame(String selector) {
		return WebElementsFactoryHelper.<T>createIFrameWebElements(factory, (BaseWebElementsImpl<T>) jqueryThis().find(selector).filter("iframe, frame"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public T frame(T filter) {
		return WebElementsFactoryHelper.<T>createIFrameWebElements(factory, (BaseWebElementsImpl<T>) ((JQueryWebElements<T>) filter).filter("iframe, frame"));
	}

	@Override
	public T window(String expr) {
		return WebElementsFactoryHelper.<T>createWindowWebElements(factory, this, expr);
	}

	@Override
	public T window(T filter) {
		return WebElementsFactoryHelper.<T>createWindowWebElements(factory, this, filter);
	}
	
	@Override
	public T window() {
		return WebElementsFactoryHelper.<T>createWindowWebElements(factory, this);
	}

	
	@Override
	public Alert alert() {
		Duration timeout = rootWebDriver().configuration().getDefaultTimeout();
		
		long time = timeout.getTime();
		TimeUnit unit = timeout.getUnit();
		
		FluentWait<T> wait = getWait(time, unit);
		
		return wait.ignoring(NoAlertPresentException.class).until(new Function<T, Alert>() {

			@Override
			@Nullable
			public Alert apply(@Nullable T input) {
				return rootWebDriver().getWrappedWebDriver().switchTo().alert();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	protected FluentWait<T> getWait(long time, TimeUnit unit) {
		Duration interval = rootWebDriver().configuration().getDefaultInterval();
		FluentWait<T> wait = new FluentWait<T>((T) this).
				withTimeout(time, unit).
				pollingEvery(interval.getTime(), interval.getUnit());
		return wait;
	}

	@SuppressWarnings("unchecked")
	protected <JQT extends JQueryWebElements<JQT>> JQT jqueryThis() {
		return (JQT) this;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
