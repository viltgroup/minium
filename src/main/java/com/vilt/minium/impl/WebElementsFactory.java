package com.vilt.minium.impl;

import static com.google.common.collect.FluentIterable.from;
import static java.lang.String.format;

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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.vilt.minium.MiniumException;
import com.vilt.minium.WebElements;
import com.vilt.minium.jquery.JQueryResources;

public class WebElementsFactory implements MethodHandler {

	private static final Logger LOG = Logger.getLogger(WebElementsFactory.class);
	
	private Map<Class<?>, Class<?>> webElementsProxyClasses = Maps.newHashMap();
	private JQueryInvoker invoker;
	private Class<?> elementsInterface;
	private Set<Class<?>> moreInterfaces;

	public WebElementsFactory(Class<? extends WebElements> elementsInterface, Class<?> ... moreInterfaces) {
		this.elementsInterface = elementsInterface;
		this.moreInterfaces = Sets.newHashSet(moreInterfaces);
		this.moreInterfaces.remove(elementsInterface);
		initInvoker();
	}

	public <T extends WebElements> T create(Class<T> superClass) {
		try {
			T webElements = this.<T>getProxyClassFor(superClass).newInstance();
			((Proxy) webElements).setHandler(this);
			return webElements;
		} catch (InstantiationException e) {
			throw new MiniumException(e);
		} catch (IllegalAccessException e) {
			throw new MiniumException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
		BaseWebElementsImpl<? extends WebElements> parentWebElements = (BaseWebElementsImpl<? extends WebElements>) self;
		return parentWebElements.invoke(thisMethod, args);
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
		jsResources.add("minium/js/jquery.js");
		
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
						toImmutableList());
			}
			else {
				// collect css and javascript resources
				if (annotation.value() != null) jsResources.addAll(Arrays.asList(annotation.value()));
				if (annotation.styles() != null) cssResources.addAll(Arrays.asList(annotation.styles()));
			}
		}
		
		if (LOG.isDebugEnabled()) {
			LOG.debug(format("Found the following resources for %s:", elementsInterface.getName()));
			LOG.debug(format(" Javascript: %s", StringUtils.join(jsResources, "; ")));
			LOG.debug(format(" CSS styles: %s", StringUtils.join(cssResources, "; ")));
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
					return Modifier.isAbstract(m.getModifiers());
				}
			});
			factory.setSuperclass(superClass);
			proxyClass = factory.createClass();
			
			webElementsProxyClasses.put(superClass, proxyClass);
		}
		return (Class<T>) proxyClass;
	}
}
