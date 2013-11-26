# Minium [![Build Status](https://travis-ci.org/viltgroup/minium.png)](https://travis-ci.org/viltgroup/minium)

![Minium banner](http://viltgroup.github.io/minium/images/banner_minium.png)

## What is Minium 

Minium is a framework, developed by Rui Figueira @ VILT, that combines jQuery and Selenium for browser testing 
and tasks automation. It also provides an interactive console that easily lets you execute Minium instructions, 
providing you immediate feedback.

In today's JavaScript development landscape, jQuery is the dominant auxiliar library. A major contributor for this
state of affairs is JQuery's powerful element selector engine. Now ubiquous in use, it provides a quick and 
intelligible way to select precisely the elements we want. At the other end of the development spectrum, Selenium 
is currently the most popular automation framework due to its innovative WebDriver API. Minium combines Selenium's 
impressive automation capabilities with jQuery's selector engine to produce a simple way to create elaborate 
automation tasks in the complex web interfaces of today.

## Minium can!

![Minium can!](http://viltgroup.github.io/minium/images/minium_can.png)

You may be thinking: big deal, there are several other tools for browser automation... That's true, but: 

* can those tools access another window (for instance, popup windows) in a easy way? **Minium can!**

```javascript
// will look to ALL opened windows to find a text field with label "Username", and fill it with a value
usernameFld = $(wd).window().find(":text").withLabel("Username");
```

* can those tools easily access iframes? **Minium can!**

```javascript
// will look to ALL frames in the main page to find a text field with label "Username"
usernameFld = $(wd).frame().find(":text").withLabel("Username");
```
* can those tools select elements based on their relative position to other elements? **Minium can!**

![Minium position methods](http://viltgroup.github.io/minium/images/position_selectors.png)

* can those tools 'speak' and teach you how to use themselves? **Minium can!**

[![Minium shows its new Web Console](http://halgatewood.com/youtube/i/QlPLNEJD5rc.jpg)](http://www.youtube.com/watch?v=QlPLNEJD5rc)

* can those tools write their own documentation? **Minium can!**

[![Minium writes its own documentation](http://halgatewood.com/youtube/i/wgAatRpNv_c.jpg)](http://www.youtube.com/watch?v=wgAatRpNv_c)

You can watch all available Minium videos at 
[Youtube playlist](http://www.youtube.com/playlist?list=PLtYR_mxVztvMZuYfgjRe5OAl2WL_mb2N_).

# Quick start

The easiest way to try Minium is to use Minium App, which contains Minium Web Console (you can watch the video ['Minium shows its new Web Console'](http://www.youtube.com/watch?v=QlPLNEJD5rc) for a small introducion) and a bundled Jetty server.
This way, you can instruct Minium to do almost anything in a browser with a few lines of Javascript.

## Before you start

Ensure that the following software is installed:

* [Java JDK 1.6+](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (**required**)
  * Don't forget to set `JAVA_HOME` environment variable 
* [Google Chrome](https://www.google.com/intl/en/chrome/browser/) (**required**)
* Specific browser drivers (recomended, only Firefox is included in Selenium Web Driver OOTB)
  * [Chrome Driver](http://chromedriver.storage.googleapis.com/index.html)
  * [IE Driver Server](https://code.google.com/p/selenium/downloads/list)
  * [PhantomJS (headless WebKit)](http://phantomjs.org/download.html)

Ensure that those web driver binaries are available in your `PATH` environment variable, otherwise Minium won't be able to launch them.
  
## Run Minium App

To install and run Minium App, just follow these instructions:

* Download one of the following compressed files:
  * [Zip archive](https://oss.sonatype.org/content/repositories/releases/com/vilt-group/minium/minium-app/0.9.1/minium-app-0.9.1-bin.zip)
  * [Compressed tarball archive](https://oss.sonatype.org/content/repositories/releases/com/vilt-group/minium/minium-app/0.9.1/minium-app-0.9.1-bin.tar.gz)
* Uncompress it in some folder (e.g. `c:\Tools\minium-app`)
* Run one of the following executables:
  * `minium-app.exe` (in windows)
  * `bin\minium-app.bat` (also in windows, but this way you can see the stdout)
  * `bin\minium-app` (linux or mac)

If Minium doesn't open a chrome app when you execute any of those scripts, probably you'll need to indicate where your chrome binary is in `app.properties` (edit specify the full Chrome binary path in the `chrome.bin` property).

## Give it a try

You're now able to create a web driver. Just go to `Web Drivers`, pick your prefered browser and name it `wd`. Then, just type the following code and run it by selecting it and pressing `Ctrl+ENTER`:

```javascript
get($(wd), "http://www.google.com/ncr");

speak("Hello, I'm Minium, and I'm alive");
speak("Let me highlight google search box");

searchbox = $(wd, ":text").withName("q");
highlight(searchbox);

speak("Minium = Minion + Selenium. Let's find out what is a Minion.");

fill(searchbox, "Minion site:wikipedia.org");
sendKeys(searchbox, [ Keys.ENTER ]);

firstResult = $(wd, "h3 a").first();
click(firstResult);

firstParagraph = $(wd, "#mw-content-text p").first();
highlight(firstParagraph);

speak("Wikipedia says: " + firstParagraph.text());
```

Or try this other script:

```javascript
get(wd, "https://docs.google.com/spreadsheet/ccc?key=0Al0ulrJIDCUVdEhoSDlRbVZYWUt5ZVJCb1pVb0h1UFE");

var sheetTabs = $(wd, ".docs-sheet-tab-name");
var numFrames = sheetTabs.size();

for (var time = 0; time < 5 * numFrames; time++) {
  var sheetTab = sheetTabs.eq(time % numFrames);
  click(sheetTab);
}
```

# Build Minium

Building Minium is not complicated. Ensure that the following software is installed:

* [Java JDK 1.6+](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (required)
  * Don't forget to set `JAVA_HOME` environment variable 
* [Maven 3](http://maven.apache.org/download.cgi)

Then, just clone Minium git repository and use Maven to build it:

```bash
git clone git://github.com/viltgroup/minium.git
cd minium
mvn install -DskipTests=true
```

**Note:** if you really want to run the tests, then make sure you have [PhantomJS](http://phantomjs.org/download.html) installed and available in the `PATH` environment variable. Then just replace `mvn install -DskipTests=true` by `mvn install`.

# Documentation

A quick guide on how to use Minium in a Java project or using its Interactive console can be found here:

* [Learning Minium in 6 Steps](https://github.com/viltgroup/minium/wiki/Learning-Minium-in-6-Steps)

If you feel that's too simple, check this one:

* [Minium creates a Google Spreadsheet](https://github.com/viltgroup/minium/wiki/Minium-creates-a-Google-Spreadsheet)

You can also check the [Minium API documentation](http://viltgroup.github.io/minium/apidocs/). For a complete list of allowed methods, check the links below.

## Available element selection methods

* [JQueryWebElements](http://viltgroup.github.io/minium/apidocs/com/vilt/minium/JQueryWebElements.html)
* [FiltersWebElements](http://viltgroup.github.io/minium/apidocs/com/vilt/minium/FiltersWebElements.html)
* [PositionWebElements](http://viltgroup.github.io/minium/apidocs/com/vilt/minium/PositionWebElements.html)
* [TargetLocatorWebElements](http://viltgroup.github.io/minium/apidocs/com/vilt/minium/TargetLocatorWebElements.html)

## Available interactions

* [Interactions](http://viltgroup.github.io/minium/apidocs/com/vilt/minium/actions/Interactions.html)
* [DebugInteractions](http://viltgroup.github.io/minium/apidocs/com/vilt/minium/actions/DebugInteractions.html)
* [TipInteractions](http://viltgroup.github.io/minium/apidocs/com/vilt/minium/actions/TipInteractions.html)
* [TouchInteractions](http://viltgroup.github.io/minium/apidocs/com/vilt/minium/actions/touch/TouchInteractions.html)

# License

Minium is licensed under [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
