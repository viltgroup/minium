package minium.web.actions;

import minium.web.internal.actions.DefaultUnhandledAlertInteractionListener;

public class WebInteractionListeners {

    public static UnhandledAlertInteractionListener unhandledAlert() {
        return new DefaultUnhandledAlertInteractionListener();
    }
}
