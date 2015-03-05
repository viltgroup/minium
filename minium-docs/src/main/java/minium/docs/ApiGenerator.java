package minium.docs;

import java.io.IOException;
import java.io.Writer;
import java.util.Comparator;
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

public class ApiGenerator {

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
            if (node.nodeName().equals("#comment")) htmlCommentNodes.add(node);
        }

        public List<Node> getHtmlCommentNodes() {
            return htmlCommentNodes;
        }
    }

    private final RootDoc root;
    private final Writer writer;
    private final Set<String> classes;

    public ApiGenerator(RootDoc root, Writer writer, Class<?> ... classes) {
        this.root = root;
        this.writer = writer;
        this.classes = FluentIterable.from(ImmutableSet.copyOf(classes)).transform(new Function<Class<?>, String>() {
            @Override
            public String apply(Class<?> clazz) {
                return clazz.getName().replace("$", ".");
            }
        }).toSet();
    }

    public void print() throws IOException {
        Map<String, ClassDoc> classDocs = getAllClassDocs();

        for (String className : classes) {
            ClassDoc classDoc = classDocs.get(className);
            if (classDoc == null) continue;

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

    protected Map<String, ClassDoc> getAllClassDocs() {
        Set<ClassDoc> classDocs = FluentIterable.from(ImmutableList.copyOf(root.classes())).transformAndConcat(new Function<ClassDoc, Iterable<ClassDoc>>() {
            @Override
            public Iterable<ClassDoc> apply(ClassDoc classDoc) {
                return ImmutableSet.<ClassDoc>builder().add(classDoc).add(classDoc.innerClasses()).build();
            }
        }).toSet();
        return FluentIterable.from(classDocs).uniqueIndex(new Function<ClassDoc, String>() {
            @Override
            public String apply(ClassDoc classDoc) {
                return classDoc.qualifiedName();
            }
        });
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

    protected String asMarkdown(String text) {
        Document doc = Jsoup.parseBodyFragment(text);
        removeHtmlComments(doc);
        Elements elements = doc.getElementsByTag("pre");
        for (Element element : elements) {
            String html = element.html().trim();
            if (html.startsWith("{@code") && html.endsWith("}")) {
                html = html.substring("{@code".length(), html.length() - 1 - "}".length());
                element.html(StringEscapeUtils.escapeHtml4(html));
            }
        }
        return doc.getElementsByTag("body").html();
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

    protected ApiGenerator appendNewline() throws IOException {
        writer.append("\n");
        return this;
    }

    protected ApiGenerator appendNewline(String format, Object ... objs) throws IOException {
        writer.append(String.format(format, objs));
        writer.append("\n");
        return this;
    }
}
