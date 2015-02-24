package minium.actions.debug;

import minium.actions.Interactable;

public interface DebugInteractable<T extends Interactable<?>> extends Interactable<T> {

    public void highlight();

}
