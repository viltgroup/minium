package minium.visual;

import minium.FreezableElements;
import minium.PositionElements;
import minium.actions.Interactable;

public interface CoreVisualElements<T extends VisualElements> extends
        BasicVisualElements<T>,
        CornersVisualElements<T>,
        PositionElements<T>,
        FreezableElements<T>,
        Interactable {

    public interface DefaultVisualElements extends CoreVisualElements<DefaultVisualElements> {
        public static final VisualFinder<DefaultVisualElements> by = new VisualFinder<DefaultVisualElements>(DefaultVisualElements.class);
    }

}
