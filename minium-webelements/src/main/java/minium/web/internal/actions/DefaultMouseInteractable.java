package minium.web.internal.actions;

import minium.Elements;
import minium.Offsets.Offset;
import minium.actions.Interactable;
import minium.actions.MouseInteractable;
import minium.actions.internal.AbstractInteractable;

public class DefaultMouseInteractable<T extends Interactable<?>> extends AbstractInteractable<T> implements MouseInteractable<T> {

    @Override
    public T clickAndHold() {
        return clickAndHold(null);
    }

    @Override
    public T clickAndHold(Offset offset) {
        perform(new ClickAndHoldInteraction(getSource(), offset));
        return myself();
    }

    @Override
    public T release() {
        return release(null);
    }

    @Override
    public T release(Offset offset) {
        perform(new ButtonReleaseInteraction(getSource(), offset));
        return myself();
    }

    @Override
    public T click() {
        return click(null);
    }

    @Override
    public T click(Offset offset) {
        perform(new ClickInteraction(getSource(), offset));
        return myself();
    }

    @Override
    public T doubleClick() {
        return doubleClick(null);
    }

    @Override
    public T doubleClick(Offset offset) {
        perform(new DoubleClickInteraction(getSource(), offset));
        return myself();
    }

    @Override
    public T moveTo() {
        return moveTo(null);
    }

    @Override
    public T moveTo(Offset offset) {
        perform(new MoveMouseInteraction(getSource(), offset));
        return myself();
    }

    @Override
    public T contextClick() {
        return contextClick(null);
    }

    @Override
    public T contextClick(Offset offset) {
        perform(new ContextClickInteraction(getSource(), offset));
        return myself();
    }

    @Override
    public T dragAndDrop(Elements target) {
        perform(new DragAndDropInteraction(getSource(), null, target, null));
        return myself();
    }

    @Override
    public T clickAll() {
        clickAll(null);
        return myself();
    }

    @Override
    public T clickAll(Offset offset) {
        perform(new ClickAllInteraction(getSource(), offset));
        return myself();
    }

}
