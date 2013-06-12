package com.vilt.minium;

public interface WebElementsFinder<T extends WebElements> {
	public T find(String selector);
	public T find(T expr);
}
