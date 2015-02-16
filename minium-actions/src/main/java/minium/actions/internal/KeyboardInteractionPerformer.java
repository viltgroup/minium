package minium.actions.internal;

import minium.Elements;
import minium.actions.Keys;

public interface KeyboardInteractionPerformer {

    // from org.openqa.selenium.WebElement
    /**
     * Clear.
     *
     * @param elements the elements
     */
    public abstract void clear(Elements elements);

    // from org.openqa.selenium.interactions.Actions
    /**
     * Key down.
     *
     * @param elements the elements
     * @param keys the keys
     */
    public abstract void keyDown(Elements elements, Keys keys);

    /**
     * Key up.
     *
     * @param elements the elements
     * @param keys the keys
     */
    public abstract void keyUp(Elements elements, Keys keys);

    /**
     * Send keys.
     *
     * @param elements the elements
     * @param keys the keys
     */
    public abstract void sendKeys(Elements elements, CharSequence... keys);

    /**
     * Release.
     *
     * @param elements the elements
     */
    public abstract void release(Elements elements);

    /**
     * Type.
     *
     * @param elements the elements
     * @param text the text
     */
    public abstract void type(Elements elements, CharSequence text);

    /**
     * Clear and type.
     *
     * @param elements the elements
     * @param text the text
     */
    public abstract void fill(Elements elements, CharSequence text);

}