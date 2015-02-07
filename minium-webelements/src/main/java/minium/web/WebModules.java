package minium.web;

import minium.actions.Configuration;
import minium.actions.HasConfiguration;
import minium.actions.Interactable;
import minium.actions.InteractionPerformer;
import minium.actions.WaitInteractable;
import minium.actions.debug.DebugInteractable;
import minium.actions.debug.DebugInteractionPerformer;
import minium.actions.internal.DefaultConfiguration;
import minium.actions.internal.DefaultDebugInteractable;
import minium.actions.internal.DefaultInteractable;
import minium.actions.internal.DefaultWaitInteractable;
import minium.internal.LocatableElements;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.WebElementsFactory.Builder;
import minium.web.actions.HasBrowser;
import minium.web.internal.actions.DefaultHasBrowser;
import minium.web.internal.actions.WebInteractionPerformer;

import org.openqa.selenium.WebDriver;

import platypus.AbstractMixinInitializer;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

public class WebModules {

    public static WebModule positionModule() {
        return new WebModule() {

            @Override
            public void configure(Builder<?> builder) {
                builder
                    .withJsResources(
                            "minium/web/internal/lib/jquery.min.js",
                            "minium/web/internal/lib/jquery.position.js"
                    )
                    .implementingInterfaces(
                            PositionWebElements.class
                    );
            }
        };
    }

    public static WebModule conditionalModule() {
        return new WebModule() {

            @Override
            public void configure(Builder<?> builder) {
                builder
                .withJsResources(
                        "minium/web/internal/lib/jquery.min.js",
                        "minium/web/internal/lib/jquery.conditional.js"
                        )
                        .implementingInterfaces(
                                ConditionalWebElements.class
                        );
            }
        };
    }

    public static WebModule defaultModule(final WebDriver wd) {
        InteractionPerformer performer = new WebInteractionPerformer();
        return combine(baseModule(wd), positionModule(), conditionalModule(), interactableModule(performer));
    }

    public static WebModule baseModule(WebDriver wd) {
        return baseModule(wd, DefaultWebElements.class);
    }

    public static WebModule baseModule(final WebDriver wd, final Class<? extends WebElements> intf) {
        WebModule baseModule = new WebModule() {
            @Override
            public void configure(Builder<?> builder) {
                builder
                .withWebDriver(wd)
                .withJsResources(
                        "minium/web/internal/lib/jquery.min.js",
                        "minium/web/internal/lib/jquery.eval.js",
                        "minium/web/internal/lib/jquery.minium-exts.js",
                        "minium/web/internal/lib/jquery.visibleText.js"
                )
                .implementingInterfaces(
                        intf,
                        LocatableElements.class,
                        HasBrowser.class
                )
                .usingMixinConfigurer(new AbstractMixinInitializer() {
                    @Override
                    protected void initialize() {
                        implement(HasBrowser.class).with(new DefaultHasBrowser());
                    }
                });
            }
        };
        return baseModule;
    };

    public static WebModule javascriptDelegatingInterfaceModule(final Class<?> intf) {
        Preconditions.checkArgument(intf.isInterface());
        return new WebModule() {
            @Override
            public void configure(Builder<?> builder) {
                builder.implementingInterfaces(intf);
            }
        };
    };

    public static WebModule interactableModule(final InteractionPerformer performer) {
        Preconditions.checkNotNull(performer);
        final Configuration configuration = new DefaultConfiguration();
        return new WebModule() {
            @Override
            public void configure(Builder<?> builder) {
                builder
                .implementingInterfaces(Interactable.class, HasConfiguration.class)
                .usingMixinConfigurer(new AbstractMixinInitializer() {

                    @Override
                    protected void initialize() {
                        implement(HasConfiguration.class).with(new HasConfiguration.Impl(configuration));
                        implement(Interactable.class).with(new DefaultInteractable(performer));
                        implement(WaitInteractable.class).with(new DefaultWaitInteractable(performer));
                    }
                });
            }
        };
    };

    public static WebModule debugModule(final DebugInteractionPerformer performer) {
        Preconditions.checkNotNull(performer);
        return new WebModule() {
            @Override
            public void configure(Builder<?> builder) {
                builder
                .implementingInterfaces(DebugInteractable.class)
                .withJsResources("minium/web/internal/lib/effect.min.js", "minium/web/internal/lib/effect-highlight.min.js")
                .usingMixinConfigurer(new AbstractMixinInitializer() {
                    @Override
                    protected void initialize() {
                        DebugInteractable interactable = new DefaultDebugInteractable(performer);
                        implement(DebugInteractable.class).with(interactable);
                    }
                });
            }
        };
    }

    public static WebModule combine(final Iterable<? extends WebModule> modules) {
        return new WebModule() {
            @Override
            public void configure(Builder<?> builder) {
                for (WebModule module : modules) {
                    module.configure(builder);
                }
            }
        };
    }

    public static WebModule combine(final WebModule ... modules) {
        return combine(ImmutableList.copyOf(modules));
    }
}
