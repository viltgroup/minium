package minium.visual.internal;

import minium.FreezableElements;
import minium.PositionElements;
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
import minium.visual.BasicVisualElements;
import minium.visual.VisualElements;
import minium.visual.internal.actions.VisualInteractionPerformer;

import org.sikuli.script.Screen;

import platypus.AbstractMixinInitializer;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

public class VisualModules {

    public static VisualModule positionModule() {
        return new VisualModule() {

            @Override
            public void configure(VisualElementsFactory.Builder<?> builder) {
                builder
                    .implementingInterfaces(PositionElements.class)
                    .usingMixinConfigurer(new AbstractMixinInitializer() {
                        @Override
                        protected void initialize() {
                            implement(PositionElements.class).with(new DefaultPositionVisualElements<VisualElements>());
                        }
                    });
            }
        };
    }

    public static VisualModule defaultModule(final Screen screen, final Class<?> intf) {
        Preconditions.checkArgument(intf == null || intf.isInterface());
        InteractionPerformer performer = new VisualInteractionPerformer();
        return combine(baseModule(screen, intf), positionModule(), interactableModule(performer));
    }

    public static VisualModule baseModule(final Screen screen, final Class<?> intf) {
        return new VisualModule() {
            @Override
            public void configure(VisualElementsFactory.Builder<?> builder) {
                if (intf != null) builder.implementingInterfaces(intf);
                builder
                    .usingScreen(screen)
                    .usingMixinConfigurer(new AbstractMixinInitializer() {
                        @Override
                        protected void initialize() {
                            implement(BasicVisualElements.class).with(new DefaultBasicVisualElements<VisualElements>());
                            implement(FreezableElements.class).with(new FreezableVisualElements<VisualElements>());
                        }
                    });
            }
        };
    };

    public static VisualModule interactableModule(final InteractionPerformer performer) {
        Preconditions.checkNotNull(performer);
        final Configuration configuration = new DefaultConfiguration();
        return new VisualModule() {
            @Override
            public void configure(VisualElementsFactory.Builder<?> builder) {
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

    public static VisualModule debugModule(final DebugInteractionPerformer performer) {
        Preconditions.checkNotNull(performer);
        return new VisualModule() {
            @Override
            public void configure(VisualElementsFactory.Builder<?> builder) {
                builder
                .implementingInterfaces(DebugInteractable.class)
                .usingMixinConfigurer(new AbstractMixinInitializer() {

                    @Override
                    protected void initialize() {
                        DebugInteractable interactable = new DefaultDebugInteractable(performer);
                        implement(DebugInteractable.class).with(interactable);
                    }
                });
            }
        };
    };

    public static VisualModule combine(final Iterable<? extends VisualModule> modules) {
        return new VisualModule() {
            @Override
            public void configure(VisualElementsFactory.Builder<?> builder) {
                for (VisualModule module : modules) {
                    module.configure(builder);
                }
            }
        };
    }

    public static VisualModule combine(final VisualModule ... modules) {
        return combine(ImmutableList.copyOf(modules));
    }
}
