package minium;

public interface ElementsFactory extends AsIs {

    public interface Builder<T extends ElementsFactory> {
        public abstract T build();
    }
}
