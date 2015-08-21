# Navigation

<p>This is a Minium adapter for `org.openqa.selenium.WebDriver.Navigation`</p>

## `.back()`

Move back a single "item" in the browser's history.

- **see:** org.openqa.selenium.WebDriver.Navigation#back()

## `.forward()`

Move a single "item" forward in the browser's history. Does nothing if we are on the latest page viewed.

- **see:** org.openqa.selenium.WebDriver.Navigation#forward()

## `.refresh()`

Refresh the current page.


## `.to(url)`

Load a new web page in the current browser window. This is done using an HTTP GET operation, and the method will block until the load is complete. This will follow redirects issued either by the server or as a meta-redirect from within the returned HTML. Should a meta-redirect "rest" for any duration of time, it is best to wait until this timeout is over, since should the underlying page change whilst your test is executing the results of future calls against this interface will be against the freshly loaded page.

Parameter | Description
--------- | -----------
url | the URL to load. It is best to use a fully qualified URL

- **see:** org.openqa.selenium.WebDriver.Navigation#to(String)

# Screenshot

<p>Allows screenshots to be taken from this browser. Images can be stored in a file or in memory. In both cases, they are stored in <code>png</code> format. It is a Minium adapter for Selenium `org.openqa.selenium.TakesScreenshot`</p>

## `.asBytes()`

Gets screenshot data in memory as a byte array in 
<code>png</code> format.

- **returns:** byte array with image data in `png` format

## `.asFile()`

Gets screenshot as a file. Notice that, as described in `org.openqa.selenium.OutputType#FILE`, file is deleted on exit, so ensure that you either read data to another structure or you just copy the file elsewhere.

- **returns:** file file with `png` format.

## `.saveTo(file)`

Saves screenshot data to a specified file. In case you need to keep the file after this process exits, this is the recommended way.

Parameter | Description
--------- | -----------
file | file where screenshot will be saved with `png` format.


# Browser

Browser can be seen as a `org.openqa.selenium.WebDriver` wrapper.

## `.$(selector)`




## `.close()`




## `.configure()`




## `.get(url)`




## `.getCurrentUrl()`




## `.getTitle()`




## `.navigate()`




## `.quit()`




## `.root()`




## `.screenshot()`




