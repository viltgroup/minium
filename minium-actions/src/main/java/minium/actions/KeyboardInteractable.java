package minium.actions;

/**
 * Keyboard interactions can be performed using this interactable interface. It "hides"
 * keyboard interactions under its methods. It mimics most of keyboard actions in
 * org.openqa.selenium.interactions.Actions.
 *
 * @author rui.figueira
 *
 * @param <T> this {@link Interactable} for chainable calls
 * @see org.openqa.selenium.interactions.Actions
 */
public interface KeyboardInteractable<T extends Interactable<?>> extends Interactable<T> {

    /**
     * Clears all input text in the first matched field.
     *
     * @return this {@link Interactable}
     */
    public T clear();

    /**
     * Performs a modifier key press. Does not release the modifier key - subsequent interactions
     * may assume it's kept pressed. Note that the modifier key is never released implicitly -
     * either <code>keyUp(theKey)</code> or <code>sendKeys(Keys.NULL)</code> must be called to
     * release the modifier.
     *
     * @param keys either <code>Keys.SHIFT</code>, <code>Keys.ALT</code> or <code>Keys.CONTROL</code>.
     *  If the provided key is none of those, {@link IllegalArgumentException} is thrown
     * @return this {@link Interactable}
     */
    public T keyDown(Keys keys);

    /**
     * Performs a modifier key press after focusing on an element.
     *
     * @param keys either <code>Keys.SHIFT</code>, <code>Keys.ALT</code> or <code>Keys.CONTROL</code>.
     *  If the provided key is none of those, {@link IllegalArgumentException} is thrown
     * @return this {@link Interactable}
     */
    public T keyUp(Keys keys);

    /**
     * Sends keys to this element.
     *
     * @param keys keys to send
     * @return this {@link Interactable}
     */
    public T sendKeys(CharSequence ... keys);

    /**
     * Sends keys to this element.
     *
     * @param keys keys to send
     * @return this {@link Interactable}
     */
    public T sendKeys(CharSequence keys);

    /**
     * Same as {@link #sendKeys(CharSequence)}
     *
     * @param text text to type
     * @return this {@link Interactable}
     */
    public T type(CharSequence text);

    /**
     * Same as {@link #clear()} followed by {@link #sendKeys(CharSequence)}
     *
     * @param text text to type
     * @return this {@link Interactable}
     */
    public T fill(CharSequence text);
}
