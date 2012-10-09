package com.vilt.minium.impl.elements;

import static java.lang.String.format;

import org.openqa.selenium.WebElement;

import com.vilt.minium.WebElements;
import com.vilt.minium.driver.WebElementsDriver;
import com.vilt.minium.impl.WebElementsFactory;

public class ExpressionWebElementsImpl<T extends WebElements<T>> extends BaseWebElementsImpl<T> {

	private BaseWebElementsImpl<T> parent;
	private String expression;
	private Object[] args;
	
	public void init(WebElementsFactory factory, T parent, String expression, Object ... args) {
		super.init(factory);
		this.parent = (BaseWebElementsImpl<T>) parent;
		this.expression = expression;
		this.args = args;
	}

	protected Iterable<WebElement> computeElements(final WebElementsDriver<T> wd) {
		return factory.getInvoker().invoke(wd, format("return %s;", expression));
	}
	
	@Override
	protected Iterable<WebElementsDriver<T>> webDrivers() {
		return parent.webDrivers();
	}
	
	@Override
	protected WebElementsDriver<T> rootWebDriver() {
		return parent.rootWebDriver();
	}
	
	@Override
	protected String getExpression() {
		return expression;
	}
	
	public Object[] getArgs() {
		return args;
	}
}
