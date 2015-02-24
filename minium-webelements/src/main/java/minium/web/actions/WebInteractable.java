package minium.web.actions;

import minium.actions.Interactable;

public interface WebInteractable<T extends Interactable<?>> extends Interactable<T> {
    public void submit();
    public void check();
    public void uncheck();
    public void select(String text);
    public void deselect(String text);
    public void selectVal(String text);
    public void deselectVal(String text);
    public void selectAll();
    public void deselectAll();

    public void scrollIntoView();
    public void close();
    public void waitUntilClosed();
}
