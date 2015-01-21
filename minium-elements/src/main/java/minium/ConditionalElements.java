package minium;

public interface ConditionalElements<T extends Elements> extends Elements {

    public abstract T and(Elements elems);

    public abstract T or(Elements elems);

    public abstract T then(Elements elems);

    public abstract T when(Elements elems);

    public abstract T unless(Elements elems);

}