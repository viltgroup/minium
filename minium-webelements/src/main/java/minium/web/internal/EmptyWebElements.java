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

import minium.web.DocumentRoots;
import minium.web.DocumentWebDriver;
import minium.web.WebElements;
import minium.web.internal.expression.EmptyWebElementsExpression;
import minium.web.internal.expression.Expression;

import org.openqa.selenium.WebElement;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

public class EmptyWebElements<T extends WebElements> extends InternalWebElements.Impl<T> implements ExpressionWebElements {

    private final DocumentWebDriver rootWebDriver;

    public EmptyWebElements(DocumentWebDriver rootWebDriver) {
        this.rootWebDriver = Preconditions.checkNotNull(rootWebDriver);
    }

    @Override
    public Iterable<DocumentWebDriver> candidateDocumentDrivers() {
        return ImmutableSet.of(rootWebDriver);
    }

    @Override
    public Iterable<DocumentWebDriver> documentDrivers() {
        return candidateDocumentDrivers();
    }

    @Override
    public DocumentRoots documentRoots() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isDocumentRoots() {
        return false;
    }

    @Override
    public Iterable<WebElement> computeNativeElements(DocumentWebDriver wd) {
        return ImmutableSet.<WebElement>of();
    }

    @Override
    public Expression getExpression() {
        return new EmptyWebElementsExpression();
    }

    @Override
    public T freeze() {
        return myself();
    }

    @Override
    public String toString() {
        return "$()";
    }
}
