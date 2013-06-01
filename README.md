![Minium banner](minium.png)

# Minium - Quick start

## Preconditions

Ensure that the following software is installed:

* [Java JDK 1.6+](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (required)
  * Don't forget to set `JAVA_HOME` environment variable 
* [Maven 3](http://maven.apache.org/download.cgi)
* [Mozilla Firefox](http://www.mozilla.org/en-US/firefox/new/)
* [Google Chrome](https://www.google.com/intl/en/chrome/browser/) (recommended)

## Building

Building Minium is not complicated. First, clone Minium git repository:

```bash
git clone git://github.com/viltgroup/minium.git
```

Enter the minium folder and build it with Maven:

```bash
cd minium
mvn package -DskipTests=true
```

This will generate all necessary binaries.

## Installing and running Minium Console

* Unzip the bundled Jetty with minium-webconsole found at 
`minium-webconsole-jetty/target/minium-webconsole-jetty-${project.version}.zip`
to some folder
* You can then launch Minium Console server:

```bash
java -jar start.jar
```

* Open Chrome and navigate to [http://localhost:8080/minium-webconsole](http://localhost:8080/minium-webconsole)
* Use Chrome to create a shortcut for Minium Console (Tools > Create application shortcuts...)

## Test it

Just type:

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

Run the code, by selecting it and pressing `Ctrl+ENTER`.
