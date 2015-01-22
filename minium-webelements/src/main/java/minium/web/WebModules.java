package minium.web;

import io.platypus.AbstractMixinInitializer;
import minium.actions.Configuration;
import minium.actions.HasConfiguration;
import minium.actions.Interactable;
import minium.actions.InteractionPerformer;
import minium.internal.LocatableElements;
import minium.internal.actions.DefaultConfiguration;
import minium.internal.actions.DefaultInteractable;
import minium.web.WebElementsFactory.Builder;
import minium.web.internal.actions.WebInteractionPerformer;

import org.openqa.selenium.WebDriver;

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
                        DefaultWebElements.class,
                        LocatableElements.class
                );
            }
        };
        InteractionPerformer performer = new WebInteractionPerformer();
        return combine(baseModule, positionModule(), conditionalModule(), interactableModule(performer));
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
                        Interactable interactable = new DefaultInteractable(performer);
                        implement(HasConfiguration.class).with(new HasConfiguration.Impl(configuration));
                        implement(Interactable.class).with(interactable);
                    }
                });
            }
        };
    };

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
