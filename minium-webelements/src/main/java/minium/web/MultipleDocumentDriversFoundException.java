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

public class MultipleDocumentDriversFoundException extends ElementsException {

    private static final long serialVersionUID = -4500350278311385767L;

    public MultipleDocumentDriversFoundException() {
        super();
    }

    public MultipleDocumentDriversFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MultipleDocumentDriversFoundException(String message) {
        super(message);
    }

    public MultipleDocumentDriversFoundException(Throwable cause) {
        super(cause);
    }

}
