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
package minium.docs;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.SortedSet;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;

public class ApiMarkdownGenerator extends ApiGenerator {

    public ApiMarkdownGenerator(RootDoc root, Writer writer, Class<?>... classes) {
        super(root, writer);
        this.classes = FluentIterable.from(ImmutableSet.copyOf(classes)).transform(new Function<Class<?>, String>() {
            @Override
            public String apply(Class<?> clazz) {
                return clazz.getName().replace("$", ".");
            }
        }).toSet();
    }

    @Override
    public void print() throws IOException {
        Map<String, ClassDoc> classDocs = getAllClassDocs();

        for (String className : classes) {
            ClassDoc classDoc = classDocs.get(className);
            if (classDoc == null)
                continue;

            SortedSet<MethodDoc> methods = Sets.newTreeSet(new MethodComparator());

            // Iterate methods and sort them
            for (MethodDoc method : classDoc.methods(false)) {
                methods.add(method);
            }

            appendNewline("# %s", StringUtils.substringAfterLast("." + classDoc.name(), "."));
            appendNewline();
            appendNewline(asMarkdown(classDoc.commentText()));
            appendNewline();

            for (MethodDoc method : methods) {
                printMarkDown(method);
            }
        }
    }

    protected void printMarkDown(MethodDoc method) throws IOException {
        appendNewline("## `.%s(%s)`", method.name(), parameters(method.parameters()));
        appendNewline();

        appendNewline(asMarkdown(method.commentText()));
        appendNewline();

        if (method.paramTags().length > 0) {
            appendNewline("Parameter | Description");
            appendNewline("--------- | -----------");
            for (ParamTag param : method.paramTags()) {
                appendNewline("%s | %s", param.parameterName(), asMarkdown(param.parameterComment()));
            }
            appendNewline();
        }

        for (Tag t : method.tags("return")) {
            String text = t.text();
            if (text != null) {
                appendNewline("- **returns:** %s", asMarkdown(text));
            }
        }

        for (Tag t : method.tags("see")) {
            String text = t.text();
            if (text != null) {
                appendNewline("- **see:** %s", asMarkdown(text));
            }
        }

        appendNewline();
    }

    protected ApiMarkdownGenerator appendNewline() throws IOException {
        writer.append("\n");
        return this;
    }

    protected ApiMarkdownGenerator appendNewline(String format, Object... objs) throws IOException {
        writer.append(String.format(format, objs));
        writer.append("\n");
        return this;
    }
}
