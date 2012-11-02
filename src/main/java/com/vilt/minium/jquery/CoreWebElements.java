package com.vilt.minium.jquery;

import com.vilt.minium.TargetLocatorWebElements;
import com.vilt.minium.WaitWebElements;

public interface CoreWebElements<T extends CoreWebElements<T>> extends 
	TargetLocatorWebElements<T>, 
	JQueryWebElements<T>, 
	WaitWebElements<T> {

}
