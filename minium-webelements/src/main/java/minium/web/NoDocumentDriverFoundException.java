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
package minium.web;

import minium.ElementsException;

public class NoDocumentDriverFoundException extends ElementsException {

    private static final long serialVersionUID = 7011052529976809314L;

    public NoDocumentDriverFoundException() {
        super();
    }

    public NoDocumentDriverFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoDocumentDriverFoundException(String message) {
        super(message);
    }

    public NoDocumentDriverFoundException(Throwable cause) {
        super(cause);
    }

}
