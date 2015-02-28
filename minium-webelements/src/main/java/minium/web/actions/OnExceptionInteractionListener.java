package minium.web.actions;

import minium.actions.InteractionListener;

public interface OnExceptionInteractionListener extends InteractionListener {
    public InteractionListener thenAbort();
    public InteractionListener thenRetry();
}
