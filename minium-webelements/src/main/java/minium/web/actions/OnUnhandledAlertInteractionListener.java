package minium.web.actions;


/**
 * In case of an unhandled alert occurs, this listener will be able to either accept or
 * dismiss the alert.
 *
 * <pre><code class="js">
 * browser.configure()
 *   .interactionListeners()
 *     .add(minium.interactionListeners
 *       .onUnhandledAlert() // on unhandled alert
 *       .accept()           // accept (or dismiss())
 *       .thenRetry()        // then retry
 *     )
 *   .done();
 * </code></pre>
 *
 * If you omit {@code accept()} / {@code dismiss()}, it will accept the alert by default.
 *
 * @author rui.figueira
 *
 */
public interface OnUnhandledAlertInteractionListener extends OnExceptionInteractionListener {
    public OnUnhandledAlertInteractionListener accept();
    public OnUnhandledAlertInteractionListener dismiss();
}
