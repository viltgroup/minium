package com.vilt.minium;

import minium.actions.InteractionPerformer;
import minium.web.WebElements;
import minium.web.WebElementsFactory;
import minium.web.WebElementsFactory.Builder;
import minium.web.WebModule;
import minium.web.WebModules;
import minium.web.internal.actions.WebInteractionPerformer;
import minium.web.internal.drivers.WindowWebDriver;

import org.openqa.selenium.WebDriver;

import com.vilt.minium.debug.DebugWebElements;
import com.vilt.minium.tips.TipWebElements;

public class DefaultWebElementsDriver extends WindowWebDriver {

    private static final WebModule LEGACY_MODULE = new WebModule() {

        @Override
        public void configure(Builder<?> builder) {
            builder
            .implementingInterfaces(DefaultWebElements.class);
        }
    };

    private static final WebModule TIP_MODULE = new WebModule() {

        @Override
        public void configure(Builder<?> builder) {
            builder
            .implementingInterfaces(TipWebElements.class)
            .withJsResources(
                    "minium/qtip2/jquery.qtip.min.js",
                    "minium/ba-dotimeout/jquery.ba-dotimeout.min.js",
                    "minium/internal/js/jquery.tip.js")
            .withCssResources(
                    "minium/qtip2/jquery.qtip.min.css",
                    "minium/internal/css/tip.css");
        }
    };

    private static final WebModule DEBUG_MODULE = new WebModule() {

        @Override
        public void configure(Builder<?> builder) {
            builder
            .implementingInterfaces(DebugWebElements.class)
            .withJsResources(
                "minium/jquery-ui/ui/minified/effect.min.js",
                "minium/jquery-ui/ui/minified/effect-highlight.min.js",
                "minium/internal/js/jquery.debug.js");
        }
    };

    private WebElementsFactory<DefaultWebElements> factory;

    public DefaultWebElementsDriver(WebDriver webDriver) {
        super(webDriver);
        Builder<DefaultWebElements> builder = new WebElementsFactory.Builder<DefaultWebElements>();
        InteractionPerformer performer = new WebInteractionPerformer();


        WebModule module = WebModules.combine(WebModules.interactableModule(performer), WebModules.defaultModule(this), TIP_MODULE, DEBUG_MODULE, LEGACY_MODULE);
        module.configure(builder);
        factory = builder.build();
    }

    /**
     * Web elements.
     *
     * @return the t
     */
    public DefaultWebElements webElements() {
        return factory.createRoot();
    }

    public DefaultWebElements find(String selector) {
        return webElements().find(selector);
    }

    public DefaultWebElements find(WebElements elems) {
        return webElements().find(elems);
    }

}
