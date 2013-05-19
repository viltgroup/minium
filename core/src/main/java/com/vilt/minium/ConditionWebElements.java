package com.vilt.minium;

@JQueryResources("minium/js/condition.js")
public interface ConditionWebElements<T extends WebElements> extends WebElements {

	public T whenNotEmpty(String selector);
	
	public T whenNotEmpty(T elems);
	
	public T whenEmpty(String elems);

	public T whenEmpty(T elems);

	public T notEmptyThen(String selector);
	
	public T notEmptyThen(T elems);
	
	public T emptyThen(String elems);
	
	public T emptyThen(T elems);
}
