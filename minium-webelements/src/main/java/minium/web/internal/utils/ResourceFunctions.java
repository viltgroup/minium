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
package minium.web.internal.utils;

import java.io.IOException;
import java.net.URL;

import minium.web.internal.ResourceException;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.io.Resources;

public class ResourceFunctions {

    private static class ClasspathFileToStringFunction implements Function<String, String> {

        private final ClassLoader classLoader;

        public ClasspathFileToStringFunction(ClassLoader classLoader) {
            this.classLoader = classLoader;
        }

        @Override
        public String apply(String filePath) {
            try {
                URL resource = classLoader.getResource(filePath);
                return Resources.toString(resource, Charsets.UTF_8);
            } catch (IOException e) {
                throw new ResourceException(e);
            }
        }
    }

    public static Function<String, String> classpathFileToStringFunction(ClassLoader classLoader) {
        return new ClasspathFileToStringFunction(classLoader);
    }
}