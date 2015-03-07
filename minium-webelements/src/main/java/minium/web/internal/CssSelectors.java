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
