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
package minium.web.internal;

import java.util.Collections;

import minium.web.DocumentWebDriver;
import minium.web.WebElements;

public class DefaultRoot<T extends WebElements> extends BaseDocumentRoots<T> {

    private final DocumentWebDriver wd;

    public DefaultRoot(DocumentWebDriver wd) {
        this.wd = wd;
    }

    @Override
    public Iterable<DocumentWebDriver> candidateDocumentDrivers() {
        return Collections.singleton(wd);
    }

    @Override
    public String toString() {
        return "";
    }

}
