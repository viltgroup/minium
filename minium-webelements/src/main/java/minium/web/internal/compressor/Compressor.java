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
package minium.web.internal.compressor;

import java.io.IOException;
import java.util.Collection;

import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;

public interface Compressor {

    static class NullCompressor implements Compressor {
        @Override
        public String compress(ClassLoader classLoader, Collection<String> jsResources) {
            return Joiner.on("\n\n").join(Collections2.transform(jsResources, ResourceFunctions.classpathFileToStringFunction(classLoader)));
        }
    }

    public static final Compressor NULL = new NullCompressor();

    String compress(ClassLoader classLoader, Collection<String> jsResources) throws IOException;

}
