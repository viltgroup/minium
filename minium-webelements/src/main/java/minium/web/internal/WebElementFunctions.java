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

import minium.web.internal.drivers.DocumentWebElement;
import minium.web.DocumentWebDriver;

import org.openqa.selenium.WebElement;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

public class WebElementFunctions {

    private static class DocumentWebElementUnwrapper implements Function<WebElement, WebElement> {
        @Override
        public WebElement apply(WebElement webElement) {
            return webElement instanceof DocumentWebElement ?
                    ((DocumentWebElement) webElement).getWrappedWebElement() :
                    webElement;
        }
    };

    private static class DocumentWebElementWrapper implements Function<WebElement, DocumentWebElement> {

        private DocumentWebDriver documentDriver;

        public DocumentWebElementWrapper(DocumentWebDriver documentDriver) {
            this.documentDriver = documentDriver;
        }

        @Override
        public DocumentWebElement apply(WebElement webElement) {
            if (webElement instanceof DocumentWebElement) {
                return (DocumentWebElement) webElement;
            }
            return new DocumentWebElement(webElement, documentDriver);
        }
    };

    private static class DocumentWebElementDocumentDriverUnwrapper implements Function<WebElement, DocumentWebDriver> {

        @Override
        public DocumentWebDriver apply(WebElement webElement) {
            Preconditions.checkArgument(webElement instanceof DocumentWebElement);
            return ((DocumentWebElement) webElement).getWrappedDriver();
        }
    };

    public static Function<WebElement, WebElement> unwrap() {
        return new DocumentWebElementUnwrapper();
    }

    public static Function<WebElement, DocumentWebElement> wrap(DocumentWebDriver documentDriver) {
        return new DocumentWebElementWrapper(documentDriver);
    }

    public static Function<WebElement, DocumentWebDriver> unwrapDocumentDriver() {
        return new DocumentWebElementDocumentDriverUnwrapper();
    }
}
