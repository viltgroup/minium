package com.vilt.minium.tips;

import java.util.concurrent.TimeUnit;

import com.vilt.minium.JQueryResources;
import com.vilt.minium.WebElements;

@JQueryResources(
		value  = { "minium/js/jquery.qtip.min.js", "minium/js/jquery.ba-dotimeout.min.js", "minium/js/tips.js" }, 
		styles = { "minium/css/jquery.qtip.min.css", "minium/css/tips.css" })
public interface TipsWebElements extends WebElements {

	void showTip(String tip, long time, TimeUnit timeUnit);

}
