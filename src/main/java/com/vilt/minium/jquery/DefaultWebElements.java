package com.vilt.minium.jquery;

import com.vilt.minium.TargetLocatorWebElements;
import com.vilt.minium.WaitWebElements;

/**
 * 
 * @author Rui
 */
public interface DefaultWebElements extends
	TargetLocatorWebElements<DefaultWebElements>, 
	JQueryWebElements<DefaultWebElements>, 
	PositionWebElements<DefaultWebElements>, 
	FiltersWebElements<DefaultWebElements>,
	WaitWebElements<DefaultWebElements> {
}
