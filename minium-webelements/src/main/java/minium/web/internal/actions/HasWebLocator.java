package minium.web.internal.actions;

import java.util.Set;

import minium.internal.HasElementsFactory;
import minium.web.WebElements;
import minium.web.internal.WebLocator;
import platypus.Mixin;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeToken;

public interface HasWebLocator<T extends WebElements> {

    public WebLocator<T> locator();

    public static class Impl<T  extends WebElements> extends Mixin.Impl implements HasWebLocator<T> {

        private final Class<T> intf;
        private final Set<Class<?>> otherIntfs;

        public Impl(Class<T> intf, Set<Class<?>> otherIntfs) {
            this.intf = intf;
            this.otherIntfs = otherIntfs == null ? ImmutableSet.<Class<?>>of() : otherIntfs;
        }

        @Override
        public WebLocator<T> locator() {
            @SuppressWarnings("serial")
            TypeToken<T> typeToken = new TypeToken<T>(HasWebLocator.Impl.class) { };
            T root = this.as(HasElementsFactory.class).factory().createRoot().as(typeToken);
            return new WebLocator<T>(root, intf, otherIntfs.toArray(new Class<?>[otherIntfs.size()]));
        }
    }
}
