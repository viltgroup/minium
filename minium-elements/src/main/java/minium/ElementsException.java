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
package minium;

public class ElementsException extends RuntimeException {

    private static final long serialVersionUID = -7292984867182787890L;

    private Elements elems;

    public ElementsException() {
        super();
    }

    public ElementsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElementsException(String message) {
        super(message);
    }

    public ElementsException(Throwable cause) {
        super(cause);
    }

    public ElementsException(Elements elems, Throwable e) {
        super(e);
        this.elems = elems;
    }

    public ElementsException(Elements elems, String msg, Throwable e) {
        super(msg, e);
        this.elems = elems;
    }

    public Elements getElements() {
        return elems;
    }
}
