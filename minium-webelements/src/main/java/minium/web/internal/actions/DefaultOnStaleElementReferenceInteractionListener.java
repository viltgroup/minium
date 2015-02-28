package minium.web.internal.actions;

import minium.actions.Interaction;
import minium.actions.internal.AbstractInteraction;
import minium.actions.internal.AfterFailInteractionEvent;

import org.openqa.selenium.StaleElementReferenceException;

public class DefaultOnStaleElementReferenceInteractionListener extends AbstractOnExceptionInteractionListener {

    @SuppressWarnings("unchecked")
    public DefaultOnStaleElementReferenceInteractionListener() {
        super(StaleElementReferenceException.class);
    }

    @Override
    protected boolean handle(AfterFailInteractionEvent event) {
        Interaction interaction = event.getInteraction();
        if (interaction instanceof AbstractInteraction) {
            ((AbstractInteraction) interaction).refreeze();
        }
        return true;
    }
}
