package minium.internal;

import minium.Elements;

import com.google.common.base.Preconditions;

public interface HasParent {

    public Elements parent();

    public static class Impl implements HasParent {

        private final Elements parent;

        public Impl(Elements parent) {
            this.parent = Preconditions.checkNotNull(parent);
        }

        @Override
        public Elements parent() {
            return parent;
        }
    }

}
