package minium.script.js;

import minium.web.actions.WebDriverBrowser;

public interface JsBrowserFactory {

    public abstract WebDriverBrowser<?> create(Object obj);

}