/*
 * Copyright (C) 2013 VILT Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vilt.minium.impl;

import static java.lang.String.format;

import javax.annotation.Nullable;

import org.openqa.selenium.WebElement;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.FluentIterable;
import com.vilt.minium.WebElements;
import com.vilt.minium.WebElementsDriver;

public class ExpressionWebElementsImpl<T extends WebElements> extends BaseWebElementsImpl<T> {

	private BaseWebElementsImpl<T> parent;
	private String expression;
	private Object[] args;
	
	@SuppressWarnings("unchecked")
	public void init(WebElementsFactory factory, WebElements parent, String expression, Object ... args) {
		super.init(factory);
		this.parent = (BaseWebElementsImpl<T>) parent;
		this.expression = expression;
		this.args = args;
	}

	protected Iterable<WebElement> computeElements(final WebElementsDriver<T> wd) {
		return factory.getInvoker().invoke(wd, false, format("return %s;", expression));
	}
	
	@Override
	protected Iterable<WebElementsDriver<T>> candidateWebDrivers() {
		return parent.candidateWebDrivers();
	}
	
	@Override
	public Iterable<WebElementsDriver<T>> webDrivers() {
		return FluentIterable.from(this)
		.transform(new Function<WebElement, WebElementsDriver<T>>() {

			@Override
			@Nullable
			@SuppressWarnings("unchecked")
			public WebElementsDriver<T> apply(@Nullable WebElement input) {
				return (WebElementsDriver<T>) ((DelegateWebElement) input).getWrappedDriver();
			}
		})
		.toSet();
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
	
	@Override
	protected T documentRootWebElements() {
		return parent.documentRootWebElements();
	}
	
	@Override
	public T root(T filter, boolean freeze) {
		return parent.root(filter, freeze);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (obj instanceof ExpressionWebElementsImpl) {
			ExpressionWebElementsImpl<T> elem = (ExpressionWebElementsImpl<T>) obj;
			return 
				Objects.equal(elem.getExpression(), this.getExpression()) &&
				Objects.equal(elem.documentRootWebElements(), this.documentRootWebElements());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(documentRootWebElements(), getExpression());
	}
}
