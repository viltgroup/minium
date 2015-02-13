#Keys
## .values()


## .valueOf(name)


## .charAt(index)


## .length()


## .subSequence(startend)


## .toString()


## .chord(value)
`Simulate pressing many keys at once in a "chord". Takes a sequence of Keys.XXXX or strings; appends each of the
 values to a string, and adds the chord termination key (Keys.NULL) and returns the resultant string.

 Note: When the low-level webdriver key handlers see Keys.NULL, active modifier keys (CTRL/ALT/SHIFT/etc) release
 via a keyup event.

 Issue: http://code.google.com/p/webdriver/issues/detail?id=79`



## .chord(value)


## .getKeyFromUnicode(key)
`Get the special key representation, {@link Keys}, of the supplied character if there is one. If there is no
 special key tied to this character, null will be returned.`


Parameter | Description
	--------- | -----------
|key|unicode character code
* **returns:** special key linked to the character code, or null if character is not a special key


## .getKeyCode()

