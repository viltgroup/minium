package minium.web.internal.actions;

import minium.ElementsException;
import minium.actions.Interactable;
import minium.actions.internal.AbstractInteractable;
import minium.web.actions.WebInteractable;

public class DefaultWebInteractable<T extends Interactable<?>> extends AbstractInteractable<T> implements WebInteractable<T> {

    @Override
    public void submit() {
        perform(new SubmitInteraction(getSource()));
    }

    @Override
    public void check() {
        perform(new CheckInteraction(getSource()));
    }

    @Override
    public void uncheck() {
        perform(new UncheckInteraction(getSource()));
    }

    // select
    @Override
    public void select(String text) {
        perform(new SelectInteraction(getSource(), text));
    }

    @Override
    public void deselect(String text) {
        perform(new DeselectInteraction(getSource(), text));
    }

    @Override
    public void selectVal(String val) {
        perform(new SelectValInteraction(getSource(), val));
    }

    @Override
    public void deselectVal(String val) {
        perform(new DeselectValInteraction(getSource(), val));
    }

    @Override
    public void selectAll() {
        perform(new SelectAllInteraction(getSource()));
    }

    @Override
    public void deselectAll() {
        perform(new DeselectAllInteraction(getSource()));
    }

    @Override
    public void scrollIntoView() {
        perform(new ScrollIntoViewInteraction(getSource()));
    }

    @Override
    public void close() {
        perform(new CloseInteraction(getSource()));
    }

    @Override
    public void waitUntilClosed() throws ElementsException {
        perform(new WaitWindowClosedElementsInteraction(getSource(), null));
    }
}
