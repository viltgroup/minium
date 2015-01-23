package minium.visual;

import minium.actions.Interactable;

public interface DefaultVisualElements extends BasicVisualElements<DefaultVisualElements>, CornersVisualElements<DefaultVisualElements>, Interactable {

    public static final VisualFinder<DefaultVisualElements> by = new VisualFinder<DefaultVisualElements>(DefaultVisualElements.class);

}
