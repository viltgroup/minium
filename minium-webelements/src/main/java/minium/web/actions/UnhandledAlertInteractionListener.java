package minium.web.actions;

import minium.actions.InteractionListener;

public interface UnhandledAlertInteractionListener extends InteractionListener {

    public InteractionListener accept();

    public InteractionListener dismiss();
}
