![Minium banner](http://viltgroup.github.io/minium/images/banner_minium.png)

[![Build Status](https://travis-ci.org/viltgroup/minium.png)](https://travis-ci.org/viltgroup/minium)

# What is Minium 

Minium is a framework, developed by Rui Figueira @ VILT, that combines jQuery and Selenium for browser testing 
and tasks automation. It also provides an interactive console that easily lets you execute Minium instructions, 
providing you immediate feedback.

In today's JavaScript development landscape jQuery is the dominant auxiliar library. A major contributor for this
state of affairs is JQuery's powerful element selector engine. Now ubiquous in use it provides a quick and 
intelligible way to select precisely the elements we want. At the other end of the development spectrum Selenium 
is currently the most popular automation framework due to its innovative WebDriver API. Minium combines Selenium's 
impressive automation capabilities with jQuery's selector engine to produce a simple way to create elaborate 
automation tasks in the complex web interfaces of today.

# "Can Minium do that?!"

You may be thinking: big deal, there are several other tools for browser automation... That's true, but: 

* can those tools select elements based on their relative position to other elements? **Minium can!**

![Minium position methods](http://viltgroup.github.io/minium/images/position_selectors.png)

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
* can those tools 'speak'? **Minium can!**

[![Minium shows its Interactive Console](http://halgatewood.com/youtube/i/Q7SH216qGko.jpg)](http://www.youtube.com/watch?v=Q7SH216qGko)

* can those tools write their own documentation? **Minium can!**

[![Minium writes its own documentation](http://halgatewood.com/youtube/i/wgAatRpNv_c.jpg)](http://www.youtube.com/watch?v=wgAatRpNv_c)

You can watch all available Minium videos at 
[Youtube playlist](http://www.youtube.com/playlist?list=PLtYR_mxVztvMZuYfgjRe5OAl2WL_mb2N_).

# Quick start

The easiest way to try Minium is to use its Interactive Console (you can watch the video ['Minium shows its Interactive Console'](http://www.youtube.com/watch?v=Q7SH216qGko) for a small introducion). You can instruct Minium to do almost anything in a browser with a few lines of Javascript.

Right now, the console is a web application and must run in a servlet container like Tomcat or Jetty. Minium generates a zip containing Jetty and minium console under minium-webconsole-jetty, so you only need to uncompress it and launch it to get started.

## Before you start

Ensure that the following software is installed:

* [Java JDK 1.6+](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (required)
  * Don't forget to set `JAVA_HOME` environment variable 
* [Maven 3](http://maven.apache.org/download.cgi)
* [Mozilla Firefox](http://www.mozilla.org/en-US/firefox/new/)
* [Google Chrome](https://www.google.com/intl/en/chrome/browser/) (recommended)

## Build Minium

Building Minium is not complicated. Just clone Minium git repository and use Maven to build it:

```bash
git clone git://github.com/viltgroup/minium.git
cd minium
mvn install -DskipTests=true
```

**Note:** if you really want to run the tests, then make sure you have [PhantomJS](http://phantomjs.org/download.html) installed and available in the `PATH` environment variable. Then just replace `mvn install -DskipTests=true` by `mvn install`.

## Run Minium Console

The fastest way to launch the Minium Console is to use Jetty Maven plugin. For that, just execute:

```bash
cd minium-webconsole
mvn jetty:run
```
Then just open the console in a browser. If you have Chrome installed:

* Open Chrome and navigate to [http://localhost:8080/minium-webconsole](http://localhost:8080/minium-webconsole)
* Use Chrome to create a shortcut for Minium Console (Tools > Create application shortcuts...)

## Give it a try

Just type the following code and run it by selecting it and pressing `Ctrl+ENTER`:

```javascript
wd = firefoxDriver();

get($(wd), "http://www.google.com");

speak("Hello, I'm Minium, and I'm alive");
speak("Let me highlight google search box");

searchbox = $(wd, ":text").withName("q");
highlight(searchbox);

speak("Minium = Minion + Selenium. Let's find out what is a Minion.");

fill(searchbox, "Minion");
sendKeys(searchbox, [ Keys.ENTER ]);

firstResult = $(wd, "h3 a").first();
click(firstResult);

firstParagraph = $(wd, "#mw-content-text p").first();
highlight(firstParagraph);

speak("Wikipedia says: " + firstParagraph.text());
```

# Documentation

A quick guide on how to use Minium in a Java project or using its Interactive console can be found here:

* [Learning Minium in 6 Steps](https://github.com/viltgroup/minium/wiki/Learning-Minium-in-6-Steps)

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
