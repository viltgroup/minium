package minium.actions;

import minium.Elements;
import minium.Offsets.Offset;

/**
 * Mouse interactions can be performed using this interactable interface. It "hides"
 * mouse interactions under its methods. It mimics most of mouse actions in
 * org.openqa.selenium.interactions.Actions.
 *
 * @author rui.figueira
 *
 * @param <T> this {@link Interactable} for chainable calls
 * @see org.openqa.selenium.interactions.Actions
 */
public interface MouseInteractable<T extends Interactable<?>> extends Interactable<T> {

    /**
     * Clicks (without releasing) at the current mouse location.
     *
     * @return this {@link Interactable}
     */
    public T clickAndHold();

    /**
     * Clicks (without releasing) at the current mouse location with the specified offset.
     *
     * @param offset offset relative to the first matched element
     * @return this {@link Interactable}
     */
    public T clickAndHold(Offset offset);

    /**
     * Releases the pressed left mouse button at the current mouse location.
     *
     * @return this {@link Interactable}
     */
    public T release();

    /**
     * Releases the pressed left mouse button at the current mouse location with
     * the specified offset.
     *
     * @param offset offset relative to the first matched element
     * @return this {@link Interactable}
     */
    public T release(Offset offset);

    /**
     * Clicks in the middle of the first matched element.
     *
     * @return this {@link Interactable}
     */
    public T click();

    /**
     * Clicks in the middle of the first matched element with the specified offset.
     *
     * @param offset offset relative to the first matched element
     * @return this {@link Interactable}
     */
    public T click(Offset offset);

    /**
     * Performs a double-click at middle of the first matched element.
     *
     * @return this {@link Interactable}
     */
    public T doubleClick();

    /**
     * Performs a double-click at middle of the first matched element with the
     * specified offset.
     *
     * @param offset offset relative to the first matched element
     * @return this {@link Interactable}
     */
    public T doubleClick(Offset offset);

    /**
     * Moves the mouse to the middle of the first matched element.
     *
     * @return this {@link Interactable}
     */
    public T moveTo();

    /**
     * Moves the mouse to the middle of the first matched element with the
     * specified offset.
     *
     * @param offset offset relative to the first matched element
     * @return this {@link Interactable}
     */
    public T moveTo(Offset offset);

    /**
     * Performs a context-click at middle of the first matched element.
     *
     * @return this {@link Interactable}
     */
    public T contextClick();

    /**
     * Performs a context-click at middle of the first matched element with the
     * specified offset.
     *
     * @param offset offset relative to the first matched element
     * @return this {@link Interactable}
     */
    public T contextClick(Offset offset);

    /**
     * A convenience method that performs click-and-hold at the location of the
     * first matched source element, moves to the location of the first matched target
     * element, then releases the mouse.
     *
     * @param target {@link Elements} expression to move to and release the mouse at.
     * @return this {@link Interactable}
     */
    public T dragAndDrop(Elements target);
}
