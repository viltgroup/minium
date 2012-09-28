package com.vilt.minium.impl;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.vilt.minium.TargetLocatorWebElements;
import com.vilt.minium.WaitWebElements;
import com.vilt.minium.WebElements;
import com.vilt.minium.driver.Configuration.Duration;
import com.vilt.minium.driver.impl.WindowWebElementsDriver;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.jquery.JQueryWebElements;

public abstract class WebElementsImpl<T extends WebElementsImpl<T>> implements WebElements<T>, JQueryWebElements<T>, TargetLocatorWebElements<T>, WaitWebElements<T> {

	protected WebElementsDriver<T> wd;
	protected JQueryInvoker invoker;
	protected WebElementsFactory factory;

	public WebElementsDriver<T> webDriver() {
		return wd;
	}
	
	protected void init(WebElementsDriver<T> wd, WebElementsFactory factory, JQueryInvoker invoker) {
		this.wd = wd;
		this.factory = factory;
		this.invoker = invoker;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T window() {
		ComposedWebElementsImpl<T> composedElems = (ComposedWebElementsImpl<T>) factory.create(wd, ComposedWebElementsImpl.class);
		composedElems.initComposingWebElements(new Supplier<List<T>>() {

			@Override
			public List<T> get() {
				String currentHandle = wd.getWindowHandle();
		
				List<T> windowRootElems = Lists.newArrayList();
				
				for (String windowHandle : wd.getWindowHandles()) {
					if (currentHandle.equals(windowHandle)) continue;
					WebElementsDriver<T> window = new WindowWebElementsDriver<T>(wd, factory, windowHandle);
					windowRootElems.add((T) window.webElements());
				}
				
				return windowRootElems;
				
			}
		});
	
		return (T) composedElems;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T window(final String nameOrHandle) {
		ComposedWebElementsImpl<T> composedElems = (ComposedWebElementsImpl<T>) factory.create(wd, ComposedWebElementsImpl.class);
		composedElems.initComposingWebElements(new Supplier<List<T>>() {

			@Override
			public List<T> get() {
				WebElementsDriver<T> window = new WindowWebElementsDriver<T>(wd, factory, nameOrHandle);

				return Collections.singletonList((T) window.webElements());
				
			}
		});
	
		return (T) composedElems;
	}
	
	@Override
	public Alert alert() {
		return wd.switchTo().alert();
	}
	
	@Override
	public T wait(Predicate<? super T> predicate) {
		Duration timeout = wd.configuration().getDefaultTimeout();
		
		long time = timeout.getTime();
		TimeUnit unit = timeout.getUnit();
		
		return wait(time, unit, predicate);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T wait(long time, TimeUnit unit, Predicate<? super T> predicate) {
		Duration interval = wd.configuration().getDefaultInterval();
		Wait<T> wait = new FluentWait<T>((T) this).
				withTimeout(time, unit).
				pollingEvery(interval.getTime(), interval.getUnit());
		
		Function<? super T, Boolean> function = Functions.forPredicate(predicate);
		wait.until(function);
		
		return (T) this;
	}
	
	@Override
	public T waitOrTimeout(Predicate<? super T> predicate) {
		Duration timeout = wd.configuration().getDefaultTimeout();
		
		long time = timeout.getTime();
		TimeUnit unit = timeout.getUnit();
		
		return waitOrTimeout(time, unit, predicate);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T waitOrTimeout(long time, TimeUnit unit, Predicate<? super T> predicate) {
		Duration interval = wd.configuration().getDefaultInterval();
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
	public WebElement get(int index) {
		return Iterables.get(this, index);
	}
	
	protected abstract <R> R invoke(Method thisMethod, Object ... args);
}