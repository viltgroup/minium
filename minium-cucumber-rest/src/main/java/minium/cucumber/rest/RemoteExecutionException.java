package minium.cucumber.rest;

import minium.cucumber.rest.dto.ExceptionDTO;

public class RemoteExecutionException extends RuntimeException {

    private static final long serialVersionUID = -2703495825099519539L;

    public RemoteExecutionException(ExceptionDTO exception) {
        super(exception.getMessage());
        this.setStackTrace(exception.getStacktrace());
    }
}
