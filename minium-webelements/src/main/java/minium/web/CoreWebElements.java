package minium.web;

import minium.FreezableElements;
import minium.actions.HasConfiguration;
import minium.actions.Interactable;
import minium.actions.WaitInteractable;
import minium.web.actions.HasAlert;
import minium.web.actions.HasBrowser;

public interface CoreWebElements<T extends WebElements> extends
        BasicWebElements<T>,
        FreezableElements<T>,
        ConditionalWebElements<T>,
        ExtensionsWebElements<T>,
        EvalWebElements<T>,
        TargetLocatorWebElements<T>,
        PositionWebElements<T>,
        HasAlert,
        HasConfiguration,
        HasBrowser,
        WaitInteractable,
        Interactable {

    public interface DefaultWebElements extends CoreWebElements<DefaultWebElements> {
        public static final WebLocator<DefaultWebElements> by = new WebLocator<>(DefaultWebElements.class);
    }

}
