# Position and Offsets

## Positional filters

Minium provides positional filters for Minium elements expressions, such as
`.leftOf()`, `.below()` or `.overlaps()`. Those methods actually use the
rendered position in the page to determine if elements can be found relative to
others.

```javascript
var header = $("th").withText("Code");

var cell = $("td").withText("A14").below(header);
var deleteBtn = $("button").rightOf(cell);
```

## Offsets

```javascript
var offsets = require("minium/offsets");

var offset = offsets.at("left+20px center-25%");
$("#some-element").click(offset);
```

```javascript
var offset = offsets.at(
  offsets.horizontal.top.plus(20).pixels(),
  offsets.vertical.middle.plus(20).percent());
```
