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
package com.vilt.minium;

/**
 * This interface provides useful jQuery extension methods for Minium {@link WebElements}, like
 * contained text filtering or attribute value filtering.
 *
 * @param <T> "self-type" for {@link WebElements}
 * @author rui.figueira
 */
@JQueryResources({ "minium/internal/js/jquery.minium-exts.js", "minium/internal/js/jquery.visibleText.js" })
public interface JQueryExtWebElements<T extends CoreWebElements<T>> extends WebElements {

    /**
     * <p>Filters elements that have a label with the given text. That means that
     * returned elements have the same <code>id</code> than the <code>for</code>
     * attribute of the <code>label</code> tag.</p>
     *
     * <p>Example:</p>
     *
     * <pre>wd.find(":text").withLabel("Username")</pre>
     *
     * evaluates <code>#username</code> in the following scenario:
     *
     * <pre>
     * {@code
     * <label for="username">Username</label>
     * <input type="text" id="username" />
     * }
     * </pre>
     *
     * @param label the label text
     * @return filtered {@link WebElements}
     */
    public T withLabel(final String label);

    /**
     * <p>Filters elements that have an attribute.
     *
     * <p>Example:</p>
     *
     * <pre>wd.find("img").withAttr("alt")</pre>
     *
     * evaluates <code>#image2</code> in the following scenario:
     *
     * <pre>
     * {@code
     * <img id="image1" src="image1.gif" />
     * <img id="image2" src="image2.gif" alt="Image 2" />
     * }
     * </pre>
     *
     * @param name the attribute name
     * @return filtered {@link WebElements}
     */
    public T withAttr(final String name);

    /**
     * <p>Filters elements that have an attribute with a given value.
     *
     * <p>Example:</p>
     *
     * <pre>wd.find("img").withAttr("alt", "Image 1")</pre>
     *
     * evaluates <code>#image1</code> in the following scenario:
     *
     * <pre>
     * {@code
     * <img id="image1" src="image1.gif" alt="Image 1" />
     * <img id="image2" src="image2.gif" alt="Image 2" />
     * }
     * </pre>
     *
     * @param name the attribute name
     * @param value the attribute value
     * @return filtered {@link WebElements}
     */
    public T withAttr(final String name, final String value);

    /**
     * <p>Filters elements that have a style property.
     *
     * <p>Example:</p>
     *
     * <pre>wd.find("img").withCss("display")</pre>
     *
     * evaluates <code>#image2</code> in the following scenario:
     *
     * <pre>
     * {@code
     * <img id="image1" src="image1.gif" />
     * <img id="image2" src="image2.gif" style="display: block" />
     * }
     * </pre>
     *
     * @param name the style property name
     * @return filtered {@link WebElements}
     */
    public T withCss(final String name);

    /**
     * <p>Filters elements that have a style property with a given value.
     *
     * <p>Example:</p>
     *
     * <pre>wd.find("img").withAttr("display", "block")</pre>
     *
     * evaluates <code>#image1</code> in the following scenario:
     *
     * <pre>
     * {@code
     * <img id="image1" src="image1.gif" style="display: block" />
     * <img id="image2" src="image2.gif" style="display: none" />
     * }
     * </pre>
     *
     * @param name the style property name
     * @param value the style property value
     * @return filtered {@link WebElements}
     */
    public T withCss(final String name, final String value);

    /**
     * <p>Filters elements that have a <code>value</code> attribute with a given value.
     * This is very useful in input elements.
     *
     * <p>Example:</p>
     *
     * <pre>wd.find(":button").withValue("Proceed")</pre>
     *
     * evaluates <code>#button</code> in the following scenario:
     *
     * <pre>
     * {@code
     * <input id="button" type="button" value="Proceed" />
     * }
     * </pre>
     *
     * @param value the value for attribute <code>value</code>
     * @return filtered {@link WebElements}
     */
    public T withValue(final String value);

    /**
     * <p>Filters elements that have a <code>name</code> attribute with a given value.
     * This is very useful in form input elements.
     *
     * <p>Example:</p>
     *
     * <pre>wd.find(":password").withName("pass")</pre>
     *
     * evaluates <code>#password</code> in the following scenario:
     *
     * <pre>
     * {@code
     * <input id="password" type="password" name="pass" />
     * }
     * </pre>
     *
     * @param name the value for attribute <code>name</code>
     * @return filtered {@link WebElements}
     */
    public T withName(final String name);

