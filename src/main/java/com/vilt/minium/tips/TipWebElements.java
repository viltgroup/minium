package com.vilt.minium.tips;

import java.util.concurrent.TimeUnit;

import com.vilt.minium.JQueryResources;
import com.vilt.minium.WebElements;

/**
 * The Interface TipsWebElements.
 */
@JQueryResources(
		value  = { "minium/js/jquery.qtip.min.js", "minium/js/jquery.ba-dotimeout.min.js", "minium/js/tip.js" }, 
		styles = { "minium/css/jquery.qtip.min.css", "minium/css/tip.css" })
public interface TipWebElements extends WebElements {

	/**
	 * Show tip.
	 *
	 * @param tip the tip
	 * @param time the time
	 * @param timeUnit the time unit
	 */
	void showTip(String tip, long time, TimeUnit timeUnit);

}
