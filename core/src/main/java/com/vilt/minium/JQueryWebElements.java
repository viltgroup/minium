package com.vilt.minium;


/**
 * Declares methods that correspond to JQuery transversing functions.
 *
 * @param <T> the generic type
 */
@JQueryResources({ "minium/js/jquery.min.js", "minium/js/jquery-ext.js" })
public interface JQueryWebElements<T extends WebElements> extends WebElements {

	/**
	 * Access a property on the first matched element. This method makes it easy 
	 * to retrieve a property value from the first matched element. If the element 
	 * does not have an attribute with such a name, undefined is returned. 
	 * Attributes include title, alt, src, href, width, style, etc.
	 * 
	 * @param name The name of the property to access.
	 * 
	 * @return String
	 */
	public String attr(String name);

	/**
	 * Access a property on the first matched element. This method makes it easy 
	 * to retrieve a property value from the first matched element. If the element 
	 * does not have an attribute with such a name, undefined is returned. 
	 * Attributes include title, alt, src, href, width, style, etc.
	 * 
	 * @param name The name of the property to access.
	 * 
	 * @return String
	 */
	public String[] attrs(String name);

	/**
	 * Get the html contents (innerHTML) of the first matched element. This property 
	 * is not available on XML documents (although it will work for XHTML documents).
	 * 
	 * @return String html
	 */
	public String html();
	
	/**
	 * Get the combined text contents of all matched elements.
	 * The result is a string that contains the combined text contents of all matched 
	 * elements. This method works on both HTML and XML documents. Cannot be used on 
	 * input elements. For input field text use the <a href='Attributes/val#val'>val 
	 * attribute</a>
	 * 
	 * @return String text
	 */
	public String text();

	/**
	 * Get the current value of the first element in the set of matched elements. The
	 * .val() method is primarily used to get the values of form  elements such as
	 * input, select and textarea. It differs from jQuery val because in the case
	 * of <select  multiple="multiple"> elements, the .val() method only returns the
	 * first selected option value;
	 * if no option is selected, it returns null.
	 *
	 * @return the string
	 */
	public String val();
	
	/**
	 * Vals.
	 *
	 * @return the string[]
	 */
	public String[] vals();
	
	/**
	 * <p>Add elements to the set of matched elements.</p>
	 * <p>For more information, read
	 * <a href="http://api.jquery.com/add/#add-selector">.add( selector )</a>
	 * </p>
	 * 
	 * @param selector A string representing a selector expression to find additional 
	 * elements to add to the set of matched elements.
	 * @return {@link WebElements} corresponding to the union of elements
	 */
	public T add(String selector);

	/**
	 * <p>Add elements to the set of matched elements.</p>
	 * <p>For more information, read
	 * <a href="http://api.jquery.com/add/#add-jQuery-object">.add( jQuery object )</a>
	 * </p>
	 * 
	 * @param elems An existing {@link WebElements} object to add to the set of 
	 * matched elements..
	 * @return {@link WebElements} corresponding to the union of elements
	 */
	public T add(T elems);

	/**
	 * Add the previous set of elements on the stack to the current set, optionally 
	 * filtered by a selector.
	 * @return
	 */
	public T addBack();

	/**
	 * Children.
	 *
	 * @return the t
	 */
	public T children();

	/**
	 * This set can be filtered with an optional expression that will cause only 
	 * elements matching the selector to be collected. Also note: while parents() 
	 * will look at all ancestors, children() will only consider immediate child 
	 * elements.
	 * 
	 * @param expr An expression to filter the child Elements with.
	 * 
	 * @return jQuery
	 */
	public T children(String expr);

	/**
	 * Children.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T children(T expr);

	/**
	 * <p>Closest works by first looking at the current element to see if it 
	 * matches the specified expression, if so it just returns the element itself. 
	 * If it doesn't match then it will continue to traverse up the document, 
	 * parent by parent, until an element is found that matches the specified 
	 * expression. If no matching element is found then none will be returned.</p>
	 * <p>Closest is especially useful for dealing with event delegation.</p>
	 * 
	 * @param expr An expression to filter the elements with.
	 * 
	 * @return jQuery
	 */
	public T closest(String expr);

