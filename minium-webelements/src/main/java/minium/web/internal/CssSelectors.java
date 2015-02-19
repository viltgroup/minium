package minium.web.internal;

import static java.lang.String.format;

import java.util.regex.Pattern;

public class CssSelectors {

    // based on http://kjvarga.blogspot.pt/2009/06/jquery-plugin-to-escape-css-selector.html
    private static final Pattern PATTERN = Pattern.compile("('|:|\"|!|;|,|\\.|\\*|\\+|\\||\\[|\\]|\\(|\\)|\\/|\\^|\\$)");

    public static String attr(String name, String val) {
        return format("[%s=%s]", name, escape(val));
    }

    private static String escape(String val) {
        return PATTERN.matcher(val).replaceAll("\\\\$1");
    }

    public static String className(String name) {
        return format(".%s", escape(name));
    }

    public static String tagName(String name) {
        return escape(name);
    }
}
