package minium.visual;

import minium.FreezableElements;
import minium.PositionElements;
import minium.actions.Interactable;

public interface DefaultVisualElements extends
    BasicVisualElements<DefaultVisualElements>,
    CornersVisualElements<DefaultVisualElements>,
    PositionElements<DefaultVisualElements>,
    FreezableElements<DefaultVisualElements>,
    Interactable {

    public static final VisualFinder<DefaultVisualElements> by = new VisualFinder<DefaultVisualElements>(DefaultVisualElements.class);

}