	/**
	 * Closest.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T closest(T expr);

	/**
	 * Find all the child nodes inside the matched elements (including text nodes), 
	 * or the content document, if the element is an iframe.
	 * 
	 * @return jQuery
	 */
	public T contents();

	/**
	 * If there was no destructive operation before, an empty set is returned.
	 * 
	 * A 'destructive' operation is any operation that changes the set of matched 
	 * jQuery elements, which means any Traversing function that returns a jQuery 
	 * object - including <a href='Traversing/add'>add</a>, 
	 * <a href='Traversing/andSelf'>andSelf</a>, 
	 * <a href='Traversing/children'>children</a>, 
	 * <a href='Traversing/filter'>filter</a>, 
	 * <a href='Traversing/find'>find</a>, 
	 * <a href='Traversing/map'>map</a>, 
	 * <a href='Traversing/next'>next</a>, 
	 * <a href='Traversing/nextAll'>nextAll</a>, 
	 * <a href='Traversing/not'>not</a>, 
	 * <a href='Traversing/parent'>parent</a>, 
	 * <a href='Traversing/parents'>parents</a>, 
	 * <a href='Traversing/prev'>prev</a>, 
	 * <a href='Traversing/prevAll'>prevAll</a>, 
	 * <a href='Traversing/siblings'>siblings</a> 
	 * and <a href='Traversing/slice'>slice</a> - plus the 
	 * <a href='Manipulation/clone'>clone</a> function (from Manipulation). 
	 * 
	 * @return jQuery
	 */
	public T end();

	/**
	 * Argument is the position of the element in the set of matched elements, 
	 * starting at 0 and going to length - 1.  Since the query filters out all 
	 * elements that do not match the given index, providing an invalid index 
	 * returns an empty set of elements rather than null.
	 * 
	 * @param index The index of the element in the jQuery object.
	 * 
	 * @return jQuery
	 */
	public T eq(int index);

	/**
	 * This method is used to narrow down the results of a search.
	 * 
	 * Provide a comma-separated list of expressions to apply multiple filters 
	 * at once.
	 * 
	 * @param expr An expression to pass into the filter
	 * 
	 * @return jQuery
	 */
	public T filter(String expr);

	/**
	 * Filter.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T filter(T expr);

	/**
	 * All searching is done using a <a href='Selectors'>jQuery expression</a>. 
	 * The expression can be written using CSS 1-3 Selector syntax. 
	 * 
	 * @param expr An expression to search with.
	 * 
	 * @return jQuery
	 */
	public T find(String expr);

	/**
	 * Find.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T find(T expr);

	/**
	 * Given a jQuery object that represents a set of DOM elements, the .first() 
	 * method constructs a new jQuery object from the first matching element.
	 * 
	 * @return jQuery
	 */
	public T first();

	/**
	 * Given a jQuery object that represents a set of DOM elements, the .has()
	 * method constructs a new jQuery object from a subset of the matching elements.
	 * The supplied selector is tested against the descendants of the matching
	 * elements; the element will be included in the result if any of its
	 * descendant elements matches the selector.
	 *
	 * @param selector the selector
	 * @return the t
	 */
	public T has(String selector);

	/**
	 * Checks for.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T has(T expr);

	/**
	 * <p>If no element fits, or the expression is not valid, then the response will be 
	 * 'false'.</p>
	 * <p>'''Note''': As of jQuery 1.3 all expressions are supported. Previously complex 
	 * expressions, such as those containing hierarchy selectors (such as +, ~, and >), 
	 * always returned 'true'.</p>
	 * 
	 * <p><a href='Traversing/filter'>filter</a> is used internally, therefore all rules 
	 * that apply there apply here, as well.</p>
	 * 
	 * @param expr The expression with which to filter
	 * 
	 * @return Boolean
	 */
	public boolean is(String expr);

	public boolean is(T elems);

	/**
	 * Given a jQuery object that represents a set of DOM elements, the .last() 
	 * method constructs a new jQuery object from the last matching element.
	 *
	 * @return jQuery
	 */
	public T last();

	public T next();

	/**
	 * next only returns the very next sibling for each element, not all next 
	 * siblings (see nextAll).
	 * 
	 * You may provide an optional expression to filter the returned set. 
	 * 
	 * @param expr An expression with which to filter the returned set.
	 * 
	 * @return jQuery
	 */
	public T next(String expr);

