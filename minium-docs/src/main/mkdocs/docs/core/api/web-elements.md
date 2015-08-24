# BasicElements



## `.eq(index)`

Reduce the set of matched elements to the one at the specified index.

Parameter | Description
--------- | -----------
index | An integer indicating the 0-based position of the element. If negative, it will be counting backwards from the last element in the set.

- **returns:** a new `Elements` that evaluates into a single element set with the element at the corresponding index, or an empty set if no element is found at that index

## `.first()`

Reduce the set of matched elements to the first one. It's equivalent to 
<code>this.eq(0)</code>.

- **returns:** a new `Elements` that evaluates into a single element set with the first element, or an empty set if 
<code>this</code> evaluates into an empty set

## `.last()`

Reduce the set of matched elements to the last one. It's equivalent to 
<code>this.eq(-1)</code>.

- **returns:** a new `Elements` that evaluates into a single element set with the last element, or an empty set if 
<code>this</code> evaluates into an empty set

## `.size()`

Computes the size of the evaluated set.

- **returns:** size of the evaluated set

# BasicWebElements



## `.add(selector)`

Add elements to the set of matched elements.

Parameter | Description
--------- | -----------
selector | A string representing a selector expression to find additional elements to add to the set of matched elements.

- **returns:** result of jQuery .add() method
- **see:** <a href="http://api.jquery.com/add/#add-selector">jQuery .add() method</a>

## `.addBack()`

Add the previous set of elements on the stack to the current set, optionally filtered by a selector.

- **returns:** result of jQuery .addBack() method
- **see:** <a href="http://api.jquery.com/addBack/">jQuery .addBack() method</a>

## `.attr(attributeName)`

Get the value of an attribute for the first element in the set of matched elements.

Parameter | Description
--------- | -----------
attributeName | The name of the attribute to get.

- **returns:** result of jQuery .attr() method
- **see:** <a href="http://api.jquery.com/attr/#attr-attributeName">jQuery .attr() method</a>

## `.children()`

Get the children of each element in the set of matched elements, optionally filtered by a selector.

- **returns:** result of jQuery .children() method
- **see:** <a href="http://api.jquery.com/children/">jQuery .children() method</a>

## `.closest(selector)`

For each element in the set, get the first element that matches the selector by testing the element itself and traversing up through its ancestors in the DOM tree.

Parameter | Description
--------- | -----------
selector | A string containing a selector expression to match elements against.

- **returns:** result of jQuery .closest() method
- **see:** <a href="http://api.jquery.com/closest/#closest-selector">jQuery .closest() method</a>

## `.contents()`

Get the children of each element in the set of matched elements, including text and comment nodes.

- **returns:** result of jQuery .contents() method
- **see:** <a href="http://api.jquery.com/contents/">jQuery .contents() method</a>

## `.css(propertyName)`

Get the value of style properties for the first element in the set of matched elements.

Parameter | Description
--------- | -----------
propertyName | A CSS property.

- **returns:** result of jQuery .css() method
- **see:** <a href="http://api.jquery.com/css/#css-propertyName">jQuery .css() method</a>

## `.data(key)`

Return the value at the named data store for the first element in the jQuery collection, as set by data(name, value) or by an HTML5 data-* attribute.

Parameter | Description
--------- | -----------
key | Name of the data stored.

- **returns:** result of jQuery .data() method
- **see:** <a href="http://api.jquery.com/data/#data-key">jQuery .data() method</a>

## `.end()`

End the most recent filtering operation in the current chain and return the set of matched elements to its previous state.

- **returns:** result of jQuery .end() method
- **see:** <a href="http://api.jquery.com/end/">jQuery .end() method</a>

## `.filter(selector)`

Reduce the set of matched elements to those that match the selector or pass the function's test.

Parameter | Description
--------- | -----------
selector | A string containing a selector expression to match the current set of elements against.

- **returns:** result of jQuery .filter() method
- **see:** <a href="http://api.jquery.com/filter/#filter-selector">jQuery .filter() method</a>

## `.find(selector)`




## `.has(selector)`

Reduce the set of matched elements to those that have a descendant that matches the selector or DOM element.

Parameter | Description
--------- | -----------
selector | A string containing a selector expression to match elements against.

