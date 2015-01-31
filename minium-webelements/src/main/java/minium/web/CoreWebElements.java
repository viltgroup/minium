package minium.web;

import minium.actions.HasConfiguration;
import minium.actions.Interactable;

public interface CoreWebElements<T extends WebElements> extends
        BasicWebElements<T>,
        ConditionalWebElements<T>,
        ExtensionsWebElements<T>,
        EvalWebElements<T>,
        TargetLocatorWebElements<T>,
        PositionWebElements<T>,
        HasConfiguration,
        Interactable {

    public interface DefaultWebElements extends CoreWebElements<DefaultWebElements> {
        public static final WebFinder<DefaultWebElements> by = new WebFinder<>(DefaultWebElements.class);
    }

}
