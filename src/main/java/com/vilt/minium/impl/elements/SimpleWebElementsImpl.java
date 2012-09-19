package com.vilt.minium.impl.elements;

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
import com.vilt.minium.impl.DelegateWebElement;
import com.vilt.minium.impl.JQueryWebElementsImpl;
import com.vilt.minium.jquery.JQueryWebElements;

public abstract class SimpleWebElementsImpl<T extends JQueryWebElements<T>> extends BaseWebElementsImpl<T> {
	
	private final class ArgToStringFunction implements Function<Object, String> {
		public String apply(Object input) {
			if (input == null) return "null";
			if (input instanceof String) return format("'%s'", StringEscapeUtils.escapeJavaScript((String) input));
			if (input instanceof Boolean) return input.toString();
			if (input instanceof Number) return input.toString();
			if (input instanceof Date) return format("new Date(%d)", ((Date) input).getTime());
			if (input instanceof JQueryWebElementsImpl) return getWebElementsExpression(input);
			
			return format("'%s'", StringEscapeUtils.escapeJavaScript(input.toString()));
		}

		private String getWebElementsExpression(Object input) {
			SimpleWebElementsImpl<?> elem = (SimpleWebElementsImpl<?>) input;
			
			if (!Objects.equal(elem.webDriver(), SimpleWebElementsImpl.this.webDriver())) {
				throw new IllegalArgumentException("Web elements don't belong to the same window...");
			}
			
			return elem.getExpression();
		}
	}
	
	private WebElementsDriver<T> driver;
	private String expression;
	
	@Override
	public WebElementsDriver<T> webDriver() {
		return driver;
	}
	
	protected void init(WebElements<T> parentWebElements, String fnName, Object ... args) {
		Collection<String> jsArgs = 
				from(Arrays.asList(args)).
				transform(new ArgToStringFunction()).
				toImmutableList();
		String expression;
		
		if (parentWebElements instanceof RootWebElementsImpl<?>) {
			expression = format("$(%s)", StringUtils.join(jsArgs, ", "));
		}
		else {
			expression = format("%s.%s(%s)", parentWebElements, fnName, StringUtils.join(jsArgs, ", "));			
		}
		
		init(parentWebElements.webDriver(), expression);
	}
	
	protected void init(WebElementsDriver<T> wd, String expression) {
		this.driver = wd;
		this.expression = expression;
	}
	
	protected String getExpression() {
		return expression;
	}
	
	@Override
	public Iterator<WebElement> iterator() {
		List<WebElement> elems = webDriver().invoke(format("return %s;", expression));
		final Iterator<WebElement> iterator = elems.iterator();
		return Iterators.transform(iterator, new Function<WebElement, WebElement>() {

			@Override
			@Nullable
			public WebElement apply(@Nullable WebElement input) {
				return new DelegateWebElement(input, webDriver());
			}
		});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected <R> R invoke(Method method, Object... args) {
		WebElementsDriver<T> wd = this.webDriver();
		SimpleWebElementsImpl<T> webElements = (SimpleWebElementsImpl<T>) wd.webElements();
		webElements.init(this, method.getName(), args);
		
		if (method.getReturnType().isAssignableFrom(webElements.getClass())) {
			return (R) webElements;
		}
		else {
			return (R) wd.invoke(webElements.getExpression(), args);
		}	
	}
	
}
