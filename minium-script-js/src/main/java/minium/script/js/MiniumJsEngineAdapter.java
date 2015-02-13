package minium.script.js;

import minium.Locator;

public class MiniumJsEngineAdapter {

    private Locator<?> by;
    private JsWebDriverFactory browsers;

    public MiniumJsEngineAdapter(Locator<?> by, JsWebDriverFactory factory) {
        this.by = by;
        browsers = factory;
    }

    public void adapt(JsEngine engine) {
        engine.put("browsers", browsers);
        engine.put("__by", by);
        try {
            engine.eval("$ = require('minium'); if (typeof $.browser !== 'undefined') browser = $.browser;", 1);
        } finally {
            engine.delete("__by");
        }
    }
}
