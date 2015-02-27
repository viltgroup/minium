package minium.web.actions;

import minium.actions.Interactable;

public interface WebInteractable<T extends Interactable<?>> extends Interactable<T> {
    public T submit();
    public T check();
    public T uncheck();
    public T select(String text);
    public T deselect(String text);
    public T selectVal(String text);
    public T deselectVal(String text);
    public T selectAll();
    public T deselectAll();
    public T scrollIntoView();
    public T close();
}
