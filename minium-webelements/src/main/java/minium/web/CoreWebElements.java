package minium.web;

import minium.FreezableElements;
import minium.actions.HasConfiguration;
import minium.actions.Interactable;
import minium.actions.WaitInteractable;

public interface CoreWebElements<T extends WebElements> extends
        BasicWebElements<T>,
        FreezableElements<T>,
        ConditionalWebElements<T>,
        ExtensionsWebElements<T>,
        EvalWebElements<T>,
        TargetLocatorWebElements<T>,
        PositionWebElements<T>,
        HasConfiguration,
        WaitInteractable,
        Interactable {

    public interface DefaultWebElements extends CoreWebElements<DefaultWebElements> {
        public static final WebFinder<DefaultWebElements> by = new WebFinder<>(DefaultWebElements.class);
    }

}