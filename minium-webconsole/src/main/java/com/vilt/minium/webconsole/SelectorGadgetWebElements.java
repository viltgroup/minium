package com.vilt.minium.webconsole;

import com.vilt.minium.JQueryResources;
import com.vilt.minium.WebElements;

@JQueryResources(
        value = { "rhino/selectorgadget/selectorgadget_combined.js", "rhino/selectorgadget/selectorgadget_minium.js" },
        styles = "rhino/selectorgadget/selectorgadget_combined.css"
)
public interface SelectorGadgetWebElements extends WebElements {
    public void activateSelectorGadget();
    public String getCssSelector();
}
