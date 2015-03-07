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
package minium.script.js;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.springframework.beans.factory.DisposableBean;

public interface JsEngine extends DisposableBean {

    public abstract <T> T runScript(File sourceFile) throws IOException;

    public abstract <T> T runScript(String path) throws IOException;

    public abstract <T> T runScript(Reader reader, String sourceName) throws IOException;

    public abstract <T> T eval(String expression, int line);

    public abstract boolean contains(String varName);

    public abstract Object get(String varName);

    public abstract <T> T get(String varName, Class<T> clazz);

    public abstract void put(String varName, Object object);

    public abstract void delete(String varName);

    public abstract void putJson(String varName, String json);

    public abstract StackTraceElement[] getExecutionStackTrace();

    public abstract void cancel();

    public abstract boolean isRunning();

    public abstract String toString(Object obj);

}