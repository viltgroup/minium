package minium.web.internal.actions;

import minium.Elements;
import minium.web.WebElements;
import minium.web.actions.Browser;
import minium.web.actions.HasBrowser;
import platypus.Mixin;

import com.google.common.reflect.TypeToken;

public class DefaultHasBrowser<T extends WebElements> extends Mixin.Impl implements HasBrowser<T> {

    @Override
    public Browser<T> browser() {
        @SuppressWarnings("serial")
        TypeToken<HasWebLocator<T>> hasLocatorToken = new TypeToken<HasWebLocator<T>>(DefaultHasBrowser.class) { };
        return new InternalBrowser<T>(this.as(Elements.class), this.as(hasLocatorToken).locator());
    }
}