	/**
	 * Next.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T next(T expr);

	public T nextAll();

	/**
	 * Use an optional expression to filter the matched set. 
	 * 
	 * @param expr An expression to filter the next Elements with.
	 * 
	 * @return jQuery
	 */
	public T nextAll(String expr);

	/**
	 * Next all.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T nextAll(T expr);

	public T nextUntil(String selector);

	public T nextUntil(String selector, String filter);

	public T nextUntil(T elems);

	public T nextUntil(T elems, String filter);

	/**
	 * Not.
	 *
	 * @param expr An expression with which to remove matching elements, an
	 * element to remove from the set or a set of elements to remove from
	 * the jQuery set of matched elements.
	 * @return jQuery
	 */
	public T not(String expr);

	/**
	 * Not.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T not(T expr);

	/**
	 * This is the first parent of the element that has position (as in 
	 * relative or absolute). This method only works with visible elements.
	 * 
	 * @return jQuery
	 */
	public T offsetParent();

	/**
	 * You may use an optional expression to filter the set of parent elements 
	 * that will match. If there is no parent, returns a jQuery object with a 
	 * length of 0.
	 * 
	 * @param expr An expression to filter the parents with.
	 * 
	 * @return jQuery
	 */
	public T parent(String expr);

	/**
	 * Parent.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T parent(T expr);

	/**
	 * Parents.
	 *
	 * @param expr An expression to filter the ancestors with
	 * @return jQuery
	 */
	public T parents(String expr);

	/**
	 * Parents.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T parents(T expr);

	public T parentsUntil(String selector);

	/**
	 * Given a selector expression that represents a set of DOM elements, the
	 * .parentsUntil() method traverses through the ancestors of these elements
	 * until it reaches an element matched by the selector passed in the method's
	 * argument. The resulting jQuery object contains all of the ancestors up to
	 * but not including the one matched by the .parentsUntil() selector.
	 *
	 * @param selector the selector
	 * @param filter the filter
	 * @return the t
	 */
	public T parentsUntil(String selector, String filter);

	public T parentsUntil(T expr);

	/**
	 * Parents until.
	 *
	 * @param expr the expr
	 * @param filter the filter
	 * @return the t
	 */
	public T parentsUntil(T expr, String filter);

	public T prev();

	/**
	 * Use an optional expression to filter the matched set.
	 * 
	 * Only the immediately previous sibling is returned, not all previous siblings. 
	 * 
	 * @param expr An expression to filter the previous Elements with.
	 * 
	 * @return jQuery
	 */
	public T prev(String expr);

	/**
	 * Prev.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T prev(T expr);

	public T prevAll();

	/**
	 * Use an optional expression to filter the matched set. 
	 * 
	 * @param expr An expression to filter the previous elements with.
	 * 
	 * @return jQuery
	 */
	public T prevAll(String expr);

	/**
	 * Prev all.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T prevAll(T expr);

	public T prevUntil(String selector);

	public T prevUntil(String selector, String filter);

	public T prevUntil(T elems);

	public T prevUntil(T elems, String filter);

	/**
	 * Siblings.
	 *
	 * @return the t
	 */
	public T siblings();

	/**
	 * Siblings.
	 *
	 * @param expr An expression to filter the sibling Elements with
	 * @return jQuery
	 */
	public T siblings(String expr);

	/**
	 * Siblings.
	 *
	 * @param expr the expr
	 * @return the t
	 */
	public T siblings(T expr);

	/**
	 * Behaves exactly like the built-in Array slice method. 
	 * 
	 * @param start Where to start the subset. The first element is at zero. 
	 * Can be negative to start from the end of the selection.
	 * 
	 * @param end Where to end the subset (does not include the end element 
	 * itself). If unspecified, ends at the end of the selection.
	 * 
	 * @return jQuery
	 */
	public T slice(int start, int end);

	/**
	 * This is an alternative to is("." + cssClass).
	 * 
	 * @param cssClass The class to match.
	 * 
	 * @return Boolean
	 */
	public boolean hasClass(String cssClass);

	/**
	 * Size.
	 *
	 * @return the int
	 */
	public int size();

	public <V> V call(String fnName, Object ... args);
}