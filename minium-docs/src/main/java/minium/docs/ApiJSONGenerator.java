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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;

public class ApiJSONGenerator extends ApiGenerator {

    private static final String PATTERN_TO_REMOVE = "evaluates <code>";

    private List<ApiElement> elements = new ArrayList<ApiElement>();

    public ApiJSONGenerator(RootDoc root, Writer writer) {
        super(root, writer);
        this.classes = new HashSet<String>();
        elements = new ArrayList<ApiElement>();
    }

    public void addClass(Class<?>... classes) {
        Set<String> classesTmp = FluentIterable.from(ImmutableSet.copyOf(classes)).transform(new Function<Class<?>, String>() {
            @Override
            public String apply(Class<?> clazz) {
                return clazz.getName().replace("$", ".");
            }
        }).toSet();

        this.classes.addAll(classesTmp);
    }

    @Override
    public void print() throws IOException {
        Map<String, ClassDoc> classDocs = getAllClassDocs();
        ApiElement element = new ApiElement();
        for (String className : classes) {
            ClassDoc classDoc = classDocs.get(className);
            if (classDoc == null)
                continue;

            SortedSet<MethodDoc> methods = Sets.newTreeSet(new MethodComparator());

            // Iterate methods and sort them
            for (MethodDoc method : classDoc.methods(false)) {
                methods.add(method);
            }

            String type = StringUtils.substringAfterLast("." + classDoc.name(), ".");
            for (MethodDoc method : methods) {
                element = printMethod(method);
                element.setType(type);
                elements.add(element);
            }
        }

        String writeListToJsonArray = writeListToJsonArray();
        writer.append(writeListToJsonArray);
    }

    protected ApiElement printMethod(MethodDoc method) throws IOException {
        ApiElement element = new ApiElement();
        element.setCaption(String.format("%s(%s)", method.name(), parameters(method.parameters())));
        element.setContent(String.format("%s(%s)", method.name(), placeholders(method.parameters())));
        StringBuilder sb = new StringBuilder();

        String comment = !method.commentText().isEmpty() ? removeImagesTags(method.commentText().replaceAll("[\\n]+", "\n")) + "\n" : "";
        sb.append(comment);

        if (method.paramTags().length > 0) {
            sb.append("<table>");
            sb.append("<tr><th>Parameter</th> <th>Description</th></tr>");
            for (ParamTag param : method.paramTags()) {
                sb.append(String.format("<tr> <th> %s </th> <th> %s </th></tr>", param.parameterName(), asMarkdown(param.parameterComment())));
            }
            sb.append("</table>");
        }

        for (Tag t : method.tags("return")) {
            String text = t.text();
            if (text != null) {
                sb.append(String.format("-<strong>returns:</strong> %s \n", asMarkdown(text)));
            }
        }

        for (Tag t : method.tags("see")) {
            String text = t.text();
            if (text != null) {
                sb.append(String.format("-<strong>see:</strong>  %s \n", asMarkdown(text)));
            }
        }

        element.setDescription(sb.toString());
        return element;
    }

    protected String removeImagesTags(String comment) {
        int index = comment.indexOf(PATTERN_TO_REMOVE);

        // if pattern not found return the string
        return (index != -1) ? comment.substring(0, index) : comment;
    }

    protected String placeholders(Parameter[] parameters) {
        return Joiner.on(", ").join(Iterables.transform(ImmutableList.copyOf(parameters), new Function<Parameter, String>() {
            int i = 1;

            @Override
            public String apply(Parameter param) {
                return "${" + i++ + ":" + param.name() + "}";
            }
        }));
    }

    protected String writeListToJsonArray() throws IOException {

        final OutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(out, elements);

        final byte[] data = ((ByteArrayOutputStream) out).toByteArray();
        return new String(data);
    }
}
