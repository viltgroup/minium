package com.vilt.minium;

import com.vilt.minium.impl.StringJavascriptFunction;

public class JavascriptFunctions {
    
    public static JavascriptFunction parse(String code) {
        return new StringJavascriptFunction(code);
    }
}

