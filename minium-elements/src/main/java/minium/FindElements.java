package minium;

public interface FindElements<T extends Elements> extends Elements {
    public T find(String selector);
}
