package minium.actions;

import minium.Elements;

public interface TouchInteractionPerformer {

    // from org.openqa.selenium.interactions.touch.TouchActions
    /**
     * Single tap.
     *
     * @param elements the elements
     */
    public abstract void singleTap(Elements elements);

    /**
     * Down.
     *
     * @param elements the elements
     * @param x the x
     * @param y the y
     */
    public abstract void down(Elements elements, int x, int y);

    /**
     * Up.
     *
     * @param elements the elements
     * @param x the x
     * @param y the y
     */
    public abstract void up(Elements elements, int x, int y);

    /**
     * Move.
     *
     * @param elements the elements
     * @param x the x
     * @param y the y
     */
    public abstract void move(Elements elements, int x, int y);

    /**
     * Double tap.
     *
     * @param elements the elements
     */
    public abstract void doubleTap(Elements elements);

    /**
     * Long press.
     *
     * @param elements the elements
     */
    public abstract void longPress(Elements elements);

    /**
     * Scroll.
     *
     * @param elements the elements
     * @param xOffset the x offset
     * @param yOffset the y offset
     */
    public abstract void scroll(Elements elements, int xOffset, int yOffset);

    /**
     * Flick.
     *
     * @param elements the elements
     * @param xSpeed the x speed
     * @param ySpeed the y speed
     */
    public abstract void flick(Elements elements, int xSpeed, int ySpeed);

}