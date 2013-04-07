package com.vilt.minium;

@JQueryResources("minium/js/condition.js")
public interface ConditionWebElements<T extends WebElements> extends WebElements {

	public T when(String selector);
	
	public T when(T elems);
	
	public T unless(String elems);

	public T unless(T elems);
}
