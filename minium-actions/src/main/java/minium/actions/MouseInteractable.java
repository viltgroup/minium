package minium.actions;

import minium.Elements;
import minium.Offsets.Offset;

public interface MouseInteractable<T extends Interactable<?>> extends Interactable<T> {
    public T clickAndHold();
    public T clickAndHold(Offset offset);
    public T release();
    public T release(Offset offset);
    public T click();
    public T click(Offset offset);
    public T doubleClick();
    public T doubleClick(Offset offset);
    public T moveTo();
    public T moveTo(Offset offset);
    public T contextClick();
    public T contextClick(Offset offset);
    public T dragAndDrop(Elements target);
}