    /**
     * <p>Filters elements which have a specified text.</p>
     *
     * <p>Example:</p>
     *
     * <pre>wd.find("span").withText("Hello")</pre>
     *
     * evaluates <code>#sometext</code> in the following scenario:
     *
     * <pre>
     * {@code
     * <span id="sometext">Hello</span>
     * }
     * </pre>
     *
     * @param text the text to match
     * @return filtered {@link WebElements}
     */
    public T withText(final String text);

    /**
     * <p>Filters elements which have a specified visible text.</p>
     *
     * <p>Example:</p>
     *
     * <pre>wd.find("span").withVisibleText("Hello")</pre>
     *
     * evaluates <code>#sometext</code> in the following scenario:
     *
     * <pre>
     * {@code
     * <span id="sometext">Hello</span>
     * }
     * </pre>
     *
     * @param text the text to match
     * @return filtered {@link WebElements}
     */
    public T withVisibleText(final String text);

    /**
     * <p>Filters elements which have a specified text as a substring.</p>
     *
     * <p>Example:</p>
     *
     * <pre>wd.find("span").containingText("Hello")</pre>
     *
     * evaluates <code>#sometext</code> in the following scenario:
     *
     * <pre>
     * {@code
     * <span id="sometext">Hello World</span>
     * }
     * </pre>
     *
     * @param text the text to match
     * @return filtered {@link WebElements}
     */
    public T containingText(final String text);

    /**
     * <p>Filters elements which have a specified visible text as a substring.</p>
     *
     * <p>Example:</p>
     *
     * <pre>wd.find("span").containingVisibleText("Hello")</pre>
     *
     * evaluates <code>#sometext</code> in the following scenario:
     *
     * <pre>
     * {@code
     * <span id="sometext">Hello World</span>
     * }
     * </pre>
     *
     * @param text the text to match
     * @return filtered {@link WebElements}
     */
    public T containingVisibleText(final String text);

    /**
     * <p>Filters elements which have text that matches the specified regular
     * expression.</p>
     *
     * <p>Example:</p>
     *
     * <pre>wd.find("span").matchingText(".* World")</pre>
     *
     * evaluates <code>#sometext</code> in the following scenario:
     *
     * <pre>
     * {@code
     * <span id="sometext">Hello World</span>
     * }
     * </pre>
     *
     * @param regex the regex
     * @return filtered {@link WebElements}
     */
    public T matchingText(final String regex);

    /**
     * <p>Filters elements which have visible text that matches the specified regular
     * expression.</p>
     *
     * <p>Example:</p>
     *
     * <pre>wd.find("span").matchingVisibleText(".* World")</pre>
     *
     * evaluates <code>#sometext</code> in the following scenario:
     *
     * <pre>
     * {@code
     * <span id="sometext">Hello World</span>
     * }
     * </pre>
     *
     * @param regex the regex
     * @return filtered {@link WebElements}
     */
    public T matchingVisibleText(final String regex);

    /**
     * <p>Filters elements tht are visible. Visibility is computed using
     * <code>:visible</code> CSS selector, as in {@code $(this).filter(":visible") }</p>
     *
     * <p>Example:</p>
     *
     * <pre>wd.find("span").visible()</pre>
     *
     * evaluates <code>#span2</code> in the following scenario:
     *
     * <pre>
     * {@code
     * <span id="span1" style="display: none">Hello World</span>
     * <span id="span2">Hello World</span>
     * }
     * </pre>
     *
     * @return filtered {@link WebElements}
     * @deprecated Use {@link JQueryExtWebElements#displayed()} instead
     */
    @Deprecated
    public T visible();

    public T displayed();

    public T selected();


    public String visibleText();

    /**
     * Get the values from the set of matched elements.
     *
     * @return an array with the value of each matched element, in the same order.
     */
    public String[] vals();
    /**
     * Get the values of a specific attribute from the set of matched elements.
     *
     * @param attributeName The name of the attribute to get.
     * @return an array with the attribute value from each matched element, in the same order.
     */
    public String[] attrs(String attributeName);
}
