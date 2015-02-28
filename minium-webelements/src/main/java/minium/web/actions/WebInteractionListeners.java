package minium.web.actions;

import minium.web.internal.actions.DefaultOnStaleElementReferenceInteractionListener;
import minium.web.internal.actions.DefaultOnTimeoutInteractionListener;
import minium.web.internal.actions.DefaultOnUnhandledAlertInteractionListener;

public class WebInteractionListeners {

    public static OnUnhandledAlertInteractionListener onUnhandledAlert() {
        return new DefaultOnUnhandledAlertInteractionListener();
    }

    public static OnTimeoutInteractionListener onTimeout() {
        return new DefaultOnTimeoutInteractionListener();
    }

    public static OnExceptionInteractionListener onStaleElementReference() {
        return new DefaultOnStaleElementReferenceInteractionListener();
    }
}