- **returns:** result of jQuery .has() method
- **see:** <a href="http://api.jquery.com/has/#has-selector">jQuery .has() method</a>

## `.hasClass(cssClass)`

Determine whether any of the matched elements are assigned the given class.

Parameter | Description
--------- | -----------
cssClass | The class name to search for.

- **returns:** result of jQuery .hasClass() method
- **see:** <a href="http://api.jquery.com/hasClass/#hasClass-className">jQuery .hasClass() method</a>

## `.html()`

Get the HTML contents of the first element in the set of matched elements.

- **returns:** result of jQuery .html() method
- **see:** <a href="http://api.jquery.com/html/">jQuery .html() method</a>

## `.is(selector)`

Check the current matched set of elements against a selector, element, or jQuery object and return true if at least one of these elements matches the given arguments.

Parameter | Description
--------- | -----------
selector | A string containing a selector expression to match elements against.

- **returns:** result of jQuery .is() method
- **see:** <a href="http://api.jquery.com/is/#is-selector">jQuery .is() method</a>

## `.next()`

Get the immediately following sibling of each element in the set of matched elements. If a selector is provided, it retrieves the next sibling only if it matches that selector.

- **returns:** result of jQuery .next() method
- **see:** <a href="http://api.jquery.com/next/">jQuery .next() method</a>

## `.nextAll()`

Get all following siblings of each element in the set of matched elements, optionally filtered by a selector.

- **returns:** result of jQuery .nextAll() method
- **see:** <a href="http://api.jquery.com/nextAll/">jQuery .nextAll() method</a>

## `.nextUntil(selector)`

Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object passed.

Parameter | Description
--------- | -----------
selector | A string containing a selector expression to indicate where to stop matching following sibling elements.

- **returns:** result of jQuery .nextUntil() method
- **see:** <a href="http://api.jquery.com/nextUntil/#nextUntil-selector-filter">jQuery .nextUntil() method</a>

## `.not(selector)`

Remove elements from the set of matched elements.

Parameter | Description
--------- | -----------
selector | A string containing a selector expression to match elements against.

- **returns:** result of jQuery .not() method
- **see:** <a href="http://api.jquery.com/not/#not-selector">jQuery .not() method</a>

## `.offsetParent()`

Get the closest ancestor element that is positioned.

- **returns:** result of jQuery .offsetParent() method
- **see:** <a href="http://api.jquery.com/offsetParent/">jQuery .offsetParent() method</a>

## `.parent()`

Get the parent of each element in the current set of matched elements, optionally filtered by a selector.

- **returns:** result of jQuery .parent() method
- **see:** <a href="http://api.jquery.com/parent/">jQuery .parent() method</a>

## `.parents()`

Get the ancestors of each element in the current set of matched elements, optionally filtered by a selector.

- **returns:** result of jQuery .parents() method
- **see:** <a href="http://api.jquery.com/parents/">jQuery .parents() method</a>

## `.parentsUntil(selector)`

Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jQuery object.

Parameter | Description
--------- | -----------
selector | A string containing a selector expression to indicate where to stop matching ancestor elements.

- **returns:** result of jQuery .parentsUntil() method
- **see:** <a href="http://api.jquery.com/parentsUntil/#parentsUntil-selector-filter">jQuery .parentsUntil() method</a>

## `.prev()`

Get the immediately preceding sibling of each element in the set of matched elements, optionally filtered by a selector.

- **returns:** result of jQuery .prev() method
- **see:** <a href="http://api.jquery.com/prev/">jQuery .prev() method</a>

## `.prevAll()`

Get all preceding siblings of each element in the set of matched elements, optionally filtered by a selector.

- **returns:** result of jQuery .prevAll() method
- **see:** <a href="http://api.jquery.com/prevAll/">jQuery .prevAll() method</a>

## `.prevUntil(selector)`

Get all preceding siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object.

Parameter | Description
--------- | -----------
selector | A string containing a selector expression to indicate where to stop matching preceding sibling elements.

- **returns:** result of jQuery .prevUntil() method
- **see:** <a href="http://api.jquery.com/prevUntil/#prevUntil-selector-filter">jQuery .prevUntil() method</a>

## `.prop(propertyName)`

Get the value of a property for the first element in the set of matched elements.

