# Assertions

Minium script uses [expect.js](https://github.com/Automattic/expect.js) for
assertions.

We extended it to provide methods that are suitable for Minium elements
expressions.

## `.to.exist()`

Asserts that the corresponding Minium elements expression exists, that is,
it evaluates into a non-empty set of elements. For unexistence assertion, use
`.not.to.exist()` instead.

```javascript
// check for existence
expect($("#some-element")).to.exist();

// check for unexistence
expect($("#some-element")).not.to.exist();
```

## `.to.have.text(text)`

Asserts that some element evaluated by the corresponding Minium elements
expression has the given text.

```javascript
expect($("h1")).to.have.text("Minium can!");

expect($("h1")).not.to.have.text("Minium can't...");
```

## `.to.have.size(size)`

Asserts that Minium elements expression evaluates into a set with given size.

```javascript
expect($("h1")).to.have.size(1);

expect($("h1")).not.to.have.size(10);
```

## `.to.have.val(val)`

Asserts that some element evaluated by the corresponding Minium elements
expression has the given value.

```javascript
expect($("#some-field")).to.have.val("Minium can!");

expect($("#some-field")).not.to.have.val("Minium can't...");
```

## `.to.have.attr(name [, val ])`

Asserts that some element evaluated by the corresponding Minium elements
expression has an attribute. In case `val` is provided, it checks if the
attribute has that value.

```javascript
expect($("img")).to.have.attr("alt");
expect($("img")).to.have.attr("alt", "nice picture");

expect($("img")).not.to.have.attr("alt");
expect($("img")).not.to.have.attr("alt", "so-so picture");
```

## `.to.have.css(name [, val])`

Asserts that some element evaluated by the corresponding Minium elements
expression has a css property. In case `val` is provided, it checks if the
css property has that value.

```javascript
expect($("img")).to.have.css("visibility");
expect($("img")).to.have.css("visibility", "visible");

expect($("img")).not.to.have.css("visibility");
expect($("img")).not.to.have.css("visibility", "hidden");
```
