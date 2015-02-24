package minium.web.internal.actions;

import minium.actions.Interactable;
import minium.actions.KeyboardInteractable;
import minium.actions.Keys;
import minium.actions.internal.AbstractInteractable;

public class DefaultKeyboardInteractable<T extends Interactable<?>> extends AbstractInteractable<T> implements KeyboardInteractable<T> {

    @Override
    public T keyUp(Keys keys) {
        perform(new KeyUpInteraction(getSource(), keys));
        return myself();
    }

    @Override
    public T keyDown(Keys keys) {
        perform(new KeyDownInteraction(getSource(), keys));
        return myself();
    }

    @Override
    public T sendKeys(CharSequence keys) {
        sendKeys(new CharSequence[] { keys });
        return myself();
    }

    @Override
    public T sendKeys(CharSequence... keys) {
        perform(new SendKeysInteraction(getSource(), keys));
        return myself();
    }

    @Override
    public T clear() {
        perform(new ClearInteraction(getSource()));
        return myself();
    }

    @Override
    public T type(CharSequence text) {
        perform(new TypeInteraction(getSource(), text));
        return myself();
    }

    @Override
    public T fill(CharSequence text) {
        perform(new FillInteraction(getSource(), text));
        return myself();
    }
}