Parameter | Description
--------- | -----------
propertyName | The name of the property to get.

- **returns:** result of jQuery .prop() method
- **see:** <a href="http://api.jquery.com/attr/#prop-propertyName">jQuery .prop() method</a>

## `.siblings()`

Get the siblings of each element in the set of matched elements, optionally filtered by a selector.

- **returns:** result of jQuery .siblings() method
- **see:** <a href="http://api.jquery.com/siblings/">jQuery .siblings() method</a>

## `.slice(start)`

Reduce the set of matched elements to a subset specified by a range of indices.

Parameter | Description
--------- | -----------
start | An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.

- **returns:** result of jQuery .slice() method
- **see:** <a href="http://api.jquery.com/slice/#slice-start-end">jQuery .slice() method</a>

## `.text()`

Get the combined text contents of each element in the set of matched elements, including their descendants.

- **returns:** result of jQuery .text() method
- **see:** <a href="http://api.jquery.com/text/">jQuery .text() method</a>

## `.val()`

Get the current value of the first element in the set of matched elements.

- **returns:** result of jQuery .val() method
- **see:** <a href="http://api.jquery.com/val/">jQuery .val() method</a>

# FreezableElements

Everytime an `Elements` expression is evaluated, the corresponding set of values needs to be computed. That means that the same `Elements` expression, when evaluated multiple times, can evaluate into different sets. 
<p>This is a very handful behaviour, specially when interacting with elements, but it has a computational cost. To avoid computing the same set of results multiple times, a `FreezableElements` expression memoizes evaluated results once they are not empty, so that next evaluations don't require computation.</p>

## `.freeze()`

Memoizes the result of an evaluation once it returns a non-empty set, so that next evaluations will return the exact same results instead of computing them again.

- **returns:** a new `Elements` that memoizes evaluation results once they are non-empty

# ConditionalWebElements

Extends `ConditionalElements` by adding methods that receive a CSS selector instead of `Elements`. First, CSS selector is evaluated using 
<code>$(selector)</code> and then the same logic applies as in `ConditionalElements`

## `.and(selector)`

Intersects both evaluated sets. For instance, if 
<code>this</code> evaluates into 
<code>{ A, B, C }</code> and 
<code>selector</code> evaluates into 
<code>{ B, C, D }</code>, then 
<code>this.and(selector)</code> evaluates into 
<code>{ B, C }</code>

Parameter | Description
--------- | -----------
selector | a CSS selector

- **returns:** new `Elements` that corresponds to the intersection of 
<code>this</code> and elements represented by 
<code>selector</code>

## `.or(selector)`

Represents the union both evaluated sets. For instance, if 
<code>this</code> evaluates into 
<code>{ A, B, C }</code> and 
<code>selector</code> evaluates into 
<code>{ B, C, D }</code>, then 
<code>this.or(selector)</code> evaluates into 
<code>{ A, B, C, D }</code>

Parameter | Description
--------- | -----------
selector | a CSS selector

- **returns:** new `Elements` that corresponds to the union of 
<code>this</code> and elements represented by 
<code>selector</code>

## `.then(selector)`

Evaluates into the elements represented by 
<code>selector</code> if and only if 
<code>this</code> evaluates into a non-empty set, otherwise returns an empty set. 
<p> For instance, if <code>this</code> evaluates into <code>{ A, B }</code> and <code>selector</code> evaluates into <code>{ B, C }</code> then <code>this.then(selector)</code> evaluates into <code>{ B, C }</code>. </p>
<p> If <code>this</code> evaluates into an empty set, then <code>this.then(selector)</code> evaluates into an empty set.</p>

Parameter | Description
--------- | -----------
selector | a CSS selector

- **returns:** new `Elements` that evaluates into elements represented by 
<code>selector</code> only and only if 
<code>this</code> evaluates into a non-empty set, otherwise returns an empty set.

## `.unless(selector)`

Evaluates into 
<code>this</code> if and only if elements represented by 
<code>selector</code> evaluates into a empty set, otherwise returns an empty set. 
<p> For instance, if <code>this</code> evaluates into <code>{ A, B }</code> and <code>selector</code> evaluates into <code>{ B, C }</code> then <code>this.unless(selector)</code> evaluates into an empty set. </p>
<p> If <code>selector</code> evaluates into an empty set, then <code>this.unless(selector)</code> evaluates into <code>{ A, B }</code>.</p>

