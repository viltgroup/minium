package com.vilt.minium;


public interface CoreWebElements<T extends CoreWebElements<T>> extends 
	TargetLocatorWebElements<T>, 
	JQueryWebElements<T>, 
	WaitWebElements<T>,
	FiltersWebElements<T>,
	WebElementsDriverProvider<T> {

}
