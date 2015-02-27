package minium.web.internal.actions;

import minium.actions.Interactable;
import minium.actions.internal.AbstractInteractable;
import minium.web.actions.WebInteractable;

public class DefaultWebInteractable<T extends Interactable<?>> extends AbstractInteractable<T> implements WebInteractable<T> {

    @Override
    public T submit() {
        return perform(new SubmitInteraction(getSource()));
    }

    @Override
    public T check() {
    	return perform(new CheckInteraction(getSource()));
    }

    @Override
    public T uncheck() {
    	return perform(new UncheckInteraction(getSource()));
    }

    // select
    @Override
    public T select(String text) {
    	return perform(new SelectInteraction(getSource(), text));
    }

    @Override
    public T deselect(String text) {
    	return perform(new DeselectInteraction(getSource(), text));
    }

    @Override
    public T selectVal(String val) {
    	return perform(new SelectValInteraction(getSource(), val));
    }

    @Override
    public T deselectVal(String val) {
    	return perform(new DeselectValInteraction(getSource(), val));
    }

    @Override
    public T selectAll() {
    	return perform(new SelectAllInteraction(getSource()));
    }

    @Override
    public T deselectAll() {
    	return perform(new DeselectAllInteraction(getSource()));
    }

    @Override
    public T scrollIntoView() {
    	return perform(new ScrollIntoViewInteraction(getSource()));
    }

    @Override
    public T close() {
    	return perform(new CloseInteraction(getSource()));
    }
}
