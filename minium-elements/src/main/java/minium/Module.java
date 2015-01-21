package minium;

public interface Module<B extends ElementsFactory.Builder<?>> {
    public void configure(B builder);
}
