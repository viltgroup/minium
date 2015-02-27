package minium.web;


public interface TargetLocatorWebElements<T extends WebElements> extends WebElements {

    public T frames();

    public T windows();

    public T documentRoots();

}