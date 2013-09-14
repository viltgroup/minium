package com.vilt.minium.impl;

import com.vilt.minium.JavascriptFunction;

public class StringJavascriptFunction implements JavascriptFunction {
    
    private String code;

    public StringJavascriptFunction(String code) {
        this.code = code;
    }
    
    @Override
    public String toString() {
        return code;
    }
}

