package minium.web.actions;


public interface OnUnhandledAlertInteractionListener extends OnExceptionInteractionListener {
    public OnUnhandledAlertInteractionListener accept();
    public OnUnhandledAlertInteractionListener dismiss();
}
