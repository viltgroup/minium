package minium.actions;

import minium.AsIs;

/**
 * <p>Objects that can perform interactions are known as Interactable. Typically, all Elements / WebElements
 * are interactable. Interactable interfaces normally "hide" interactions behind methods like
 * .click() or .fill().</p>
 *
 * <p>This is the root interface for all other {@link Interactable} interfaces such as {@link MouseInteractable} or
 * {@link KeyboardInteractable}. It makes available generic methods to perform interactions on the corresponding
 * elements.</p>
 *
 * @author rui.figueira
 *
 * @param <T> this {@link Interactable} for chainable calls
 */
public interface Interactable<T extends Interactable<?>> extends AsIs {

    /**
     * Performs the specified interaction on the corresponding elements.
     *
     * @param interaction the interaction to perform
     */
    public abstract T perform(Interaction interaction);

    /**
     * Performs the specified asynchronous interaction on the corresponding elements, and waits until it completes.
     *
     * @param interaction the asynchronous interaction
     */
    public abstract T performAndWait(AsyncInteraction interaction);
}
