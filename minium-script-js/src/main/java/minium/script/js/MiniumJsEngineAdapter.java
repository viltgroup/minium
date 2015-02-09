package minium.script.js;

import minium.Finder;

public class MiniumJsEngineAdapter {

    private Finder<?> by;

    public MiniumJsEngineAdapter(Finder<?> by) {
        this.by = by;
    }

    public void adapt(JsEngine engine) {
        engine.put("__by", by);
        try {
            engine.eval("$ = require('minium'); if (typeof $.browser !== 'undefined') browser = $.browser;", 1);
        } finally {
            engine.delete("__by");
        }
    }
}
