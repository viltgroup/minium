package minium;

public interface BasicElements<T extends Elements> extends Elements {

    public T eq(int index);

    public T first();

    public T last();

    public int size();
}
