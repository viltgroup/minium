package minium.actions;

import minium.Elements;
import minium.Offsets.Offset;

public interface MouseInteractionPerformer {

    /**
     * Click and hold.
     *
     * @param elements the elements
     */
    public abstract void clickAndHold(Elements elements);

    public abstract void clickAndHold(Elements elements, Offset offset);

    public abstract void release(Elements element);

    public abstract void release(Elements element, Offset offset);

    /**
     * Click.
     *
     * @param elements the elements
     */
    public abstract void click(Elements elements);

    public abstract void click(Elements elements, Offset offset);

    /**
     * Double click.
     *
     * @param elements the elements
     */
    public abstract void doubleClick(Elements elements);

    public abstract void doubleClick(Elements elements, Offset offset);

    /**
     * Move to element.
     *
     * @param elements the elements
     */
    public abstract void moveTo(Elements elements);

    public abstract void moveTo(Elements elements, Offset offset);

    /**
     * Context click.
     *
     * @param elements the elements
     */
    public abstract void contextClick(Elements elements);

    public abstract void contextClick(Elements elements, Offset offset);

    /**
     * Drag and drop.
     *
     * @param source the source
     * @param target the target
     */
    public abstract void dragAndDrop(Elements source, Elements target);

    // additional methods
    /**
     * Click all.
     *
     * @param elements the elements
     */
    public abstract void clickAll(Elements elements);

    public abstract void clickAll(Elements elements, Offset offset);

    public abstract void check(Elements elements);

    public abstract void uncheck(Elements elements);

    /**
     * Deselect.
     *
     * @param elems the elems
     * @param text the text
     */
    public abstract void deselect(Elements elems, String text);

    /**
     * Deselect val.
     *
     * @param elems the elems
     * @param val the val
     */
    public abstract void deselectVal(Elements elems, String val);

    /**
     * Deselect all.
     *
     * @param elems the elems
     */
    public abstract void deselectAll(Elements elems);

}