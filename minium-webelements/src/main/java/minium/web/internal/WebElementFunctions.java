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
