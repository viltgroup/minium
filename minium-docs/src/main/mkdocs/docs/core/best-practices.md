# Best Practices

## One `Elements` expression, one `Interaction`

Most of UI testing and automation code should be as simple as identifying elements and perform interactions with those elements.
For that reason, Minium code should be as straighforward as possible:

* an expression should be chosen to evaluate into an element
* an interaction should be applied to that expression
* explicit waits sould be avoided if possible
* getting values from elements like text, size, etc. should also be avoided

An example of what Minium code should look like:

```javascript
// elements expressions
var usernameFld = $(":text").withName("username");
var passwordFld = $(":password").withName("password");
var loginBtn =  $(":submit");

// interactions
usernameFld.fill("minium");
passwordFld.fill("some strong password");
loginBtn.click();
```

### Avoid control flow statements

Control flow statements like `if-then-else` should be an exception, not the rule, and so, try to aoid using them.

As an example, suppose you want to uncheck all checkboxes in a form. A possible way of doing it could be:

```javascript
var checkboxes = $("#my-form :checkbox");
for (var checkbox in Iterator(checkboxes)) {
  if (checkbox.is(":checked")) checkbox.click();
}
```

However, it is possible to do the same without the `if` statement. The right way would be:

```javascript
var checkboxes = $("#my-form :checkbox").filter(":checked");
for (var checkbox in Iterator(checkboxes)) {
  checkboxes.click();
}
```

### Avoid explicit waits

Let's consider filling a field when some loading operation is running. If we want to ensure that
we don't fill that field until the loading operation completes, one possible solution would be:

```javascript
// elements
var field = $("#somefield");
var loading = $(".loading");
// interactions
loading.waitForUnexistence();
field.fill("Minium can do better than this");
```

However, we can simplify that code by just considering a different expression for `field`: if the expression
only evaluates into `#somefield` element when `.loading` element doesn't exist, we can avoid having an explicit
wait:

```javascript
// elements
var field = $("#somefield").unless(".loading");
// interactions
field.fill("Minium can!");
```

### Avoid getting values from elements

Minium provides some jquery methods for accessing values from `WebElements`, like '.text()', '.attr(name)', etc.
However, these methods will always evaluate immediatelly, which can be a problem, because Minium cannot
ensure their evaluation occurs when it actually evaluates into a non-empty set. For that reason, it should avoid.

Instead, filters are provided to restrict elements based on a specific value:

```javascript
// gets element by text
$("span").withText("Minium can!");

// gets element by style value
$("div").withCss("visibility", "visible");

// gets element by name
$(":text").withName("somename");
```

In case you really need to get some value, consider chaining the method call with a `.waitForExistence()`:

```javascript
var elemText = $("label").waitForExistence().text();
// or getting a style value
var backgroundColor = $("label").waitForExistence().css("background-color");
```

### Avoid iterating using `.size()`

Let's say we want to remove all items from a shopping cart, and for that we need to iterate through all the
corresponding remove buttons and click on it. We could consider the following code for that:

```javascript
var shoppingCart = $("#shopping-cart");
var removeBtns = shoppingCart.find(".items button").withValue("Remove");
var size = removeBtns.size();

for (var i = 0; i < size; i++) {
  // only clicks the first matching element
  removeBtn.click();
}
```
However, as explained before, getting values from elements should be avoided, and so a better alternative is to replace
the iteration with a `while` loop where we check for the existence of `removeBtns` (don't forget that
each iteration will remove the first element, therefore next time `removeBtns` evaluates it will return
one element less):

```javascript
var shoppingCart = $("#shopping-cart");
var removeBtns = shoppingCart.find(".items button").withValue("Remove");

while (removeBtns.checkForExistence()) {
  // only clicks the first matching element
  removeBtns.click();
}
```

Yet, that code is not perfect yet: when no more items exist in the shopping cart, Minium will wait until a timeout at
`removeBtns.checkForExistence()` and only then it will return false, breaking the `while` loop.

On the other hand, if we try to check immediatelly for the existence of `removeBtns` (with `removeBtns.checkForExistence("immediate")`),
that could cause some problems. Let's, for instance, make things even harder: consider that every time we remove an element of the shopping
cart, the shopping cart is refreshed dynamically (an element `.loading` will be displayed).

