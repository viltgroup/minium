package com.vilt.minium.jquery;

import com.vilt.minium.TargetLocatorWebElements;
import com.vilt.minium.WaitWebElements;
import com.vilt.minium.jquery.debug.DebugWebElements;

/**
 * 
 * @author Rui
 */
public interface DefaultWebElements extends
	TargetLocatorWebElements<DefaultWebElements>, 
	JQueryWebElements<DefaultWebElements>, 
	PositionWebElements<DefaultWebElements>, 
	FiltersWebElements<DefaultWebElements>,
	WaitWebElements<DefaultWebElements>,
	DebugWebElements<DefaultWebElements> {
}
