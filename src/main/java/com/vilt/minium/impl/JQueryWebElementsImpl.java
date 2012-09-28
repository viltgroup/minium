package com.vilt.minium.impl;

import static com.google.common.collect.FluentIterable.from;
import static java.lang.String.format;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebElement;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.Iterators;
import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;

public abstract class JQueryWebElementsImpl<T extends JQueryWebElementsImpl<T>> extends WebElementsImpl<T> {
	
	private final class ArgToStringFunction implements Function<Object, String> {
		public String apply(Object input) {
			if (input == null) return "null";
			if (input instanceof String) return format("'%s'", StringEscapeUtils.escapeJavaScript((String) input));
			if (input instanceof Boolean) return input.toString();
			if (input instanceof Number) return input.toString();
			if (input instanceof Date) return format("new Date(%d)", ((Date) input).getTime());
			if (input instanceof JQueryWebElementsImpl) return getJQueryWebElementsExpression(input);
			
			return format("'%s'", StringEscapeUtils.escapeJavaScript(input.toString()));
		}

		private String getJQueryWebElementsExpression(Object input) {
			JQueryWebElementsImpl<?> elem = (JQueryWebElementsImpl<?>) input;
			
			if (!Objects.equal(elem.webDriver(), JQueryWebElementsImpl.this.webDriver())) {
				throw new IllegalArgumentException("Web elements don't belong to the same window...");
			}
			
			return elem.getExpression();
		}
	}

	private String expression = "$(document)";
	private List<Object> arguments;
	
	public JQueryWebElementsImpl() {
	}

	public Iterator<WebElement> iterator() {
		List<WebElement> elems = invoker.invoke(wd, format("return %s;", expression), arguments);
		final Iterator<WebElement> iterator = elems.iterator();
		return Iterators.transform(iterator, new Function<WebElement, WebElement>() {

			@Override
			@Nullable
			public WebElement apply(@Nullable WebElement input) {
				return new DelegateWebElement(input, wd);
			}
		});
	}
	
	@Override
	public T frame() {
		return this.find("iframe, frame").andSelf().filter("iframe, frame").createFrameElements();
	}
	
	@Override
	public T frame(String selector) {
		return this.find(selector).filter("iframe, frame").createFrameElements();
	}
	
	@SuppressWarnings("unchecked")
	protected T createFrameElements() {
//		ComposedWebElementsImpl<T> composedElems = (ComposedWebElementsImpl<T>) factory.create(wd, ComposedWebElementsImpl.class);
//		composedElems.initComposingWebElements(new Supplier<List<WebElementsImpl<T>>>() {
//
//			@Override
//			public List<WebElementsImpl<T>> get() {
//				ImmutableList<WebElementsImpl<T>> elems = FluentIterable.
//						from(WebElementsImpl.this).
//						transform(new Function<WebElement, WebElementsImpl<T>>() {
//							@Nullable
//							public WebElementsImpl<T> apply(@Nullable WebElement input) {
//								FrameWebElementsDriver<T> fwd = new FrameWebElementsDriver<T>(wd, factory, input);
//								return (WebElementsImpl<T>) factory.create(fwd);
//							}
//						}).
//						toImmutableList();
//				return elems;
//			}
//		});
//		
//		return (T) composedElems;
		RootFrameWebElementsImpl<?> frameElems = (RootFrameWebElementsImpl<?>) factory.create(wd, RootFrameWebElementsImpl.class);
		frameElems.initParentRootWebElements((WebElementsImpl<?>) this);
		
		return (T) frameElems;
	}
	
	@Override
	public String toString() {
		return expression;
	}
	
	protected String getExpression() {
		return expression;
	}
	
	protected void setExpression(String expression) {
		this.expression = expression;
	}

	protected void initExpression(WebElements<T> parentWebElements, String fnName, Object ... args) {
		Collection<String> jsArgs = 
				from(Arrays.asList(args)).
				transform(new ArgToStringFunction()).
				toImmutableList();

		expression = format("%s.%s(%s)", parentWebElements, fnName, StringUtils.join(jsArgs, ", "));
	}
	
	protected void initRootExpression(Object ... args) {
		Collection<String> jsArgs = 
				from(Arrays.asList(args)).
				transform(new ArgToStringFunction()).
				toImmutableList();

		expression = format("$(%s)", StringUtils.join(jsArgs, ", "));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <R> R invoke(Method method, Object... args) {
		WebElementsDriver<T> wd = this.webDriver();
		JQueryWebElementsImpl<T> webElements = (JQueryWebElementsImpl<T>) factory.create(wd);
		webElements.initExpression(this, method.getName(), args);
		
		if (method.getReturnType().isAssignableFrom(webElements.getClass())) {
			return (R) webElements;
		}
		else {
			return (R) invoker.invokeExpression(wd, webElements.getExpression(), args);
		}	
	}
}
