package minium.web;

import minium.actions.Interactable;

public interface CoreWebElements<T extends WebElements> extends
        BasicWebElements<T>,
        ExtensionsWebElements<T>,
        EvalWebElements<T>,
        TargetLocatorWebElements<T>,
        PositionWebElements<T>,
        Interactable {

    public interface DefaultWebElements extends CoreWebElements<DefaultWebElements> {
        public static final WebFinder<DefaultWebElements> by = new WebFinder<>(DefaultWebElements.class);
    }

}
