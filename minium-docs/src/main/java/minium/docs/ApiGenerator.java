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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;

public abstract class ApiGenerator {

    private static final Pattern INLINE_REGEX = Pattern.compile("\\{\\@(?:code|link) (.*?)\\}");

    protected static final class MethodComparator implements Comparator<MethodDoc> {
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

    protected final RootDoc root;
    protected final Writer writer;
    protected Set<String> classes;

    public ApiGenerator(RootDoc root, Writer writer) {
        this.root = root;
        this.writer = writer;
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

    protected String parameters(Parameter[] parameters) {
        return Joiner.on(", ").join(Iterables.transform(ImmutableList.copyOf(parameters), new Function<Parameter, String>() {
            @Override
            public String apply(Parameter param) {
                return param.name();
            }
        }));
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

    public abstract void print() throws IOException;
}
