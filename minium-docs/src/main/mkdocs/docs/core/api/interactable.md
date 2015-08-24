# Interactable

<p>Objects that can perform interactions are known as Interactable. Typically, all Elements / WebElements are interactable. Interactable interfaces normally "hide" interactions behind methods like .click() or .fill().</p> 
<p>This is the root interface for all other `Interactable` interfaces such as `MouseInteractable` or `KeyboardInteractable`. It makes available generic methods to perform interactions on the corresponding elements.</p>

## `.perform(interaction)`

Performs the specified interaction on the corresponding elements.

Parameter | Description
--------- | -----------
interaction | the interaction to perform

- **returns:** this `Interactable`

## `.performAndWait(interaction)`

Performs the specified asynchronous interaction on the corresponding elements, and waits until it completes.

Parameter | Description
--------- | -----------
interaction | the asynchronous interaction

- **returns:** this `Interactable`

# MouseInteractable

Mouse interactions can be performed using this interactable interface. It "hides" mouse interactions under its methods. It mimics most of mouse actions in `org.openqa.selenium.interactions.Actions`.

## `.click()`

Clicks in the middle of the first matched element.

- **returns:** this `Interactable`

## `.clickAndHold()`

Clicks (without releasing) at the current mouse location.

- **returns:** this `Interactable`

## `.contextClick()`

Performs a context-click at middle of the first matched element.

- **returns:** this `Interactable`

## `.doubleClick()`

Performs a double-click at middle of the first matched element.

- **returns:** this `Interactable`

## `.dragAndDrop(target)`

A convenience method that performs click-and-hold at the location of the first matched source element, moves to the location of the first matched target element, then releases the mouse.

Parameter | Description
--------- | -----------
target | `Elements` expression to move to and release the mouse at.

- **returns:** this `Interactable`

## `.moveTo()`

Moves the mouse to the middle of the first matched element.

- **returns:** this `Interactable`

## `.release()`

Releases the pressed left mouse button at the current mouse location.

- **returns:** this `Interactable`

# KeyboardInteractable

Keyboard interactions can be performed using this interactable interface. It "hides" keyboard interactions under its methods. It mimics most of keyboard actions in org.openqa.selenium.interactions.Actions.

## `.clear()`

Clears all input text in the first matched field.

- **returns:** this `Interactable`

## `.fill(text)`

Same as `#clear()` followed by `#sendKeys(CharSequence)`

Parameter | Description
--------- | -----------
text | text to type

- **returns:** this `Interactable`

## `.keyDown(keys)`

Performs a modifier key press. Does not release the modifier key - subsequent interactions may assume it's kept pressed. Note that the modifier key is never released implicitly - either 
<code>keyUp(theKey)</code> or 
<code>sendKeys(Keys.NULL)</code> must be called to release the modifier.

Parameter | Description
--------- | -----------
keys | either `Keys.SHIFT`, `Keys.ALT` or `Keys.CONTROL`. If the provided key is none of those, `IllegalArgumentException` is thrown

- **returns:** this `Interactable`

## `.keyUp(keys)`

Performs a modifier key press after focusing on an element.

Parameter | Description
--------- | -----------
keys | either `Keys.SHIFT`, `Keys.ALT` or `Keys.CONTROL`. If the provided key is none of those, `IllegalArgumentException` is thrown

- **returns:** this `Interactable`

## `.sendKeys(keys)`

Sends keys to this element.

Parameter | Description
--------- | -----------
keys | keys to send

- **returns:** this `Interactable`

## `.type(text)`

Same as `#sendKeys(CharSequence)`

Parameter | Description
--------- | -----------
text | text to type

- **returns:** this `Interactable`

# WaitInteractable

Provides wait interaction methods for Minium expressions.

## `.checkForExistence()`

Waits for the existence of the elements expression, that is, that it evaluates into a non-empty set before a timeout occurs. It will return true in case it succeeds, false otherwise. No `TimeoutException` is thrown in this method.

- **returns:** true if it evaluates into some element, false otherwise

## `.checkForUnexistence()`

Waits for the unexistence of the elements expression, that is, that it evaluates into a empty set before a timeout occurs. It will return true in case it succeeds, false otherwise. No `TimeoutException` is thrown in this method.

- **returns:** true if it doesn't evaluates into some element, false otherwise

## `.waitForExistence()`

Waits for the existence of the elements expression, that is, that it evaluates into a non-empty set before a timeout occurs. It will fail with a `TimeoutException` if no element matches this expression.

- **returns:** this `Interactable`

## `.waitForUnexistence()`

Waits for the unexistence of the elements expression, that is, that it evaluates into a empty set before a timeout occurs. It will fail with a `TimeoutException` if some element matches this expression.

- **returns:** this `Interactable`

## `.waitTime(time, timeUnit)`

Waits for the specified time interval.

Parameter | Description
--------- | -----------
time | time to wait
timeUnit | time units for the specified time

- **returns:** this `Interactable`

# WebInteractable

Web-specific interactions that can be performed using this interactable interface. It "hides" web-specific interactions under its methods.

## `.check()`

Checks the corresponding input, in case it is unchecked.

- **returns:** this `Interactable`

## `.close()`

Closes the browser window for the corresponding element.

- **returns:** this `Interactable`

## `.deselect(text)`

Deselects the option with the corresponding text.

Parameter | Description
--------- | -----------
text | text of the option to deselect

- **returns:** this `Interactable`

## `.deselectAll()`

Deselects all options (for multi-select field).

- **returns:** this `Interactable`

## `.deselectVal(value)`

Deselects the option with the corresponding value.

Parameter | Description
--------- | -----------
value | value of the option to deselect

- **returns:** this `Interactable`

## `.scrollIntoView()`

Scrolls the corresponding element into view.

- **returns:** this `Interactable`

## `.select(text)`

Selects the option with the corresponding text.

Parameter | Description
--------- | -----------
text | text of the option to select

- **returns:** this `Interactable`

## `.selectAll()`

Selects all options (for multi-select field).

- **returns:** this `Interactable`

## `.selectVal(value)`

Selects the option with the corresponding value.

Parameter | Description
--------- | -----------
value | value of the option to select

- **returns:** this `Interactable`

## `.submit()`

Submits the corresponding form. Mimics `org.openqa.selenium.WebElement#submit()`.

- **returns:** this `Interactable`

## `.uncheck()`

Unchecks the corresponding input, in case it is checked.

- **returns:** this `Interactable`