Parameter | Description
--------- | -----------
selector | a CSS selector

- **returns:** new `Elements` that evaluates into 
<code>this</code> only and only if elements represented by 
<code>selector</code> evaluates into a empty set, otherwise returns an empty set.

## `.when(selector)`

Evaluates into 
<code>this</code> if and only if elements represented by 
<code>selector</code> evaluates into a non-empty set, otherwise returns an empty set. Basically, 
<code>someElems.when(selector)</code> is equivalent to 
<code>$(selector).then(someElems)</code>.

Parameter | Description
--------- | -----------
selector | a CSS selector

- **returns:** new `Elements` that evaluates into 
<code>this</code> only and only if elements represented by 
<code>selector</code> evaluates into a non-empty set, otherwise returns an empty set.

# ExtensionsWebElements

This interface provides useful jQuery extension methods for Minium `WebElements`, like contained text filtering or attribute value filtering.

## `.containingText(text)`

<p>Filters elements which have a specified text as a substring.</p> 
<p>Example:</p> 
<pre>wd.find("span").containingText("Hello")</pre> evaluates 
<code>#sometext</code> in the following scenario: 
<pre>
 &lt;span id="sometext"&gt;Hello World&lt;/span&gt;
</pre>

Parameter | Description
--------- | -----------
text | the text to match

- **returns:** filtered `WebElements`

## `.containingVisibleText(text)`

<p>Filters elements which have a specified visible text as a substring.</p> 
<p>Example:</p> 
<pre>wd.find("span").containingVisibleText("Hello")</pre> evaluates 
<code>#sometext</code> in the following scenario: 
<pre>
 &lt;span id="sometext"&gt;Hello World&lt;/span&gt;
</pre>

Parameter | Description
--------- | -----------
text | the text to match

- **returns:** filtered `WebElements`

## `.displayed()`




## `.matchingText(regex)`

<p>Filters elements which have text that matches the specified regular expression.</p> 
<p>Example:</p> 
<pre>wd.find("span").matchingText(".* World")</pre> evaluates 
<code>#sometext</code> in the following scenario: 
<pre>
 &lt;span id="sometext"&gt;Hello World&lt;/span&gt;
</pre>

Parameter | Description
--------- | -----------
regex | the regex

- **returns:** filtered `WebElements`

## `.matchingVisibleText(regex)`

<p>Filters elements which have visible text that matches the specified regular expression.</p> 
<p>Example:</p> 
<pre>wd.find("span").matchingVisibleText(".* World")</pre> evaluates 
<code>#sometext</code> in the following scenario: 
<pre>
 &lt;span id="sometext"&gt;Hello World&lt;/span&gt;
</pre>

Parameter | Description
--------- | -----------
regex | the regex

- **returns:** filtered `WebElements`

## `.selected()`




## `.visible()`

<p>Filters elements tht are visible. Visibility is computed using <code>:visible</code> jQuery CSS selector, as in `$(this).filter(":visible") `</p> 
<p>Example:</p> 
<pre>wd.find("span").visible()</pre> evaluates 
<code>#span2</code> in the following scenario: 
<pre>
 &lt;span id="span1" style="display: none"&gt;Hello World&lt;/span&gt;
 &lt;span id="span2"&gt;Hello World&lt;/span&gt;
</pre>

- **returns:** filtered `WebElements`

## `.visibleText()`




## `.withAttr(name)`

<p>Filters elements that have an attribute. </p>
<p>Example:</p> 
<pre>wd.find("img").withAttr("alt")</pre> evaluates 
<code>#image2</code> in the following scenario: 
<pre>
 &lt;img id="image1" src="image1.gif"&gt;
 &lt;img id="image2" src="image2.gif" alt="Image 2"&gt;
</pre>

Parameter | Description
--------- | -----------
name | the attribute name

- **returns:** filtered `WebElements`

## `.withCss(name)`