In that case, removing an item would mean that the next iteration using `removeBtns.checkForExistence("immediate")` would not occur,
because `removeBtns` would not exist until the shopping cart completed refreshing.

So, how do we avoid breaking the loop too soon and avoid waiting the `timeout` period at the end?
The solution is the following:

```javascript
var shoppingCart = $("#shopping-cart").unless(".loading");
var removeBtns = shoppingCart.find(".items button").withValue("Remove");

while (shoppingCart.waitForExistence().then(removeBtns).checkForExistence("immediate")) {
  // only clicks the first matching element each iteration
  removeBtns.click();
}
```
So what did we change?

- we changed the shopping cart expression to `$("#shopping-cart").unless(".loading")`, so that we
  only consider the shopping cart element when no loading is in progress
- we changed the `while` loop condition to:
    - ensure shopping cart exists (`shoppingCart.waitForExistence()`)
    - we use the `.then(removeBtns)` method to return `removeBtns` expression only when `shoppingCart.waitForExistence()`
      evaluates into a non-empty set
    - we can now check immediatelly for the existence of `removeBtns`, because we know that no loading
      operation is in progress

## Base `Elements` expression pattern

The concept behing the Base `Elements` expression is that it should represent the root elements
of the UI that can be interacted with.

For instance:

- when a `.loading` elements is displayed in the page, no element should be interacted with,
  so base should evaluate into a empty set
- when *Modal* elements are displayed (for instance, a [Bootstrap Modal](http://getbootstrap.com/javascript/#modals))
  we want base to evaluate to that *Modal* element, therefore excluding all elements
  that are behind the *backdrop* element

Then, by using `base` as the root of our elements expressions, we can get some assurance
that we are getting the right elements instead of getting elements that are not interactable
(or should not be interactable) at that point.


```javascript
var loading = $(".loading");
var base = $(":root").unless(loading);

// we get field by finding text elements starting at base
var field = base.find(":text").withName("somefield");

field.fill("Minium can!");
```

Another example: let's assume that we have a page with a modal window that "hides"
a text field, but the modal window itself also provides a text field. By using the
base `Elements` pattern, we can easily refer to the one that is currently available for
interactions with just `base.find(":text")`. That expression evaluates into the text field
that is hidden by the modal window when the modal window is closed, and it evaluates
into the text field element provided by the modal window when that modal window is displayed.

```javascript
var modalWnd = $(".modal");
var base = $(":root").unless(modalWnd).add(modalWnd);

var openModalBtn = base.find("button").withText("Open Modal");
var textFld = base.find(":text");

// no modal window at this point, it evaluates to the text input that will be hidden by the modal window
textFld.fill("No modal window yet");

// opens modal window
openModalBtn.click();

// now it evaluates into the text field inside the modal window
textFld.fill("Modal window text field");

// at this point, this will fail after a timeout because openModalBtn only evaluates when modal
// window is closed
openModalBtn.click(); // throws a TimeoutException
```

### Control timeout based on the page state

However, we still need to provide more control when a timeout occurs. In the case
we have a loading element, most likely the application will be performing some AJAX
requests that can take more time than the usual. For those cases, we want to have a
way to provide a different timeout.

Minium provides that kind of mechanism with the `onTimeout` interaction listener.
That interaction listener captures timed out interactions and checks if some conditions
are met (in our example, if a `.loading` element is being displayed). In case it is, it
will then wait using a specified waiting preset for a different condition (in our example,
that the `.loading` element is not displayed anymore), after which it will retry the
interaction:

```javascript
var timeUnits = require("minium/timeunits");

browser.configure()
  //  we define "very-slow" waiting preset as a 30 seconds timeout
  .waitingPreset("very-slow")
    .timeout(30, timeUnits.SECONDS)
  .done()
  // and the onTimeout interaction listener
  .interactionListeners()
    .add(minium.interactionListeners
      .onTimeout()
      .when(loading)
      .waitForUnexistence(loading)
      .withWaitingPreset("very-slow")
      .thenRetry());
```
