package minium.web.internal;

import minium.web.internal.drivers.JavascriptInvoker;

public interface HasJavascriptInvoker {

    public JavascriptInvoker javascriptInvoker();

    public static class Impl implements HasJavascriptInvoker {

        private final JavascriptInvoker javascriptInvoker;

        public Impl(JavascriptInvoker javascriptInvoker) {
            this.javascriptInvoker = javascriptInvoker;
        }

        @Override
        public JavascriptInvoker javascriptInvoker() {
            return javascriptInvoker;
        }

    }
}
