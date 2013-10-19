package com.vilt.minium.webconsole.controller;

public class CanceledException extends RuntimeException {

    private static final long serialVersionUID = -712490542066321031L;

    public CanceledException(String msg) {
        super(msg);
    }
}
