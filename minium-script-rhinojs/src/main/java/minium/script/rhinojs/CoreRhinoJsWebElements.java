package minium.script.rhinojs;

import minium.web.CoreWebElements;
import minium.web.WebElements;
import minium.web.WebFinder;

public interface CoreRhinoJsWebElements<T extends WebElements> extends
    CoreWebElements<T>,
    JsFunctionWebElements<T> {

    public interface DefaultRhinoJsWebElements extends CoreRhinoJsWebElements<DefaultRhinoJsWebElements> {
        public static final WebFinder<DefaultRhinoJsWebElements> by = new WebFinder<>(DefaultRhinoJsWebElements.class);
    }
}
