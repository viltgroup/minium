package com.vilt.minium.jquery.internal;

import com.vilt.minium.WebElements;
import com.vilt.minium.jquery.JQueryResources;

@JQueryResources("minium/js/new-window.js")
public interface NewWindowWebElements<T extends WebElements<T>> extends WebElements<T> {

	public T createHiddenAnchor(String url, String target, String settings);

	public void removeHiddenAnchor();
}
