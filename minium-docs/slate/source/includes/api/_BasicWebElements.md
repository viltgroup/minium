#BasicWebElements
## .find(elems)


## .attr(attributeName)
`
 Get the value of an attribute for the first element in the set of matched elements.`


Parameter | Description
	--------- | -----------
|attributeName|The name of the attribute to get.
* **see:** [ http://api.jquery.com/attr/#attr-attributeName](http://api.jquery.com/attr/#attr-attributeName)
* **returns:** result of jQuery .attr() method


## .css(propertyName)
`
 Get the value of style properties for the first element in the set of matched elements.`


Parameter | Description
	--------- | -----------
|propertyName|A CSS property.
* **see:** [ http://api.jquery.com/css/#css-propertyName](http://api.jquery.com/css/#css-propertyName)
* **returns:** result of jQuery .css() method


## .html()
`
 Get the HTML contents of the first element in the set of matched elements.`

* **see:** [ http://api.jquery.com/html/](http://api.jquery.com/html/)
* **returns:** result of jQuery .html() method


## .text()
`
 Get the combined text contents of each element in the set of matched elements, including their descendants.`

* **see:** [ http://api.jquery.com/text/](http://api.jquery.com/text/)
* **returns:** result of jQuery .text() method


## .val()
`
 Get the current value of the first element in the set of matched elements.`

* **see:** [ http://api.jquery.com/val/](http://api.jquery.com/val/)
* **returns:** result of jQuery .val() method


## .add(selector)
`
 Add elements to the set of matched elements.`


Parameter | Description
	--------- | -----------
|selector|A string representing a selector expression to find additional elements to add to the set of matched elements.
* **see:** [ http://api.jquery.com/add/#add-selector](http://api.jquery.com/add/#add-selector)
* **returns:** result of jQuery .add() method


## .add(elems)
`
 Add elements to the set of matched elements.`


Parameter | Description
	--------- | -----------
|elems|One or more elements to add to the set of matched elements.
* **see:** [ http://api.jquery.com/add/#add-elements](http://api.jquery.com/add/#add-elements)
* **returns:** result of jQuery .add() method


## .addBack()
`
 Add the previous set of elements on the stack to the current set, optionally filtered by a selector.`

* **see:** [ http://api.jquery.com/addBack/](http://api.jquery.com/addBack/)
* **returns:** result of jQuery .addBack() method


## .addBack(selector)
`
 Add the previous set of elements on the stack to the current set, optionally filtered by a selector.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to match the current set of elements against.
* **see:** [ http://api.jquery.com/addBack/#addBack-selector](http://api.jquery.com/addBack/#addBack-selector)
* **returns:** result of jQuery .addBack() method


## .children()
`
 Get the children of each element in the set of matched elements, optionally filtered by a selector.`

* **see:** [ http://api.jquery.com/children/](http://api.jquery.com/children/)
* **returns:** result of jQuery .children() method


## .children(selector)
`
 Get the children of each element in the set of matched elements, optionally filtered by a selector.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/children/#children-selector](http://api.jquery.com/children/#children-selector)
* **returns:** result of jQuery .children() method


## .children(elems)
`
 Get the children of each element in the set of matched elements, optionally filtered by a selector.`


Parameter | Description
	--------- | -----------
|elems|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/children/#children-selector](http://api.jquery.com/children/#children-selector)
* **returns:** result of jQuery .children() method


## .closest(selector)
`
 For each element in the set, get the first element that matches the selector by testing the element itself and traversing up through its ancestors in the DOM tree.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/closest/#closest-selector](http://api.jquery.com/closest/#closest-selector)
* **returns:** result of jQuery .closest() method


## .closest(elems)
`
 For each element in the set, get the first element that matches the selector by testing the element itself and traversing up through its ancestors in the DOM tree.`


Parameter | Description
	--------- | -----------
|elems|A jQuery object to match elements against.
* **see:** [ http://api.jquery.com/closest/#closest-jQuery-object](http://api.jquery.com/closest/#closest-jQuery-object)
* **returns:** result of jQuery .closest() method


## .contents()
`
 Get the children of each element in the set of matched elements, including text and comment nodes.`

* **see:** [ http://api.jquery.com/contents/](http://api.jquery.com/contents/)
* **returns:** result of jQuery .contents() method


## .end()
`
 End the most recent filtering operation in the current chain and return the set of matched elements to its previous state.`

* **see:** [ http://api.jquery.com/end/](http://api.jquery.com/end/)
* **returns:** result of jQuery .end() method


## .filter(selector)
`
 Reduce the set of matched elements to those that match the selector or pass the function's test.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to match the current set of elements against.
* **returns:** result of jQuery .filter() method


## .filter(elems)
`
 Reduce the set of matched elements to those that match the selector or pass the function's test.`


Parameter | Description
	--------- | -----------
|elems|An existing jQuery object to match the current set of elements against.
* **returns:** result of jQuery .filter() method


## .has(selector)
`
 Reduce the set of matched elements to those that have a descendant that matches the selector or DOM element.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/has/#has-selector](http://api.jquery.com/has/#has-selector)
* **returns:** result of jQuery .has() method


## .has(elems)
`
 Reduce the set of matched elements to those that have a descendant that matches the selector or DOM element.`


Parameter | Description
	--------- | -----------
|elems|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/has/#has-selector](http://api.jquery.com/has/#has-selector)
* **returns:** result of jQuery .has() method


## .is(selector)
`
 Check the current matched set of elements against a selector, element, or jQuery object and return true if at least one of these elements matches the given arguments.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/is/#is-selector](http://api.jquery.com/is/#is-selector)
* **returns:** result of jQuery .is() method


## .is(elems)
`
 Check the current matched set of elements against a selector, element, or jQuery object and return true if at least one of these elements matches the given arguments.`


Parameter | Description
	--------- | -----------
|elems|An existing jQuery object to match the current set of elements against.
* **see:** [ http://api.jquery.com/is/#is-jQuery-object](http://api.jquery.com/is/#is-jQuery-object)
* **returns:** result of jQuery .is() method


## .next()
`
 Get the immediately following sibling of each element in the set of matched elements. If a selector is provided, it retrieves the next sibling only if it matches that selector.`

* **see:** [ http://api.jquery.com/next/](http://api.jquery.com/next/)
* **returns:** result of jQuery .next() method


## .next(selector)
`
 Get the immediately following sibling of each element in the set of matched elements. If a selector is provided, it retrieves the next sibling only if it matches that selector.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/next/#next-selector](http://api.jquery.com/next/#next-selector)
* **returns:** result of jQuery .next() method


## .next(elems)
`
 Get the immediately following sibling of each element in the set of matched elements. If a selector is provided, it retrieves the next sibling only if it matches that selector.`


Parameter | Description
	--------- | -----------
|elems|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/next/#next-selector](http://api.jquery.com/next/#next-selector)
* **returns:** result of jQuery .next() method


## .nextAll()
`
 Get all following siblings of each element in the set of matched elements, optionally filtered by a selector.`

* **see:** [ http://api.jquery.com/nextAll/](http://api.jquery.com/nextAll/)
* **returns:** result of jQuery .nextAll() method


## .nextAll(selector)
`
 Get all following siblings of each element in the set of matched elements, optionally filtered by a selector.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/nextAll/#nextAll-selector](http://api.jquery.com/nextAll/#nextAll-selector)
* **returns:** result of jQuery .nextAll() method


## .nextAll(elems)
`
 Get all following siblings of each element in the set of matched elements, optionally filtered by a selector.`


Parameter | Description
	--------- | -----------
|elems|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/nextAll/#nextAll-selector](http://api.jquery.com/nextAll/#nextAll-selector)
* **returns:** result of jQuery .nextAll() method


## .nextUntil(selector)
`
 Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object passed.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to indicate where to stop matching following sibling elements.
* **see:** [ http://api.jquery.com/nextUntil/#nextUntil-selector-filter](http://api.jquery.com/nextUntil/#nextUntil-selector-filter)
* **returns:** result of jQuery .nextUntil() method


## .nextUntil(selectorfilter)
`
 Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object passed.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to indicate where to stop matching following sibling elements.
|filter|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/nextUntil/#nextUntil-selector-filter](http://api.jquery.com/nextUntil/#nextUntil-selector-filter)
* **returns:** result of jQuery .nextUntil() method


## .nextUntil(elems)
`
 Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object passed.`


Parameter | Description
	--------- | -----------
|elems|A DOM node or jQuery object indicating where to stop matching following sibling elements.
* **see:** [ http://api.jquery.com/nextUntil/#nextUntil-element-filter](http://api.jquery.com/nextUntil/#nextUntil-element-filter)
* **returns:** result of jQuery .nextUntil() method


## .nextUntil(elemsfilter)
`
 Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object passed.`


Parameter | Description
	--------- | -----------
|elems|A DOM node or jQuery object indicating where to stop matching following sibling elements.
|filter|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/nextUntil/#nextUntil-element-filter](http://api.jquery.com/nextUntil/#nextUntil-element-filter)
* **returns:** result of jQuery .nextUntil() method


## .not(selector)
`
 Remove elements from the set of matched elements.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/not/#not-selector](http://api.jquery.com/not/#not-selector)
* **returns:** result of jQuery .not() method


## .not(elems)
`
 Remove elements from the set of matched elements.`


Parameter | Description
	--------- | -----------
|elems|An existing jQuery object to match the current set of elements against.
* **see:** [ http://api.jquery.com/not/#not-jQuery-object](http://api.jquery.com/not/#not-jQuery-object)
* **returns:** result of jQuery .not() method


## .offsetParent()
`
 Get the closest ancestor element that is positioned.`

* **see:** [ http://api.jquery.com/offsetParent/](http://api.jquery.com/offsetParent/)
* **returns:** result of jQuery .offsetParent() method


## .parent()
`
 Get the parent of each element in the current set of matched elements, optionally filtered by a selector.`

* **see:** [ http://api.jquery.com/parent/](http://api.jquery.com/parent/)
* **returns:** result of jQuery .parent() method


## .parent(selector)
`
 Get the parent of each element in the current set of matched elements, optionally filtered by a selector.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/parent/#parent-selector](http://api.jquery.com/parent/#parent-selector)
* **returns:** result of jQuery .parent() method


## .parent(elems)
`
 Get the parent of each element in the current set of matched elements, optionally filtered by a selector.`


Parameter | Description
	--------- | -----------
|elems|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/parent/#parent-selector](http://api.jquery.com/parent/#parent-selector)
* **returns:** result of jQuery .parent() method


## .parents()
`
 Get the ancestors of each element in the current set of matched elements, optionally filtered by a selector.`

* **see:** [ http://api.jquery.com/parents/](http://api.jquery.com/parents/)
* **returns:** result of jQuery .parents() method


## .parents(selector)
`
 Get the ancestors of each element in the current set of matched elements, optionally filtered by a selector.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/parents/#parents-selector](http://api.jquery.com/parents/#parents-selector)
* **returns:** result of jQuery .parents() method


## .parents(elems)
`
 Get the ancestors of each element in the current set of matched elements, optionally filtered by a selector.`


Parameter | Description
	--------- | -----------
|elems|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/parents/#parents-selector](http://api.jquery.com/parents/#parents-selector)
* **returns:** result of jQuery .parents() method


## .parentsUntil(selector)
`
 Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jQuery object.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to indicate where to stop matching ancestor elements.
* **see:** [ http://api.jquery.com/parentsUntil/#parentsUntil-selector-filter](http://api.jquery.com/parentsUntil/#parentsUntil-selector-filter)
* **returns:** result of jQuery .parentsUntil() method


## .parentsUntil(selectorfilter)
`
 Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jQuery object.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to indicate where to stop matching ancestor elements.
|filter|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/parentsUntil/#parentsUntil-selector-filter](http://api.jquery.com/parentsUntil/#parentsUntil-selector-filter)
* **returns:** result of jQuery .parentsUntil() method


## .parentsUntil(elems)
`
 Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jQuery object.`


Parameter | Description
	--------- | -----------
|elems|A DOM node or jQuery object indicating where to stop matching ancestor elements.
* **see:** [ http://api.jquery.com/parentsUntil/#parentsUntil-element-filter](http://api.jquery.com/parentsUntil/#parentsUntil-element-filter)
* **returns:** result of jQuery .parentsUntil() method


## .parentsUntil(elemsfilter)
`
 Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jQuery object.`


Parameter | Description
	--------- | -----------
|elems|A DOM node or jQuery object indicating where to stop matching ancestor elements.
|filter|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/parentsUntil/#parentsUntil-element-filter](http://api.jquery.com/parentsUntil/#parentsUntil-element-filter)
* **returns:** result of jQuery .parentsUntil() method


## .prev()
`
 Get the immediately preceding sibling of each element in the set of matched elements, optionally filtered by a selector.`

* **see:** [ http://api.jquery.com/prev/](http://api.jquery.com/prev/)
* **returns:** result of jQuery .prev() method


## .prev(selector)
`
 Get the immediately preceding sibling of each element in the set of matched elements, optionally filtered by a selector.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/prev/#prev-selector](http://api.jquery.com/prev/#prev-selector)
* **returns:** result of jQuery .prev() method


## .prev(elems)
`
 Get the immediately preceding sibling of each element in the set of matched elements, optionally filtered by a selector.`


Parameter | Description
	--------- | -----------
|elems|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/prev/#prev-selector](http://api.jquery.com/prev/#prev-selector)
* **returns:** result of jQuery .prev() method


## .prevAll()
`
 Get all preceding siblings of each element in the set of matched elements, optionally filtered by a selector.`

* **see:** [ http://api.jquery.com/prevAll/](http://api.jquery.com/prevAll/)
* **returns:** result of jQuery .prevAll() method


## .prevAll(selector)
`
 Get all preceding siblings of each element in the set of matched elements, optionally filtered by a selector.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/prevAll/#prevAll-selector](http://api.jquery.com/prevAll/#prevAll-selector)
* **returns:** result of jQuery .prevAll() method


## .prevAll(elems)
`
 Get all preceding siblings of each element in the set of matched elements, optionally filtered by a selector.`


Parameter | Description
	--------- | -----------
|elems|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/prevAll/#prevAll-selector](http://api.jquery.com/prevAll/#prevAll-selector)
* **returns:** result of jQuery .prevAll() method


## .prevUntil(selector)
`
 Get all preceding siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to indicate where to stop matching preceding sibling elements.
* **see:** [ http://api.jquery.com/prevUntil/#prevUntil-selector-filter](http://api.jquery.com/prevUntil/#prevUntil-selector-filter)
* **returns:** result of jQuery .prevUntil() method


## .prevUntil(selectorfilter)
`
 Get all preceding siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to indicate where to stop matching preceding sibling elements.
|filter|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/prevUntil/#prevUntil-selector-filter](http://api.jquery.com/prevUntil/#prevUntil-selector-filter)
* **returns:** result of jQuery .prevUntil() method


## .prevUntil(elems)
`
 Get all preceding siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object.`


Parameter | Description
	--------- | -----------
|elems|A DOM node or jQuery object indicating where to stop matching preceding sibling elements.
* **see:** [ http://api.jquery.com/prevUntil/#prevUntil-element-filter](http://api.jquery.com/prevUntil/#prevUntil-element-filter)
* **returns:** result of jQuery .prevUntil() method


## .prevUntil(elemsfilter)
`
 Get all preceding siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object.`


Parameter | Description
	--------- | -----------
|elems|A DOM node or jQuery object indicating where to stop matching preceding sibling elements.
|filter|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/prevUntil/#prevUntil-element-filter](http://api.jquery.com/prevUntil/#prevUntil-element-filter)
* **returns:** result of jQuery .prevUntil() method


## .siblings()
`
 Get the siblings of each element in the set of matched elements, optionally filtered by a selector.`

* **see:** [ http://api.jquery.com/siblings/](http://api.jquery.com/siblings/)
* **returns:** result of jQuery .siblings() method


## .siblings(selector)
`
 Get the siblings of each element in the set of matched elements, optionally filtered by a selector.`


Parameter | Description
	--------- | -----------
|selector|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/siblings/#siblings-selector](http://api.jquery.com/siblings/#siblings-selector)
* **returns:** result of jQuery .siblings() method


## .siblings(elems)
`
 Get the siblings of each element in the set of matched elements, optionally filtered by a selector.`


Parameter | Description
	--------- | -----------
|elems|A string containing a selector expression to match elements against.
* **see:** [ http://api.jquery.com/siblings/#siblings-selector](http://api.jquery.com/siblings/#siblings-selector)
* **returns:** result of jQuery .siblings() method


## .slice(start)
`
 Reduce the set of matched elements to a subset specified by a range of indices.`


Parameter | Description
	--------- | -----------
|start|An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
* **see:** [ http://api.jquery.com/slice/#slice-start-end](http://api.jquery.com/slice/#slice-start-end)
* **returns:** result of jQuery .slice() method


## .slice(startend)
`
 Reduce the set of matched elements to a subset specified by a range of indices.`


Parameter | Description
	--------- | -----------
|start|An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
|end|An integer indicating the 0-based position at which the elements stop being selected. If negative, it indicates an offset from the end of the set. If omitted, the range continues until the end of the set.
* **see:** [ http://api.jquery.com/slice/#slice-start-end](http://api.jquery.com/slice/#slice-start-end)
* **returns:** result of jQuery .slice() method


## .hasClass(cssClass)
`
 Determine whether any of the matched elements are assigned the given class.`


Parameter | Description
	--------- | -----------
|cssClass|The class name to search for.
* **see:** [ http://api.jquery.com/hasClass/#hasClass-className](http://api.jquery.com/hasClass/#hasClass-className)
* **returns:** result of jQuery .hasClass() method

