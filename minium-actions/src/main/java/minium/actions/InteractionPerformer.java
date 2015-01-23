package minium.actions;

import java.util.concurrent.TimeUnit;

import minium.Elements;

public interface InteractionPerformer extends MouseInteractionPerformer, KeyboardInteractionPerformer {

    /**
     * With.
     *
     * @param listeners the listeners
     * @return the interaction performer
     */
    public abstract InteractionPerformer with(InteractionListener... listeners);

    /**
     * Perform.
     *
     * @param interaction the interaction
     */
    public abstract void perform(Interaction interaction);

    /**
     * Perform.
     *
     * @param interaction the interaction
     */
    public abstract void performAndWait(AsyncInteraction interaction);

    public abstract void get(Elements elements, String url);

    /**
     * Submit.
     *
     * @param elements the elements
     */
    public abstract void submit(Elements elements);

    // select
    /**
     * Select.
     *
     * @param elems the elems
     * @param text the text
     */
    public abstract void select(Elements elems, String text);

    /**
     * Select val.
     *
     * @param elems the elems
     * @param val the val
     */
    public abstract void selectVal(Elements elems, String val);

    /**
     * Select all.
     *
     * @param elems the elems
     */
    public abstract void selectAll(Elements elems);

    public abstract void waitWhileEmpty(Elements elems) throws TimeoutException;

    public abstract void waitWhileNotEmpty(Elements elems) throws TimeoutException;

    /**
     * Check not empty.
     *
     * @param elems the elems
     * @return true, if successful
     */
    public abstract boolean checkNotEmpty(Elements elems);

    /**
     * Check empty.
     *
     * @param elems the elems
     * @return true, if successful
     */
    public abstract boolean checkEmpty(Elements elems);

    /**
     * Wait until closed.
     *
     * @param elems the elems
     */
    public abstract void waitUntilClosed(Elements elems) throws TimeoutException;

    /**
     * Wait time.
     *
     * @param time the time
     * @param unit the unit
     */
    public abstract void waitTime(long time, TimeUnit unit);

    public abstract void close(Elements elements);

    public abstract void scrollIntoView(Elements elements);

}