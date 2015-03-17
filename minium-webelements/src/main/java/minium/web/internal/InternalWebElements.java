/*
 * Copyright (C) 2015 The Minium Authors
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
package minium.web.internal;

import static com.google.common.collect.FluentIterable.from;

import java.util.Iterator;

import minium.FreezableElements;
import minium.internal.BaseElements;
import minium.internal.HasElementsFactory;
import minium.web.DocumentRoots;
import minium.web.DocumentWebDriver;
import minium.web.WebElements;
import minium.web.internal.drivers.DocumentWebElement;
import minium.web.internal.drivers.JavascriptInvoker;
import minium.web.internal.expression.Expression;
import minium.web.internal.expression.VariableGenerator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.reflect.TypeToken;

public interface InternalWebElements extends WebElements {

    public abstract Iterable<DocumentWebDriver> candidateDocumentDrivers();

    public abstract DocumentWebDriver documentDriver();

    public abstract Iterable<DocumentWebDriver> documentDrivers();

    public abstract DocumentRoots documentRoots();

    public abstract boolean isDocumentRoots();

    public abstract Iterable<WebElement> computeNativeElements(DocumentWebDriver wd);

    public abstract Iterable<DocumentWebElement> wrappedNativeElements();

    public abstract static class Impl<T extends WebElements> extends BaseElements<T> implements InternalWebElements, FreezableElements<T> {

        private static final Logger LOGGER = LoggerFactory.getLogger(InternalWebElements.Impl.class);

        private final class DocumentWebDriverNativeElementsFetcher implements Function<DocumentWebDriver, Iterable<DocumentWebElement>> {
            @Override
            public Iterable<DocumentWebElement> apply(DocumentWebDriver wd) {
                return from(computeNativeElements(wd)).transform(WebElementFunctions.wrap(wd));
            }
        }

        @Override
        public DocumentWebDriver documentDriver() {
            Iterator<DocumentWebDriver> documentDriversIterator = documentDrivers().iterator();
            DocumentWebDriver documentDriver = Iterators.getNext(documentDriversIterator, null);

            // if iterator has next element, then it contains more than one document driver
            Preconditions.checkState(!documentDriversIterator.hasNext(), "found multiple document "
                    + "drivers for this web element (call documentDrivers insteand)");

            return documentDriver;
        }

        @Override
        public Iterable<DocumentWebElement> wrappedNativeElements() {
            Iterable<DocumentWebElement> nativeElems = from(candidateDocumentDrivers()).transformAndConcat(new DocumentWebDriverNativeElementsFetcher()).toSet();
            if (LOGGER.isTraceEnabled()) {
                LOGGER.trace("{} elements evaluated for {}", Iterables.size(nativeElems), myself());
            }
            return nativeElems;
        }

        @Override
        public Iterable<WebElement> computeNativeElements(DocumentWebDriver wd) {
            Expression expression = this.as(ExpressionWebElements.class).getExpression();
            VariableGenerator varGenerator = new VariableGenerator.Impl();

            String javascript = expression.getJavascript(varGenerator);
            Object[] args = expression.getArgs();
            if (args == null) args = new Object[0];

            Preconditions.checkState(varGenerator.usedVariables() == args.length);

            return javascriptInvoker().invokeExpression(wd, javascript, args);
        }

        protected WebDriver nativeWebDriver() {
            return this.as(HasNativeWebDriver.class).nativeWebDriver();
        }

        protected JavascriptInvoker javascriptInvoker() {
            return this.is(HasJavascriptInvoker.class) ?
                    this.as(HasJavascriptInvoker.class).javascriptInvoker() :
                    null;
        }

        protected WebElementsFactory<T> factory() {
            TypeToken<WebElementsFactory<T>> factoryTypeToken = typeTokenFor(WebElementsFactory.class);
            return this.as(HasElementsFactory.class).factory().as(factoryTypeToken);
        }
    }
}