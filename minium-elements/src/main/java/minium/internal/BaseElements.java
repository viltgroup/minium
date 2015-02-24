package minium.internal;

import minium.Elements;

import com.google.common.reflect.TypeToken;

public abstract class BaseElements<T extends Elements> extends Chainable<T> implements Elements {

    protected InternalElementsFactory<T> internalFactory() {
        TypeToken<InternalElementsFactory<T>> factoryTypeToken = typeTokenFor(InternalElementsFactory.class);
        return this.as(HasElementsFactory.class).factory().as(factoryTypeToken);
    }

    protected Elements parent() {
        return this.is(HasParent.class) ? this.as(HasParent.class).parent() : null;
    }

    protected Elements root() {
        Elements curr = myself();
        while (curr.is(HasParent.class)) {
            curr = curr.as(HasParent.class).parent();
        }
        return curr;
    }
}