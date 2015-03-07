/*
 * Copyright (C) 2015 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minium.cucumber.rest.dto;

public abstract class ExecutionResult {

    public static enum Status {
        SUCCEDED,
        FAILED
    }

    private Status status = Status.SUCCEDED;
    protected ExceptionDTO exception;

    public ExecutionResult() {
        status = Status.SUCCEDED;
    }

    public ExecutionResult(Throwable e) {
        status = Status.FAILED;
        exception = new ExceptionDTO(e);
    }

    public Status getStatus() {
        return status == null ? (exception != null ? Status.FAILED : Status.SUCCEDED) : status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ExceptionDTO getException() {
        return exception;
    }

    public void setException(ExceptionDTO exception) {
        this.exception = exception;
    }
}