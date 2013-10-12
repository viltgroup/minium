package com.vilt.minium.webconsole.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ConsoleControllerHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> handleException(Exception e) {
        return new ResponseEntity<Exception>(e,  HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
