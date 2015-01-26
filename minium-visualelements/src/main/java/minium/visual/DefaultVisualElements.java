package minium.visual;

import minium.FreezableElements;
import minium.PositionElements;
import minium.actions.Interactable;
import minium.actions.debug.DebugInteractable;

public interface DefaultVisualElements extends
    BasicVisualElements<DefaultVisualElements>,
    CornersVisualElements<DefaultVisualElements>,
    PositionElements<DefaultVisualElements>,
    FreezableElements<DefaultVisualElements>,
    DebugInteractable,
    Interactable {

    public static final VisualFinder<DefaultVisualElements> by = new VisualFinder<DefaultVisualElements>(DefaultVisualElements.class);

}
