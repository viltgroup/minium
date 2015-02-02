package minium;

public interface ElementsFactory<T extends Elements> extends AsIs {

    public abstract T createRoot();

    public interface Builder<EF extends ElementsFactory<?>> {
        public abstract EF build();
    }
}
