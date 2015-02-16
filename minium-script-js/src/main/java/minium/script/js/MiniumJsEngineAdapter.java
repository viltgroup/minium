package minium.script.js;

import minium.Locator;

public class MiniumJsEngineAdapter {

    private Locator<?> by;
    private JsBrowserFactory browserFactory;

    public MiniumJsEngineAdapter(Locator<?> by, JsBrowserFactory factory) {
        this.by = by;
        browserFactory = factory;
    }

    public void adapt(JsEngine engine) {
        engine.put("__by", by);
        engine.put("__browserFactory", browserFactory);
        try {
            engine.eval("minium = require('minium'); $ = minium.$; if (typeof minium.browser !== 'undefined') browser = minium.browser;", 1);
            engine.eval("require('minium').__browserFactory = __browserFactory;", 1);
        } finally {
            engine.delete("__browserFactory");
            engine.delete("__by");
        }
    }
}
