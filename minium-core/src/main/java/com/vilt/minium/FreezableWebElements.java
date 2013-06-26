package com.vilt.minium;

public interface FreezableWebElements<T extends CoreWebElements<T>> extends WebElements {
	public T freeze();
}
