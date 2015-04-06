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
package minium.actions;

import static java.lang.String.format;
import minium.Elements;
import minium.ElementsException;

import com.google.common.base.Predicate;

public class TimeoutException extends ElementsException {

    private static final long serialVersionUID = 2337656934950527267L;

    protected Predicate<?> predicate;

    public TimeoutException() {
        super();
    }

    public TimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeoutException(String message) {
        super(message);
    }

    public TimeoutException(Throwable cause) {
        super(cause);
    }

    public TimeoutException(Predicate<?> predicate, Elements elems, int failedAttempts) {
        super(elems, format("Timeout on %s after %d attempts for predicate %s", elems, failedAttempts, predicate), null);
        this.predicate = predicate;
    }

    public Predicate<?> getPredicate() {
        return predicate;
    }
}
