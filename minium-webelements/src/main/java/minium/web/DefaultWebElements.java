package minium.web;

import minium.actions.Interactable;

public interface DefaultWebElements extends
        BasicWebElements<DefaultWebElements>,
        JQueryExtWebElements<DefaultWebElements>,
        EvalWebElements<DefaultWebElements>,
        TargetLocatorWebElements<DefaultWebElements>,
        PositionWebElements<DefaultWebElements>,
        Interactable {

}
