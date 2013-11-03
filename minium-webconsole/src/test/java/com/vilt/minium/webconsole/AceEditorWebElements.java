package com.vilt.minium.webconsole;

import com.vilt.minium.JQueryResources;
import com.vilt.minium.WebElements;

@JQueryResources("minium/aceEditor.js")
public interface AceEditorWebElements extends WebElements {

    public void writeCode(String code);
    public void writeCode(String code, boolean selected);
    public void focus();
}
