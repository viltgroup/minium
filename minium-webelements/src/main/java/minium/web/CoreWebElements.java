package minium.web;

import minium.FreezableElements;
import minium.IterableElements;
import minium.actions.HasConfiguration;
import minium.actions.Interactable;
import minium.actions.KeyboardInteractable;
import minium.actions.MouseInteractable;
import minium.actions.WaitInteractable;
import minium.web.actions.HasAlert;
import minium.web.actions.HasBrowser;
import minium.web.actions.WebInteractable;

public interface CoreWebElements<T extends WebElements & Interactable<T>> extends
        BasicWebElements<T>,
        FreezableElements<T>,
        IterableElements<T>,
        ConditionalWebElements<T>,
        ExtensionsWebElements<T>,
        EvalWebElements<T>,
        TargetLocatorWebElements<T>,
        PositionWebElements<T>,
        HasBrowser<T>,
        HasAlert,
        HasConfiguration,
        WaitInteractable<T>,
        MouseInteractable<T>,
        KeyboardInteractable<T>,
        WebInteractable<T> {

    public interface DefaultWebElements extends CoreWebElements<DefaultWebElements> { }

}
