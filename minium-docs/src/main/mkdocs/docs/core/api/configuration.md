# WaitingPreset

A waiting preset is basically a named pair of timeout and interval values. They are very convenient to reflect specific interaction waiting periods (for instance, some tasks may require a bigger timeout period, so one can create a "slow" waiting preset for that.

## `.done()`

Goes back to the corresponding configuration.

- **returns:** this `Configuration`

## `.interval(interval)`

Sets this waiting preset timeout.

Parameter | Description
--------- | -----------
interval | the interval period for this waiting preset

- **returns:** this waiting preset

## `.reset()`

Resets this waiting preset interval, that is, both timeout and interval periods will be the default values for the corresponding configuration.

- **returns:** this waiting preset

## `.timeout(timeout)`

Sets this waiting preset timeout.

Parameter | Description
--------- | -----------
timeout | the timeout period for this waiting preset

- **returns:** this waiting preset

# InteractionListenerCollection

Handles interaction listeners registration and unregistration, as well as accessing all registered interaction listeners.

## `.add(interactionListener)`

Adds a new interaction listener. If the same interaction listerer is added twice, it will only be called once (`.equals()` is used for comparison).

Parameter | Description
--------- | -----------
interactionListener | interaction listener to add

- **returns:** this interaction listener collection

## `.clear()`

Removes all interaction listeners from this configuration.

- **returns:** this interaction listener collection

## `.done()`

Goes back to the corresponding configuration.

- **returns:** this `Configuration`

## `.remove(interactionListener)`

Removes an existing interaction listener (`.equals()` is used for comparison). If the listener does not exist, there is no state change.

Parameter | Description
--------- | -----------
interactionListener | interaction listener to remove

- **returns:** this interaction listener collection

# CookieCollection

<p>Maintains all cookies for this browser. It is possible to access cookies, as well as add and remove them. This is a Minium adapter for `org.openqa.selenium.WebDriver.Options`</p> 
<p>This is a chainable interface (to go back to the browser `WebConfiguration`, call `.done()`).</p>

## `.add(cookie)`

Adds a cookie to this browser.

Parameter | Description
--------- | -----------
cookie | the cookie to add

- **returns:** this `CookieCollection`

## `.clear()`

Removes all cookies from this browser.

- **returns:** this `CookieCollection`

## `.done()`

Goes back to this browser `WebConfiguration`.

- **returns:** this browser `WebConfiguration`

## `.get(name)`

Gets a cookie by name.

Parameter | Description
--------- | -----------
name | name of the cookie to get

- **returns:** this `CookieCollection`

## `.remove(name)`

Removes a cookie from this browser.

Parameter | Description
--------- | -----------
name | name of the cookie we want to remove.

- **returns:** this `CookieCollection`

# Window

This is a Minium adapter for `org.openqa.selenium.WebDriver.Window`.

## `.getPosition()`

Get the position of the current window, relative to the upper left corner of the screen.

- **returns:** the current window position.

## `.getSize()`

Get the size of the current window. This will return the outer window dimension, not just the view port.

- **returns:** the current window size

## `.maximize()`

Maximizes the current window if it is not already maximized.


## `.setPosition(targetPosition)`

Set the position of the current window. This is relative to the upper left corner of the screen, synonymous to `window.moveTo()` in javascript.

Parameter | Description
--------- | -----------
targetPosition | the target position of the window.

- **returns:** this `Window`

## `.setSize(targetSize)`

Set the size of the current window. This will change the outer window dimension, not just the view port, synonymous to `window.resizeTo()` in javascript.

Parameter | Description
--------- | -----------
targetSize | the target size.

- **returns:** this `Window`

# Configuration

Fluent API for Minium configuration. For instance, you can write code like this: 
<pre>
 configuration
   .defaultTimeout(2, TimeUnit.SECONDS)
   .defaultInterval(1, TimeUnit.SECONDS)
   .waitingPreset("slow")
     .timeout(20, TimeUnit.SECONDS)
     .interval(5, TimeUnit.SECONDS)
   .done()
   .waitingPreset("fast")
     .timeout(1, TimeUnit.SECONDS)
     .interval(200, TimeUnit.MILLISECONDS)
   .done()
   .interactionListeners()
     .add(slowMotion(2, TimeUnit.SECONDS))
     .add(retry())
   .done()
 </pre>

## `.defaultInterval()`

Gets the default interaction interval.

- **returns:** the default interaction interval

## `.defaultTimeout()`

Gets the default interaction timeout.

- **returns:** the default interaction timeout

## `.exceptionHandlers()`

Gets the exception handlers collection so that exception handlers can be added or removed.

- **returns:** the exception handlers collection

## `.interactionListeners()`

Gets the interaction listeners collection so that listeners can be added or removed.

- **returns:** the interaction listeners collection

## `.waitingPreset(preset)`

Gets the waiting preset corresponding to the passed preset value. That waiting can be used to access or update both interval and timeout periods. If that waiting preset was never configure, both interval and timeout periods will be the default ones.

Parameter | Description
--------- | -----------
preset | the waiting preset name

- **returns:** the corresponding waiting preset (never null)

# WebConfiguration

Extends `Configuration` by allowing cookies and browser windows management.

## `.cookies()`

Gets the cookies collection so that cookies can be added or removed.

- **returns:** the cookies collection

## `.window()`

Gets the window manager object, which allows window position and dimensions to be controlled.

- **returns:** this configuration `Window`

