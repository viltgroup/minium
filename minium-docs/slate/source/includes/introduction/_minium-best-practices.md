# Best Practices

## One `Elements` expression, one `Interaction`

Minium code should be as straighforward as possible:

* an expression should be chosen to evaluate into an element
* an interaction should be applied to that expression
* explicit waits sould be avoided if possible
* getting values from elements like text, size, etc. should also be avoided

### Avoid explicit waits

Let's consider filling a field when some loading operation is running. If we want to ensure that
we don't fill that field until the loading operation completes, one possible solution would be:

```javascript
// elements
field = $("#somefield");
loading = $(".loading");
// interactions
loading.waitForUnexistence();
field.fill("Minium can do better than this");
```

However, we can simplify that code by just considering a different expression for `field`: if the expression
only evaluates into `#somefield` element when `.loading` element doesn't exist, we can avoid having an explicit
wait:

```javascript
// elements
field = $("#somefield").unless(".loading");
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

```preview
elemText = $("label").waitForExistence().text();
// or getting a style value
backgroundColor = $("label").waitForExistence().css("background-color");
```

### Avoid iterating using `.size()`

Let's say we want to remove all items from a shopping cart, and for that we need to iterate through all the 
corresponding remove buttons and click on it. We could consider the following code for that:

```javascript
shoppingCart = $("#shopping-cart");
removeBtns = shoppingCart.find(".items button").withValue("Remove");
size = removeBtns.size();

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
shoppingCart = $("#shopping-cart");
removeBtns = shoppingCart.find(".items button").withValue("Remove");

while (removeBtns.checkForExistence()) {
  // only clicks the first matching element
  removeBtns.click();
}
```

Yet, that code is not perfect yet: when no more items exist in the shopping cart, Minium will wait until a timeout at
`removeBtns.checkForExistence()` and only then it will return false, breaking the `while` loop.

On the other end, if we try to check immediatelly for the existence of `removeBtns` (with `removeBtns.checkForExistence("immediate")`),
that could cause some problems. Let's, for instance, make things even harder: consider that every time we remove an element of the shopping 
cart, the shopping cart is refreshed dynamically (an element `.loading` will be displayed).

In that case, removing an item would mean that the next iteration using `removeBtns.checkForExistence("immediate")` would not occur, 
because `removeBtns` would not exist until the shopping cart completed refreshing.

So, how do we avoid breaking the loop too soon and avoid waiting the `timeout` period at the end?
The solution is the following:

```javascript
shoppingCart = $("#shopping-cart").unless(".loading");
removeBtns = shoppingCart.find(".items button").withValue("Remove");

while (shoppingCart.waitForExistence().then(removeBtns).checkForExistence("immediate")) {
  // only clicks the first matching element
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

**TODO**

```javascript
base = $(":root").unless(".loading");

field = base.find(":text").withName("somefield");
```