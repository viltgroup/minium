/*
 * Copyright (C) 2015 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minium.web.internal;

import static java.lang.String.format;
import minium.actions.Configuration;
import minium.actions.HasConfiguration;
import minium.actions.HasInteractionListeners;
import minium.actions.Interactable;
import minium.actions.KeyboardInteractable;
import minium.actions.MouseInteractable;
import minium.actions.WaitInteractable;
import minium.actions.debug.DebugInteractable;
import minium.actions.internal.DefaultConfiguration;
import minium.actions.internal.DefaultHasInteractionListeners;
import minium.actions.internal.DefaultWaitInteractable;
import minium.internal.LocatableElements;
import minium.web.ConditionalWebElements;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.PositionWebElements;
import minium.web.WebElements;
import minium.web.actions.HasAlert;
import minium.web.actions.WebInteractable;
import minium.web.internal.WebElementsFactory.Builder;
import minium.web.internal.actions.DefaultDebugInteractable;
import minium.web.internal.actions.DefaultHasAlert;
import minium.web.internal.actions.DefaultKeyboardInteractable;
import minium.web.internal.actions.DefaultMouseInteractable;
import minium.web.internal.actions.DefaultWebInteractable;

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
        return combine(baseModule(wd), positionModule(), conditionalModule(), interactableModule());
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
                        "minium/web/internal/lib/json2.min.js",
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

    public static WebModule interactableModule() {
        final Configuration configuration = new DefaultConfiguration();

        return new WebModule() {
            @Override
            public void configure(Builder<?> builder) {
                builder
                .implementingInterfaces(HasInteractionListeners.class, HasConfiguration.class, WaitInteractable.class, MouseInteractable.class, KeyboardInteractable.class, WebInteractable.class)
                .usingMixinConfigurer(new AbstractMixinInitializer() {

                    @Override
                    protected void initialize() {
                        implement(HasInteractionListeners.class).with(new DefaultHasInteractionListeners<Interactable<?>>());
                        implement(HasConfiguration.class).with(new HasConfiguration.Impl(configuration));
                        implement(WaitInteractable.class).with(new DefaultWaitInteractable<Interactable<?>>());
                        implement(MouseInteractable.class).with(new DefaultMouseInteractable<Interactable<?>>());
                        implement(KeyboardInteractable.class).with(new DefaultKeyboardInteractable<Interactable<?>>());
                        implement(WebInteractable.class).with(new DefaultWebInteractable<Interactable<?>>());
                    }
                });
            }

            @Override
            public String toString() {
                return "WebModule[interactable]";
            }
        };
    };

    public static WebModule debugModule() {
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
                        implement(DebugInteractable.class).with(new DefaultDebugInteractable<Interactable<?>>());
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
