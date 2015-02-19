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
package com.vilt.minium.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.javascript.jscomp.CommandLineRunner;
import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.SourceFile;
import com.vilt.minium.impl.JQueryInvoker.Compressor;

public class ClosureCompressor implements Compressor {

    private final class ClasspathFileToSourceFileFunction implements Function<String, SourceFile> {
        @Override
        public SourceFile apply(String input) {
            try {
                return SourceFile.fromReader(input, getClasspathFileReader(input));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String compress(ClassLoader classLoader, Collection<String> jsResources) {
        try {
            Collection<SourceFile> sourceFiles = Collections2.transform(jsResources, new ClasspathFileToSourceFileFunction());

            Compiler compiler = new Compiler();
            compiler.disableThreads();

            CompilerOptions options = new CompilerOptions();
            CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);
            compiler.compile(CommandLineRunner.getDefaultExterns(), Lists.newArrayList(sourceFiles), options);
            return compiler.toSource();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Reader getClasspathFileReader(String filename) {
        InputStream is = JQueryInvoker.class.getClassLoader().getResourceAsStream(filename);
        Preconditions.checkNotNull(is, "File %s not found in classpath", filename);
        return new InputStreamReader(is, Charsets.UTF_8);
    }
}
