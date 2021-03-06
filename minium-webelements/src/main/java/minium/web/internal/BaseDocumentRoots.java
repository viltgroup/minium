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

import java.util.Collections;
import java.util.Iterator;

import minium.web.CannotFreezeException;
import minium.web.DocumentRoots;
import minium.web.DocumentWebDriver;
import minium.web.WebElements;
import minium.web.internal.expression.Expression;
import minium.web.internal.expression.RootExpression;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.common.collect.Iterators;

public abstract class BaseDocumentRoots<T extends WebElements> extends InternalWebElements.Impl<T> implements DocumentRoots, ExpressionWebElements {

    static class FrozenRootsImpl<T extends WebElements> extends BaseDocumentRoots<T> {

        private DocumentWebDriver frozenDriver;

        @Override
        public Iterable<DocumentWebDriver> candidateDocumentDrivers() {
            if (frozenDriver == null) {
                Iterable<DocumentWebDriver> webDrivers = parent().as(InternalWebElements.class).candidateDocumentDrivers();

                Iterator<DocumentWebDriver> iterator = webDrivers.iterator();
                DocumentWebDriver firstWebDriver = Iterators.getNext(iterator, null);

                if (iterator.hasNext()) {
                    throw new CannotFreezeException("Cannot freeze root because more than one root matched");
                }

                frozenDriver = firstWebDriver;
            }

            if (frozenDriver == null) {
                return Collections.emptyList();
            } else {
                return Collections.singleton(frozenDriver);
            }
        }

        @Override
        public String toString() {
            return parent().toString() + ".freeze()";
        }
    }

    @Override
    public WebElements documentRoots() {
        return myself();
    }

    @Override
    public WebElements candidateDocumentRoots() {
        return myself();
    }

    @Override
    public boolean isDocumentRoots() {
        return true;
    }

    @Override
    public Iterable<DocumentWebDriver> documentDrivers() {
        return candidateDocumentDrivers();
    }

    @Override
    public Expression getExpression() {
        return new RootExpression();
    }

    @Override
    public Iterable<WebElement> computeNativeElements(DocumentWebDriver wd) {
        if (this.is(HasJavascriptInvoker.class)) {
            return super.computeNativeElements(wd);
        }
        return Collections.singleton(wd.findElement(By.xpath("/*")));
    }

    @Override
    public T freeze() {
        return internalFactory().createMixin(myself(), new FrozenRootsImpl<T>());
    }
}
