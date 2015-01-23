package minium.web;

import minium.actions.Interactable;

public interface DefaultWebElements extends
        BasicWebElements<DefaultWebElements>,
        JQueryExtWebElements<DefaultWebElements>,
        EvalWebElements<DefaultWebElements>,
        TargetLocatorWebElements<DefaultWebElements>,
        PositionWebElements<DefaultWebElements>,
        Interactable {

    public static final WebFinder<DefaultWebElements> by = new WebFinder<>(DefaultWebElements.class);

}
