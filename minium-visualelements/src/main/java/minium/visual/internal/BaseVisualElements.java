package minium.visual.internal;

import minium.internal.BaseElements;
import minium.internal.HasElementsFactory;
import minium.internal.InternalElementsFactory;
import minium.visual.VisualElements;

import org.sikuli.script.Screen;

import com.google.common.reflect.TypeToken;

public class BaseVisualElements<T extends VisualElements> extends BaseElements<T> {

    protected Screen screen() {
        return this.is(HasScreen.class) ? this.as(HasScreen.class).screen() : null;
    }

    protected VisualElementsFactory<T> factory() {
        TypeToken<VisualElementsFactory<T>> visualElementsFactoryTypeToken = typeTokenFor(VisualElementsFactory.class);
        return this.as(HasElementsFactory.class).factory().as(visualElementsFactoryTypeToken);
    }

    @Override
    protected InternalElementsFactory<T> internalFactory() {
        TypeToken<InternalElementsFactory<T>> internalElementsFactoryTypeToken = typeTokenFor(InternalElementsFactory.class);
        return this.as(HasElementsFactory.class).factory().as(internalElementsFactoryTypeToken);
    }

}