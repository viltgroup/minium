# Cucumber

Minium uses Cucumber Rhino but with some modifications regarding the way
parameters are passed to the step handlers.

In Cucumber Rhino, passed parameters are `java` objects.

The most important change is related steps that get a `DataTable` as parameter:
because the java version of `DataTable` doesn't make much sense in
javascript, we have wrapped it in a javascript object that is much more similar
to `cucumber-js` or `cucumber-ruby` `DataTable`.

## Data Tables

### `.raw()`

```gherkin
When tic-tac-toe board is:
| X | O | X |
| O | X | O |
| O |   |   |
```

Then `datatable.raw()` would return:

```json
[
  [ "X", "O", "X" ],
  [ "X", "O", "X" ],
  [ "X", "" , ""  ]
]
```

### `.rows()`

Like `.raw()` but skips first row.

```gherkin
Given the following produts exist:
| name         | code    | price  |
| Hubsan X4    | 0001234 | $34.99 |
| Raspberry Pi | 0009876 | $42.99 |
```

Then `datatable.rows()` would return:

```json
[
  [ "Hubsan X4", "0001234", "$34.99" ],
  [ "Raspberry Pi", "0009876", "$42.99" ]
]
```


### `.hashes()`

```gherkin
Given the following produts exist:
| name         | code    | price  |
| Hubsan X4    | 0001234 | $34.99 |
| Raspberry Pi | 0009876 | $42.99 |
```

Then `datatable.hashes()` would return:

```json
[{
  "name" : "Hubsan X4",
  "code" : "0001234",
  "price" : "$34.99"
},
{
  "name" : "Raspberry Pi",
  "code" : "0009876",
  "price" : "$42.99"
}
]
```

### `.rowHash()`

Returns a object with the first column as keys and the second column as values.

It is very useful for handling more dynamic data, where the number of properties
is not known *a priori*, and there can lots of properties.

For instance, you can use it in a form filling step:

```gherkin
When I fill:
| Name       | Minium                                 |
| Home Page  | http://minium.vilt.io                  |
| Repository | http://www.github.com/viltgroup/minium |
```

```javascript
When(/^I fill:$/, function (datatable) {
  var formVals = datatable.rowHash();
  // formVals will be a object { "Name" : "Minium", "Home Page" : ... }
  for (var field in formVals) {
    var val = formVals[field];
    var field = $("input, select, textarea").withLabel(field).waitForExistence();
    if (field.is(":checkbox, :radio")) {
      if (val === "true") field.click();
    } else if (field.is("select")) {
      field.select(val);
    } else {
      // fallback, just fill
      field.fill(val);
    }
  }
});
```