<p>Filters elements that have a style property. </p>
<p>Example:</p> 
<pre>wd.find("img").withCss("display")</pre> evaluates 
<code>#image2</code> in the following scenario: 
<pre>
 &lt;img id="image1" src="image1.gif"&gt;
 &lt;img id="image2" src="image2.gif" style="display: block"&gt;
</pre>

Parameter | Description
--------- | -----------
name | the style property name

- **returns:** filtered `WebElements`

## `.withLabel(label)`

<p>Filters elements that have a label with the given text. That means that returned elements have the same <code>id</code> than the <code>for</code> attribute of the <code>label</code> tag.</p> 
<p>Example:</p> 
<pre>wd.find(":text").withLabel("Username")</pre> evaluates 
<code>#username</code> in the following scenario: 
<pre>
 &lt;label for="username"&gt;Username&lt;/label&gt;
 &lt;input type="text" id="username"&gt;
</pre>

Parameter | Description
--------- | -----------
label | the label text

- **returns:** filtered `WebElements`

## `.withName(name)`

<p>Filters elements that have a <code>name</code> attribute with a given value. This is very useful in form input elements. </p>
<p>Example:</p> 
<pre>wd.find(":password").withName("pass")</pre> evaluates 
<code>#password</code> in the following scenario: 
<pre>
 &lt;input id="password" type="password" name="pass"&gt;
</pre>

Parameter | Description
--------- | -----------
name | the value for attribute 
<code>name</code>

- **returns:** filtered `WebElements`

## `.withProp(name)`

<p>Filters elements that have a property. </p>
<p>Example:</p> 
<pre>wd.find("img").withProp("title")</pre> evaluates 
<code>#image2</code> in the following scenario: 
<pre>
 &lt;img id="image1" src="image1.gif"&gt;
 &lt;img id="image2" src="image2.gif" title="Image 2"&gt;
</pre>

Parameter | Description
--------- | -----------
name | the property name

- **returns:** filtered `WebElements`

## `.withText(text)`

<p>Filters elements which have a specified text.</p> 
<p>Example:</p> 
<pre>wd.find("span").withText("Hello")</pre> evaluates 
<code>#sometext</code> in the following scenario: 
<pre>
 &lt;span id="sometext"&gt;Hello&lt;/span&gt;
</pre>

Parameter | Description
--------- | -----------
text | the text to match

- **returns:** filtered `WebElements`

## `.withValue(value)`

<p>Filters elements that have a <code>value</code> attribute with a given value. This is very useful in input elements. </p>
<p>Example:</p> 
<pre>wd.find(":button").withValue("Proceed")</pre> evaluates 
<code>#button</code> in the following scenario: 
<pre>
 &lt;input id="button" type="button" value="Proceed"&gt;
</pre>

Parameter | Description
--------- | -----------
value | the value for attribute 
<code>value</code>

- **returns:** filtered `WebElements`

## `.withVisibleText(text)`

<p>Filters elements which have a specified visible text.</p> 
<p>Example:</p> 
<pre>wd.find("span").withVisibleText("Hello")</pre> evaluates 
<code>#sometext</code> in the following scenario: 
<pre>
 &lt;span id="sometext"&gt;Hello&lt;/span&gt;
</pre>

Parameter | Description
--------- | -----------
text | the text to match

- **returns:** filtered `WebElements`

# EvalWebElements



## `.eval(script)`




## `.evalWebElements(script)`




# TargetLocatorWebElements



## `.documentRoots()`




## `.frames()`




## `.windows()`




# PositionWebElements

The Interface PositionWebElements.

## `.above(expr)`

Returns elements above this element that match the expression.

Parameter | Description
--------- | -----------
expr | the expression to use for matching elements.

- **returns:** elements

## `.below(expr)`

Returns elements below this element that match the expression.

Parameter | Description
--------- | -----------
expr | the expression to use for matching elements.

- **returns:** elements

## `.leftOf(expr)`

Returns elements at the left this element that match the expression.

Parameter | Description
--------- | -----------
expr | the expression to use for matching elements.

- **returns:** elements

## `.overlaps(expr)`

Overlaps.

Parameter | Description
--------- | -----------
expr | the expr

- **returns:** the t

## `.rightOf(expr)`

Returns elements at the right of this element that match the expression.

Parameter | Description
--------- | -----------
expr | the expression to use for matching elements.

- **returns:** elements

