package com.vilt.minium.webconsole;

import com.vilt.minium.JQueryResources;
import com.vilt.minium.WebElements;

@JQueryResources(
        value = { "minium/selectorgadget/selectorgadget_combined.js", "minium/selectorgadget/selectorgadget_minium.js" },
        styles = "minium/selectorgadget/selectorgadget_combined.css"
)
public interface SelectorGadgetWebElements extends WebElements {
    public void activateSelectorGadget();
    public void deactivateSelectorGadget();
    public String getCssSelector();
}
