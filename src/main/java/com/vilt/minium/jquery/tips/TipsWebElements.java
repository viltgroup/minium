package com.vilt.minium.jquery.tips;

import java.util.concurrent.TimeUnit;

import com.vilt.minium.WebElements;
import com.vilt.minium.jquery.JQueryResources;

@JQueryResources(
		value  = { "minium/js/jquery.qtip.min.js", "minium/js/jquery.ba-dotimeout.min.js", "minium/js/tips.js" }, 
		styles = { "minium/css/jquery.qtip.min.css", "minium/css/tips.css" })
public interface TipsWebElements extends WebElements {

	void showTip(String tip, long time, TimeUnit timeUnit);

}
