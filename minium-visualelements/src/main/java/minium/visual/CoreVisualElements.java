package minium.visual;

import minium.FreezableElements;
import minium.PositionElements;
import minium.actions.HasConfiguration;
import minium.actions.Interactable;
import minium.actions.WaitInteractable;

public interface CoreVisualElements<T extends VisualElements> extends
        BasicVisualElements<T>,
        PositionElements<T>,
        FreezableElements<T>,
        HasConfiguration,
        WaitInteractable,
        Interactable {

    public interface DefaultVisualElements extends CoreVisualElements<DefaultVisualElements> {
        public static final VisualLocator<DefaultVisualElements> by = new VisualLocator<DefaultVisualElements>(DefaultVisualElements.class);
    }

}
