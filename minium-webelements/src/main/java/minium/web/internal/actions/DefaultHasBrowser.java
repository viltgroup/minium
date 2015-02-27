package minium.web.internal.actions;

import minium.internal.Chainable;
import minium.web.WebElements;
import minium.web.actions.Browser;
import minium.web.actions.HasBrowser;

public class DefaultHasBrowser<T extends WebElements> extends Chainable<T> implements HasBrowser<T> {

    @Override
    public Browser<T> browser() {
        return new InternalBrowser<T>(myself());
    }
}
