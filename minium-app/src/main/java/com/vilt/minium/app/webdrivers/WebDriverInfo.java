/*
 * Copyright (C) 2013 The Minium Authors
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
package com.vilt.minium.app.webdrivers;

public class WebDriverInfo {

    public enum Type {
        Chrome, Firefox, Safari, InternetExplorer, Opera, PhantomJS, Remote
    }

    private String varName;
    private Type type;
    private boolean remote;

    public WebDriverInfo(String varName, Type type) {
        this(varName, type, false);
    }

    public WebDriverInfo(String varName, Type type, boolean remote) {
        super();
        this.varName = varName;
        this.type = type;
        this.remote = remote;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }

}
