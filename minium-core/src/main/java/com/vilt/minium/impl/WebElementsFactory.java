/*
 * Copyright (C) 2013 The Minium Authors
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

import static com.google.common.collect.FluentIterable.from;
import static java.lang.String.format;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.vilt.minium.Configuration;
import com.vilt.minium.ExceptionHandler;
import com.vilt.minium.JQueryResources;
import com.vilt.minium.WebElements;
import com.vilt.minium.WebElementsDriverProvider;
import com.vilt.minium.WebElementsException;

public class WebElementsFactory implements MethodHandler {

	private static final Logger logger = LoggerFactory.getLogger(WebElementsFactory.class);
	
	private Map<Class<?>, Class<?>> webElementsProxyClasses = Maps.newHashMap();
	private JQueryInvoker invoker;
	private Class<?> elementsInterface;
	private Set<Class<?>> moreInterfaces;

	public WebElementsFactory(Class<? extends WebElements> elementsInterface, Class<?> ... moreInterfaces) {
		this.elementsInterface = elementsInterface;
		this.moreInterfaces = Sets.newHashSet(moreInterfaces);
		// we always add the WebElementsDriverProvider class
		this.moreInterfaces.add(WebElementsDriverProvider.class);
		this.moreInterfaces.remove(elementsInterface);
		initInvoker();
	}

	public <T extends WebElements> T create(Class<T> superClass) {
		try {
			T webElements = this.<T>getProxyClassFor(superClass).newInstance();
			((Proxy) webElements).setHandler(this);
			return webElements;
		} catch (InstantiationException e) {
			throw new WebElementsException(e);
		} catch (IllegalAccessException e) {
			throw new WebElementsException(e);
		}
	}
	
	public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
		BaseWebElementsImpl<?> parentWebElements = (BaseWebElementsImpl<?>) self;
		try {
			return doInvoke(self, thisMethod, proceed, args, parentWebElements);
		} catch (Exception e) {
			handleException(parentWebElements, e);
			// if code reaches here, that means exception was handled successfully, so let's retry it once again
			return doInvoke(self, thisMethod, proceed, args, parentWebElements);
		}
	}

	private Object doInvoke(Object self, Method thisMethod, Method proceed, Object[] args, BaseWebElementsImpl<?> parentWebElements) throws Throwable {
		if (Modifier.isAbstract(thisMethod.getModifiers())) {
			return parentWebElements.invoke(thisMethod, args);
		} else {
			try {
				return proceed.invoke(self, args);
			} catch (InvocationTargetException e) {
				// we need to throw the target exception
				throw e.getTargetException();
			} catch (Throwable e) {
				throw e;
			}
		}
	}

	public JQueryInvoker getInvoker() {
		return invoker;
	}
	
	@SuppressWarnings("unchecked")
	private void initInvoker() {
		
		// we need to ensure that insertion order is maintained, as first resources 
		// must have priority (normally, dependent resources go after those dependencies, e.g
		// position.js after jquery.js)
		Set<String> jsResources = Sets.newLinkedHashSet();
		Set<String> cssResources = Sets.newLinkedHashSet();
		
		// jquery is required, nothing will work without it...
		jsResources.add("minium/js/jquery.min.js");
		
		Queue<Class<?>> toCheck = Lists.newLinkedList();
		toCheck.add(elementsInterface);
		
		if (moreInterfaces != null) {
			toCheck.addAll(moreInterfaces);
		}
		
		while (!toCheck.isEmpty()) {
			Class<?> interfaze = toCheck.poll();
			JQueryResources annotation = interfaze.getAnnotation(JQueryResources.class);
			
			// we only expand sub-interfaces if no JQuery annotation was found 
			if (annotation == null) {
				Class<?>[] subInterfaces = interfaze.getInterfaces();
				
				toCheck.addAll(
						from(Arrays.asList(subInterfaces)).
						filter(new Predicate<Class<?>>() {
							public boolean apply(Class<?> input) {
								return WebElements.class.isAssignableFrom(input);
							}
						}).
						transform(new Function<Class<?>, Class<WebElements>>() {
							public Class<WebElements> apply(Class<?> input) {
								return (Class<WebElements>) input;
							}
						}).
						toList());
			}
			else {
				// collect css and javascript resources
				if (annotation.value() != null) jsResources.addAll(Arrays.asList(annotation.value()));
				if (annotation.styles() != null) cssResources.addAll(Arrays.asList(annotation.styles()));
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug(format("Found the following resources for %s:", elementsInterface.getName()));
			logger.debug(format(" Javascript: %s", StringUtils.join(jsResources, "; ")));
			logger.debug(format(" CSS styles: %s", StringUtils.join(cssResources, "; ")));
		}
		
		invoker = new JQueryInvoker(jsResources, cssResources);
	}

	@SuppressWarnings("unchecked")
	private <T extends WebElements> Class<T> getProxyClassFor(final Class<?> superClass) {
		Class<?> proxyClass = webElementsProxyClasses.get(superClass);
		if (proxyClass == null) {
			ProxyFactory factory = new ProxyFactory();
			
			Class<?>[] interfaces;
			if (moreInterfaces == null) {
				interfaces = new Class<?>[] { elementsInterface };
			}
			else {
				interfaces = new Class<?>[moreInterfaces.size() + 1];
				interfaces[0] = elementsInterface;
				System.arraycopy(moreInterfaces.toArray(), 0, interfaces, 1, moreInterfaces.size());
			}
			
			factory.setInterfaces(interfaces);
			factory.setFilter(new MethodFilter() {
				public boolean isHandled(Method m) {
					return true;
				}
			});
			factory.setSuperclass(superClass);
			proxyClass = factory.createClass();
			
			webElementsProxyClasses.put(superClass, proxyClass);
		}
		return (Class<T>) proxyClass;
	}
	
	private void handleException(BaseWebElementsImpl<?> parentWebElements, Exception e) throws Exception {
		Configuration configuration = parentWebElements.configuration();
		for (ExceptionHandler exceptionHandler : configuration.exceptionHandlers()) {
			boolean handled = exceptionHandler.handle(parentWebElements, e);
			if (handled) return;
		}
		throw e;
	}
}
