package minium.script.dynjs;

import minium.web.CoreWebElements;
import minium.web.WebElements;
import minium.web.WebLocator;

public interface CoreDynJsWebElements<T extends WebElements> extends
    CoreWebElements<T>,
    JsFunctionWebElements<T> {

    public interface DefaultDynJsWebElements extends CoreDynJsWebElements<DefaultDynJsWebElements> {
        public static final WebLocator<DefaultDynJsWebElements> by = new WebLocator<>(DefaultDynJsWebElements.class);
    }
}
