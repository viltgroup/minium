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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.vilt.minium.Async;
import com.vilt.minium.Configuration;
import com.vilt.minium.Configuration.WaitingPreset;
import com.vilt.minium.CoreWebElements;
import com.vilt.minium.Duration;
import com.vilt.minium.FreezableWebElements;
import com.vilt.minium.TargetLocatorWebElements;
import com.vilt.minium.TimeoutException;
import com.vilt.minium.WebElements;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.WebElementsDriverProvider;
import com.vilt.minium.WebElementsException;

public abstract class BaseWebElementsImpl<T extends CoreWebElements<T>> implements
WebElements, TargetLocatorWebElements<T>, WaitWebElements<T>, FreezableWebElements<T>, WebElementsDriverProvider<T> {

    final Logger logger = LoggerFactory.getLogger(WebElements.class);

    private static class WebElementsWait<T> extends FluentWait<T> {

        public WebElementsWait(T input, Duration timeout, Duration interval) {
            super(input);
            withTimeout(timeout.getTime(), timeout.getUnit());
            pollingEvery(interval.getTime(), interval.getUnit());
        }

        @Override
        protected RuntimeException timeoutException(String message, Throwable lasteException) {
            return new TimeoutException(message, lasteException);
        }
    }

    private final Function<Object, String> argToStringFunction = new Function<Object, String>() {
        @Override
        public String apply(Object input) {
            if (input == null) return "null";
            if (input instanceof String) return format("'%s'", StringEscapeUtils.escapeEcmaScript((String) input));
            if (input instanceof Boolean) return input.toString();
            if (input instanceof Number) return input.toString();
            if (input instanceof StringJavascriptFunction) return input.toString();
            if (input instanceof Date) return format("new Date(%d)", ((Date) input).getTime());
            if (input instanceof BaseWebElementsImpl) return getJQueryWebElementsExpression(input);

            return format("'%s'", StringEscapeUtils.escapeEcmaScript(input.toString()));
        }

        @SuppressWarnings("unchecked")
        private String getJQueryWebElementsExpression(Object input) {
            BaseWebElementsImpl<T> elem = (BaseWebElementsImpl<T>) input;

            if (!elem.documentRootWebElements().equals(BaseWebElementsImpl.this.documentRootWebElements())) {
                throw new IllegalArgumentException("WebElements does not belong to the same window / iframe");
            }

            return elem.getExpression();
        }
    };

    protected WebElementsFactory factory;
    // this as T
    protected T myself;

    protected abstract String getExpression();

    protected abstract Iterable<WebElement> computeElements(final WebElementsDriver<T> wd);

    protected abstract Iterable<WebElementsDriver<T>> candidateWebDrivers();

    protected abstract T documentRootWebElements();

    @SuppressWarnings("unchecked")
    public void init(WebElementsFactory factory) {
        this.myself = (T) this;
        this.factory = factory;
    }

    public Object invoke(Method method, Object ... args) {
        if (method.isVarArgs()) {
            args = expandVarArgs(args);
        }
        String expression = computeExpression(this, isAsyncMethod(method), method.getName(), args);

        if (method.getReturnType() != Object.class && method.getReturnType().isAssignableFrom(this.getClass())) {
            T webElements = WebElementsFactoryHelper.createExpressionWebElements(factory, myself, expression);
            return webElements;
        } else {
            Object result = null;

            boolean async = isAsyncMethod(method);

            Iterable<WebElementsDriver<T>> webDrivers = candidateWebDrivers();

            if (method.getReturnType() == Void.TYPE) {
                for (WebElementsDriver<T> wd : webDrivers) {
                    factory.getInvoker().invokeExpression(wd, async, expression);
                }
            } else {
                if (Iterables.size(webDrivers) == 0) {
                    throw new WebElementsException("The expression has no frame or window to be evaluated to");
                } else if (Iterables.size(webDrivers) == 1) {
                    WebElementsDriver<T> wd = Iterables.get(webDrivers, 0);
                    result = factory.getInvoker().invokeExpression(wd, async, expression);
                } else {
                    String sizeExpression = computeExpression(this, false, "size");
                    WebElementsDriver<T> webDriverWithResults = null;

                    for (WebElementsDriver<T> wd : webDrivers) {
                        long size = (Long) factory.getInvoker().invokeExpression(wd, async, sizeExpression);
                        if (size > 0) {
                            if (webDriverWithResults == null) {
                                webDriverWithResults = wd;
                            } else {
                                throw new WebElementsException("Several frames or windows match the same expression, so value cannot be computed");
                            }
                        }
                    }

                    if (webDriverWithResults != null) {
                        result = factory.getInvoker().invokeExpression(webDriverWithResults, async, expression);
                    }
                }
            }

            if (logger.isDebugEnabled()) {
                String val;
                if (method.getReturnType() == Void.TYPE) {
                    val = "void";
                } else {
                    val = StringUtils.abbreviate(argToStringFunction.apply(result), 40);
                    if (val.startsWith("'") && !val.endsWith("'")) val += "(...)'";
                }
                logger.debug("[Value: {}] {}", argToStringFunction.apply(result), expression);
            }

            // let's handle numbers when return type is int
            if (method.getReturnType() == Integer.TYPE) {
                return result == null ? 0 : ((Number) result).intValue();
            } else {
                return result;
            }
        }
    }

    private Object[] expandVarArgs(Object[] args) {
        if (args == null || args.length == 0) return args;
        Object[] lastArg = (Object[]) args[args.length - 1];
        int size = lastArg == null ? 0 : lastArg.length;
        Object[] expandedArgs = new Object[args.length + size - 1];
        System.arraycopy(args, 0, expandedArgs, 0, args.length - 1);
        if (size > 0) {
            System.arraycopy(lastArg, 0, expandedArgs, args.length - 1, lastArg.length);
        }
        return expandedArgs;
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
             from(Arrays.asList(args))
                .transform(argToStringFunction)
                .toList();

        if (async) {
            jsArgs = Lists.newArrayList(jsArgs);
            jsArgs.add("callback");
        }

        if (parent instanceof DocumentRootWebElementsImpl<?> && "find".equals(fnName)) {
            return format("$(%s)", StringUtils.join(jsArgs, ", "));
        } else {
            return format("%s.%s(%s)", parent.getExpression(), fnName, StringUtils.join(jsArgs, ", "));
        }
    }

    @Override
    public final Iterator<WebElement> iterator() {
        Iterable<WebElement> elements = computeElements();
        if (logger.isDebugEnabled()) {
            logger.debug("[WebElements size: {}] {}", Iterables.size(elements), this);
        }

        return elements.iterator();
    }

    @Override
    public WebElement get(int index) {
        return Iterables.get(this, index);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <WE extends WebElements> WE cast(Class<WE> clazz) {
        if (!clazz.isAssignableFrom(this.getClass())) {
            throw new ClassCastException(
                    format("WebElements does not implement %s. Ensure that the class is passed as an extension interface in DefaultWebElementsDriver constructor", clazz.getName()));
        }
        return (WE) this;
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
        } else if (Iterables.isEmpty(webDrivers)) {
            throw new IllegalStateException("This web elements must evaluate to one web driver");
        }

        return Iterables.get(webDrivers, 0);
    }

    @Override
    public T wait(long time, TimeUnit unit, Predicate<? super T> predicate) {
        return this.wait(new Duration(time, unit), predicate);
    }

    @Override
    public T wait(Duration timeout, Predicate<? super T> predicate) {
        return wait(timeout, null, predicate);
    }

    @Override
    public T wait(String preset, Predicate<? super T> predicate) {
        WaitingPreset waitingPreset = configuration().waitingPreset(preset);
        return wait(waitingPreset.timeout(), waitingPreset.interval(), predicate);
    }

    @SuppressWarnings("unchecked")
    protected T wait(Duration timeout, Duration interval, Predicate<? super T> predicate) {
        if (timeout == null) {
            timeout = rootWebDriver().configure().defaultTimeout();
        }
        if (interval == null) {
            interval = rootWebDriver().configure().defaultInterval();
        }

        Wait<T> wait = getWait(timeout, interval);

        Function<? super T, Boolean> function = Functions.forPredicate(predicate);
        wait.until(function);

        return (T) this;
    }

    @Override
    public T waitOrTimeout(long time, TimeUnit unit, Predicate<? super T> predicate) {
        return waitOrTimeout(new Duration(time, unit), predicate);
    }

    @Override
    public T waitOrTimeout(Duration timeout, Predicate<? super T> predicate) {
        return waitOrTimeout(timeout, null, predicate);
    }

    @Override
    public T waitOrTimeout(String preset, Predicate<? super T> predicate) {
        WaitingPreset waitingPreset = configuration().waitingPreset(preset);
        return waitOrTimeout(waitingPreset.timeout(), waitingPreset.interval(), predicate);
    }

    @SuppressWarnings("unchecked")
    protected T waitOrTimeout(Duration timeout, Duration interval, Predicate<? super T> predicate) {
        if (timeout == null) {
            timeout = rootWebDriver().configure().defaultTimeout();
        }
        if (interval == null) {
            interval = rootWebDriver().configure().defaultInterval();
        }

        Wait<T> wait = getWait(timeout, interval);

        Function<? super T, Boolean> function = Functions.forPredicate(predicate);

        try {
            wait.until(function);
        } catch (TimeoutException e) {
            // ignore
        }

        return (T) this;
    }

    @Deprecated
    @Override
    public T frame() {
        return frames();
    }

    @Override
    public T frames() {
        return frames(null, false);
    }

    @Deprecated
    @Override
    public T window() {
        return windows();
    }

    @Override
    public T windows() {
        return windows(null, false);
    }

    @Override
    public T root() {
        return root(false);
    }

    protected T root(boolean freeze) {
        return root(myself, freeze);
    }

    @Override
    public Alert alert() {
        Duration timeout = rootWebDriver().configure().defaultTimeout();
        Duration interval = rootWebDriver().configure().defaultInterval();

        FluentWait<T> wait = getWait(timeout, interval);

        return wait.ignoring(NoAlertPresentException.class).until(new Function<T, Alert>() {

            @Override
            @Nullable
            public Alert apply(@Nullable T input) {
                return nativeWebDriver().switchTo().alert();
            }
        });
    }

    @Override
    public void close() {
        Iterable<WebElementsDriver<T>> webDrivers = webDrivers();
        for (WebElementsDriver<T> driver : webDrivers) {
            driver.close();
        }
    }

    protected T windows(T filter, boolean freeze) {
        return WebElementsFactoryHelper.createWindowWebElements(factory, myself, filter, freeze);
    }

    protected T frames(T filter, boolean freeze) {
        T parent = myself.find("iframe, frame").addBack().filter("iframe, frame");
        return WebElementsFactoryHelper.createFrameWebElements(factory, parent, filter, freeze);
    }

    protected abstract T root(T filter, boolean freeze);

    protected WebElementsWait<T> getWait(Duration timeout, Duration interval) {
        return new WebElementsWait<T>(myself, timeout, interval);
    }

    @Override
    public WebDriver nativeWebDriver() {
        return rootWebDriver().getWrappedWebDriver();
    }

    @Override
    public Configuration configuration() {
        return rootWebDriver().configure();
    }

    @Override
    public String toString() {
        return getExpression();
    }
}
