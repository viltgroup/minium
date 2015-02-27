package minium.web.internal;

import static com.google.common.collect.FluentIterable.from;
import static java.lang.String.format;
import minium.internal.BaseElements;
import minium.web.BasicWebElements;
import minium.web.DocumentWebDriver;
import minium.web.TargetLocatorWebElements;
import minium.web.WebElements;
import minium.web.internal.drivers.DocumentWebElement;
import minium.web.internal.drivers.FrameWebDriver;
import minium.web.internal.drivers.WindowWebDriver;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;

public class DefaultTargetLocatorWebElements<T extends BasicWebElements<T>> extends BaseElements<T> implements TargetLocatorWebElements<T> {

    protected static class FrameRoots<T extends WebElements> extends BaseDocumentRoots<T> {

        private final class FrameDocumentDriverCreator implements Function<DocumentWebElement, DocumentWebDriver> {
            @Override
            public DocumentWebDriver apply(DocumentWebElement webElement) {
                return new FrameWebDriver(webElement);
            }
        }

        private final T frameElems;

        public FrameRoots(T frameElems) {
            this.frameElems = frameElems;
        }

        @Override
        public Iterable<DocumentWebDriver> candidateDocumentDrivers() {
            return from(frameElems.as(InternalWebElements.class).wrappedNativeElements()).transform(new FrameDocumentDriverCreator());
        }

        @Override
        public String toString() {
            String parentStr = parent().toString();
            return Strings.isNullOrEmpty(parentStr) ? "[frames]" : format("%s -> [frames]", parentStr);
        }

    }

    public static class WindowRoots<T extends WebElements> extends BaseDocumentRoots<T> {

        protected final class HandleToWindowWebDriver implements Function<String, DocumentWebDriver> {
            @Override
            public DocumentWebDriver apply(String windowHandle) {
                return new WindowWebDriver(nativeWebDriver(), windowHandle);
            }
        }

        @Override
        public Iterable<DocumentWebDriver> candidateDocumentDrivers() {
            Iterable<String> windowHandles = candidateHandles();
            return from(windowHandles).transform(new HandleToWindowWebDriver());
        }

        protected Iterable<String> candidateHandles() {
            return from(nativeWebDriver().getWindowHandles());
        }

        @Override
        public String toString() {
            String parentStr = parent().toString();
            return Strings.isNullOrEmpty(parentStr) ? "[windows]" : format("%s -> [windows]", parentStr);
        }

    }

    private static final String FRAMES_CSS_SELECTOR = "iframe, frame";

    @SuppressWarnings("serial")
    private TypeToken<BasicWebElements<T>> basicWebElementsTypeToken = new TypeToken<BasicWebElements<T>>(getClass()) { };

    @Override
    public T frames() {
        T frameElems = this
                .as(basicWebElementsTypeToken)
                .find(FRAMES_CSS_SELECTOR)
                .add(myself())
                .filter(FRAMES_CSS_SELECTOR);
        return internalFactory().createMixin(myself(), new FrameRoots<T>(frameElems));
    }

    @Override
    public T windows() {
        return internalFactory().createMixin(myself(), new WindowRoots<T>());
    }

    @Override
    public T documentRoots() {
        return this.as(InternalWebElements.class).documentRoots().as(typeVariableToken());
    }
}