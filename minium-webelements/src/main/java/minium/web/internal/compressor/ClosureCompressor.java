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

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.javascript.jscomp.CommandLineRunner;
import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.SourceFile;

public class ClosureCompressor implements Compressor {

    @Override
    public String compress(ClassLoader classLoader, Collection<String> jsResources) throws IOException {
        Compiler compiler = new Compiler();
        compiler.disableThreads();
        Collection<SourceFile> sourceFiles = Collections2.transform(jsResources, ResourceFunctions.classpathFileToSourceFileFunction(classLoader));

        CompilerOptions options = new CompilerOptions();
        CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);
        compiler.compile(CommandLineRunner.getDefaultExterns(), Lists.newArrayList(sourceFiles), options);
        return compiler.toSource();
    }
}
