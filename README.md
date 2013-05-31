# Minium - Quick start

## Preconditions

Ensure that the following software is installed:

* [Java JDK / JRE 1.6+](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (required)
* [Maven 3](http://maven.apache.org/download.cgi)
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

* Install [chromedriver](https://code.google.com/p/chromedriver/downloads/list)
  * **Note:** install chromedriver, not chromedriver2! (for now)
* Make sure chromedriver can be located on your `PATH` environment variable
* Unzip the bundled Jetty with minium-webconsole found at 
`minium-webconsole-jetty/target/minium-webconsole-jetty-${project.version}.zip`
to some folder
* You can then launch Minium Console server:

```bash
java -jar start.jar
```

* Open Chrome and navigate to [http://localhost:8080/minium-webconsole](http://localhost:8080/minium-webconsole)
* Use Chrome to create a shortcut for Minium Console (Tools > Create application shortcuts...)
