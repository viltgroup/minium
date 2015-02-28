package minium.web.internal.actions;

import static com.google.common.base.Throwables.getCausalChain;
import static com.google.common.collect.FluentIterable.from;

import java.util.Set;

import minium.actions.InteractionListener;
import minium.actions.internal.AfterFailInteractionEvent;
import minium.actions.internal.DefaultInteractionListener;
import minium.web.actions.OnExceptionInteractionListener;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;

public abstract class AbstractOnExceptionInteractionListener extends DefaultInteractionListener implements OnExceptionInteractionListener {

    private Set<Class<? extends Throwable>> exceptions;

    // by default, we retry
    private boolean retry = true;

    @SuppressWarnings("unchecked")
    public AbstractOnExceptionInteractionListener(Class<? extends Throwable> ... exceptions) {
        this.exceptions = ImmutableSet.copyOf(exceptions);
    }

    protected abstract boolean handle(AfterFailInteractionEvent event);

    @Override
    protected final void onAfterFailEvent(AfterFailInteractionEvent event) {
        boolean canHandleException = from(getCausalChain(event.getException())).anyMatch(new Predicate<Throwable>() {
            @Override
            public boolean apply(Throwable throwable) {
                return exceptions.contains(throwable.getClass());
            }
        });
        if (canHandleException) {
            boolean handle = handle(event);
            if (handle && retry) {
                event.setRetry(retry);
            }
        }
    }

    @Override
    public InteractionListener thenAbort() {
        retry = false;
        return this;
    }

    @Override
    public InteractionListener thenRetry() {
        retry = true;
        return this;
    }


}
