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
package minium.cucumber;

import java.io.IOException;
import java.io.InputStream;

import cucumber.runtime.io.Resource;

public class StringResource implements Resource {

    private String path;
    private InputStream inputStream;

    public StringResource(String path, InputStream inputStream) {
        super();
        this.path = path;
        this.inputStream = inputStream;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getAbsolutePath() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }

    @Override
    public String getClassName(String paramString) {
        // TODO Auto-generated method stub
        return null;
    }

}
