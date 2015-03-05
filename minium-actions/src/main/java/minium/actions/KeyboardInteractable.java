package minium.actions;

/**
 * Keyboard interactions can be performed using this interactable interface. It basically mimics
 *
 *
 * @author rui.figueira
 *
 * @param <T>
 */
public interface KeyboardInteractable<T extends Interactable<?>> extends Interactable<T> {
    public T clear();
    public T keyDown(Keys keys);
    public T keyUp(Keys keys);
    public T sendKeys(CharSequence ... keys);
    public T sendKeys(CharSequence keys);
    public T type(CharSequence text);
    public T fill(CharSequence text);
}
