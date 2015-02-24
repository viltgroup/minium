package minium.actions;

public interface KeyboardInteractable<T extends Interactable<?>> extends Interactable<T> {
    public T clear();
    public T keyDown(Keys keys);
    public T keyUp(Keys keys);
    public T sendKeys(CharSequence ... keys);
    public T sendKeys(CharSequence keys);
    public T type(CharSequence text);
    public T fill(CharSequence text);
}
