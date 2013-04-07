package com.vilt.minium;

/**
 * The Interface CoreWebElements.
 *
 * @param <T> recursive {@link WebElements} type
 * @author rui.figueira
 */
public interface CoreWebElements<T extends CoreWebElements<T>> extends 
	TargetLocatorWebElements<T>, 
	JQueryWebElements<T>, 
	WaitWebElements<T>,
	FiltersWebElements<T>,
	ConditionWebElements<T>,
	WebElementsDriverProvider<T> {

}
