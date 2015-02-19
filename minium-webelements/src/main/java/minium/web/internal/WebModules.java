package minium.web.internal;

import static java.lang.String.format;
import minium.actions.Configuration;
import minium.actions.HasConfiguration;
import minium.actions.Interactable;
import minium.actions.WaitInteractable;
import minium.actions.debug.DebugInteractable;
import minium.actions.debug.DebugInteractionPerformer;
import minium.actions.internal.DefaultConfiguration;
import minium.actions.internal.DefaultDebugInteractable;
import minium.actions.internal.DefaultInteractable;
import minium.actions.internal.DefaultWaitInteractable;
import minium.actions.internal.InteractionPerformer;
import minium.internal.LocatableElements;
import minium.web.ConditionalWebElements;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.PositionWebElements;
import minium.web.WebElements;
import minium.web.actions.HasAlert;
import minium.web.internal.WebElementsFactory.Builder;
import minium.web.internal.actions.DefaultHasAlert;
import minium.web.internal.actions.WebInteractionPerformer;

import org.openqa.selenium.WebDriver;

import platypus.AbstractMixinInitializer;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

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

            @Override
            public String toString() {
                return "WebModule[position]";
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

            @Override
            public String toString() {
                return "WebModule[conditional]";
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
                        HasAlert.class
                )
                .usingMixinConfigurer(new AbstractMixinInitializer() {
                    @Override
                    protected void initialize() {
                        implement(HasAlert.class).with(new DefaultHasAlert());
                    }
                });
            }

            @Override
            public String toString() {
                return "WebModule[base]";
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

            @Override
            public String toString() {
                return "WebModule[javascript delegating]";
            }
        };
    }

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

            @Override
            public String toString() {
                return "WebModule[interactable]";
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
                .withJsResources(
                        "minium/web/internal/lib/jquery.min.js",
                        "minium/web/internal/lib/jquery.highlight.js")
                .usingMixinConfigurer(new AbstractMixinInitializer() {
                    @Override
                    protected void initialize() {
                        DebugInteractable interactable = new DefaultDebugInteractable(performer);
                        implement(DebugInteractable.class).with(interactable);
                    }
                });
            }

            @Override
            public String toString() {
                return "WebModule[debug]";
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

            @Override
            public String toString() {
                return format("WebModule%s", Iterables.toString(modules));
            }
        };
    }

    public static WebModule combine(final WebModule ... modules) {
        return combine(ImmutableList.copyOf(modules));
    }
}
