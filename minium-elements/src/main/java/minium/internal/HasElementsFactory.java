package minium.internal;

import minium.ElementsFactory;


public interface HasElementsFactory {

    public ElementsFactory factory();

    public static class Impl implements HasElementsFactory {

        protected final ElementsFactory factory;

        public Impl(ElementsFactory factory) {
            this.factory = factory;
        }

        @Override
        public ElementsFactory factory() {
            return factory;
        }

    }

}
