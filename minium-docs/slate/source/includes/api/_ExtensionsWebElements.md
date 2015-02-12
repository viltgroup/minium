#ExtensionsWebElements
## .withLabel(label)
`<p>Filters elements that have a label with the given text. That means that
 returned elements have the same <code>id</code> than the <code>for</code>
 attribute of the <code>label</code> tag.</p>

 <p>Example:</p>

 <pre>wd.find(":text").withLabel("Username")</pre>

 evaluates <code>#username</code> in the following scenario:

 <pre>
 {@code
 <label for="username">Username</label>
 <input type="text" id="username" />
 }
 </pre>`


Parameter | Description
	--------- | -----------
|label|the label text
* **returns:** filtered {@link WebElements}


## .withAttr(name)
`<p>Filters elements that have an attribute.

 <p>Example:</p>

 <pre>wd.find("img").withAttr("alt")</pre>

 evaluates <code>#image2</code> in the following scenario:

 <pre>
 {@code
 <img id="image1" src="image1.gif" />
 <img id="image2" src="image2.gif" alt="Image 2" />
 }
 </pre>`


Parameter | Description
	--------- | -----------
|name|the attribute name
* **returns:** filtered {@link WebElements}


## .withAttr(namevalue)
`<p>Filters elements that have an attribute with a given value.

 <p>Example:</p>

 <pre>wd.find("img").withAttr("alt", "Image 1")</pre>

 evaluates <code>#image1</code> in the following scenario:

 <pre>
 {@code
 <img id="image1" src="image1.gif" alt="Image 1" />
 <img id="image2" src="image2.gif" alt="Image 2" />
 }
 </pre>`


Parameter | Description
	--------- | -----------
|name|the attribute name
|value|the attribute value
* **returns:** filtered {@link WebElements}


## .withCss(name)
`<p>Filters elements that have a style property.

 <p>Example:</p>

 <pre>wd.find("img").withCss("display")</pre>

 evaluates <code>#image2</code> in the following scenario:

 <pre>
 {@code
 <img id="image1" src="image1.gif" />
 <img id="image2" src="image2.gif" style="display: block" />
 }
 </pre>`


Parameter | Description
	--------- | -----------
|name|the style property name
* **returns:** filtered {@link WebElements}


## .withCss(namevalue)
`<p>Filters elements that have a style property with a given value.

 <p>Example:</p>

 <pre>wd.find("img").withAttr("display", "block")</pre>

 evaluates <code>#image1</code> in the following scenario:

 <pre>
 {@code
 <img id="image1" src="image1.gif" style="display: block" />
 <img id="image2" src="image2.gif" style="display: none" />
 }
 </pre>`


Parameter | Description
	--------- | -----------
|name|the style property name
|value|the style property value
* **returns:** filtered {@link WebElements}


## .withValue(value)
`<p>Filters elements that have a <code>value</code> attribute with a given value.
 This is very useful in input elements.

 <p>Example:</p>

 <pre>wd.find(":button").withValue("Proceed")</pre>

 evaluates <code>#button</code> in the following scenario:

 <pre>
 {@code
 <input id="button" type="button" value="Proceed" />
 }
 </pre>`


Parameter | Description
	--------- | -----------
|value|the value for attribute <code>value</code>
* **returns:** filtered {@link WebElements}


## .withName(name)
`<p>Filters elements that have a <code>name</code> attribute with a given value.
 This is very useful in form input elements.

 <p>Example:</p>

 <pre>wd.find(":password").withName("pass")</pre>

 evaluates <code>#password</code> in the following scenario:

 <pre>
 {@code
 <input id="password" type="password" name="pass" />
 }
 </pre>`


Parameter | Description
	--------- | -----------
|name|the value for attribute <code>name</code>
* **returns:** filtered {@link WebElements}


## .withText(text)
`<p>Filters elements which have a specified text.</p>

 <p>Example:</p>

 <pre>wd.find("span").withText("Hello")</pre>

 evaluates <code>#sometext</code> in the following scenario:

 <pre>
 {@code
 <span id="sometext">Hello</span>
 }
 </pre>`


Parameter | Description
	--------- | -----------
|text|the text to match
* **returns:** filtered {@link WebElements}


## .withVisibleText(text)
`<p>Filters elements which have a specified visible text.</p>

 <p>Example:</p>

 <pre>wd.find("span").withVisibleText("Hello")</pre>

 evaluates <code>#sometext</code> in the following scenario:

 <pre>
 {@code
 <span id="sometext">Hello</span>
 }
 </pre>`


Parameter | Description
	--------- | -----------
|text|the text to match
* **returns:** filtered {@link WebElements}


## .containingText(text)
`<p>Filters elements which have a specified text as a substring.</p>

 <p>Example:</p>

 <pre>wd.find("span").containingText("Hello")</pre>

 evaluates <code>#sometext</code> in the following scenario:

 <pre>
 {@code
 <span id="sometext">Hello World</span>
 }
 </pre>`


Parameter | Description
	--------- | -----------
|text|the text to match
* **returns:** filtered {@link WebElements}


## .containingVisibleText(text)
`<p>Filters elements which have a specified visible text as a substring.</p>

 <p>Example:</p>

 <pre>wd.find("span").containingVisibleText("Hello")</pre>

 evaluates <code>#sometext</code> in the following scenario:

 <pre>
 {@code
 <span id="sometext">Hello World</span>
 }
 </pre>`


Parameter | Description
	--------- | -----------
|text|the text to match
* **returns:** filtered {@link WebElements}


## .matchingText(regex)
`<p>Filters elements which have text that matches the specified regular
 expression.</p>

 <p>Example:</p>

 <pre>wd.find("span").matchingText(".* World")</pre>

 evaluates <code>#sometext</code> in the following scenario:

 <pre>
 {@code
 <span id="sometext">Hello World</span>
 }
 </pre>`


Parameter | Description
	--------- | -----------
|regex|the regex
* **returns:** filtered {@link WebElements}


## .matchingVisibleText(regex)
`<p>Filters elements which have visible text that matches the specified regular
 expression.</p>

 <p>Example:</p>

 <pre>wd.find("span").matchingVisibleText(".* World")</pre>

 evaluates <code>#sometext</code> in the following scenario:

 <pre>
 {@code
 <span id="sometext">Hello World</span>
 }
 </pre>`


Parameter | Description
	--------- | -----------
|regex|the regex
* **returns:** filtered {@link WebElements}


## .visible()
`<p>Filters elements tht are visible. Visibility is computed using
 <code>:visible</code> CSS selector, as in {@code $(this).filter(":visible") }</p>

 <p>Example:</p>

 <pre>wd.find("span").visible()</pre>

 evaluates <code>#span2</code> in the following scenario:

 <pre>
 {@code
 <span id="span1" style="display: none">Hello World</span>
 <span id="span2">Hello World</span>
 }
 </pre>`

* **returns:** filtered {@link WebElements}


## .displayed()


## .selected()


## .visibleText()


## .vals()
`Get the values from the set of matched elements.`

* **returns:** an array with the value of each matched element, in the same order.


## .attrs(attributeName)
`Get the values of a specific attribute from the set of matched elements.`


Parameter | Description
	--------- | -----------
|attributeName|The name of the attribute to get.
* **returns:** an array with the attribute value from each matched element, in the same order.

