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
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;

public class ApiJSONGenerator {

    private static final Pattern INLINE_REGEX = Pattern.compile("\\{\\@(?:code|link) (.*?)\\}");

    private static final class MethodComparator implements Comparator<MethodDoc> {
        @Override
        public int compare(MethodDoc o1, MethodDoc o2) {
            return o1.name().compareTo(o2.name());
        }
    }

    private static final class HtmlCommentsNodeVisitor implements NodeVisitor {
        private List<Node> htmlCommentNodes = Lists.newArrayList();

        @Override
        public void tail(Node node, int depth) {
            // do nothing
        }

        @Override
        public void head(Node node, int depth) {
            if (node.nodeName().equals("#comment"))
                htmlCommentNodes.add(node);
        }

        public List<Node> getHtmlCommentNodes() {
            return htmlCommentNodes;
        }
    }

    private final RootDoc root;
    private final Writer writer;
    private final Set<String> classes;
    private List<ApiElement> elements = new ArrayList<ApiElement>();

    public ApiJSONGenerator(RootDoc root, Writer writer) {
        this.root = root;
        this.writer = writer;
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

    protected Map<String, ClassDoc> getAllClassDocs() {
        Set<ClassDoc> classDocs = FluentIterable.from(ImmutableList.copyOf(root.classes())).transformAndConcat(new Function<ClassDoc, Iterable<ClassDoc>>() {
            @Override
            public Iterable<ClassDoc> apply(ClassDoc classDoc) {
                return ImmutableSet.<ClassDoc> builder().add(classDoc).add(classDoc.innerClasses()).build();
            }
        }).toSet();
        return FluentIterable.from(classDocs).uniqueIndex(new Function<ClassDoc, String>() {
            @Override
            public String apply(ClassDoc classDoc) {
                return classDoc.qualifiedName();
            }
        });
    }

    protected ApiElement printMethod(MethodDoc method) throws IOException {
        ApiElement element = new ApiElement();
        element.setCaption(String.format("%s(%s)", method.name(), parameters(method.parameters())));
        element.setContent(String.format("%s(%s)", method.name(), placeholders(method.parameters())));
        StringBuilder sb = new StringBuilder();

        String comment = !method.commentText().isEmpty() ? method.commentText() + "\n" : "";
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
                sb.append(String.format("- <strong>see:</strong>  %s \n", asMarkdown(text)));
            }
        }

        element.setDescription(sb.toString());
        return element;
    }

    protected String asMarkdown(String text) {
        Document doc = Jsoup.parseBodyFragment(text);
        removeHtmlComments(doc);
        replaceJavadocCodeBlock(doc);
        String html = doc.getElementsByTag("body").html();
        return replaceInline(html);
    }

    protected String replaceInline(String html) {
        Matcher matcher = INLINE_REGEX.matcher(html);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "`$1`");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    protected void replaceJavadocCodeBlock(Document doc) {
        Elements elements = doc.getElementsByTag("pre");
        for (Element element : elements) {
            String html = element.html().trim();
            if (html.startsWith("{@code") && html.endsWith("}")) {
                html = html.substring("{@code".length(), html.length() - 1 - "}".length());
                element.html(StringEscapeUtils.escapeHtml4(html));
            }
        }
    }

    protected void removeHtmlComments(Document doc) {
        HtmlCommentsNodeVisitor htmlCommentsNodeVisitor = new HtmlCommentsNodeVisitor();
        doc.traverse(htmlCommentsNodeVisitor);
        for (Node htmlCommentNode : htmlCommentsNodeVisitor.getHtmlCommentNodes()) {
            htmlCommentNode.remove();
        }
    }

    protected String getUrl(String link) {
        Pattern p = Pattern.compile("href=\"(.*?)\"");
        Matcher m = p.matcher(link);
        String url = null;
        if (m.find()) {
            url = m.group(1);
        }
        return url;
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

    protected String parameters(Parameter[] parameters) {
        return Joiner.on(", ").join(Iterables.transform(ImmutableList.copyOf(parameters), new Function<Parameter, String>() {
            @Override
            public String apply(Parameter param) {
                return param.name();
            }
        }));
    }

    public String writeListToJsonArray() throws IOException {

        final OutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(out, elements);

        final byte[] data = ((ByteArrayOutputStream) out).toByteArray();
        return new String(data);
    }
}
