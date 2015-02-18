---
title: API Reference

language_tabs:
  - javascript

toc_footers:
  - <a href='http://minium.vilt.io'>minium.io</a>
  - <a href='https://github.com/viltgroup/minium'>Github</a>
  - <a href='http://github.com/tripit/slate'>Documentation Powered by Slate</a>

# API
includes:
  - /api/Elements
  - /api/Interactions

search: true

---

# Introduction


## Setup

### Before you start

Ensure that the following software is installed:

* [Java JDK 1.7+](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (**required**)
  * Don't forget to set `JAVA_HOME` environment variable 
* [Google Chrome](https://www.google.com/intl/en/chrome/browser/) (**required**)


### To install and run Minium App, just follow these instructions:

* Download one of the following compressed files:
  * [Zip archive](https://oss.sonatype.org/content/repositories/releases/com/vilt-group/minium/minium-app/0.9.6/minium-app-0.9.6-bin.zip)
  * [Compressed tarball archive](https://oss.sonatype.org/content/repositories/releases/com/vilt-group/minium/minium-app/0.9.6/minium-app-0.9.6-bin.tar.gz)
* Uncompress it in some folder (e.g. `c:\Tools\minium-app`)
* By default, Selenium (and therefore Minium) can run Firefox out of the box. For other browsers, you will need specific drivers. Just download the ones you want to use (see links below) and place the corresponding executable files in `drivers` folder:
  * [Chrome Driver]()
  * [IE Driver Server](https://code.google.com/p/selenium/downloads/list)
  * [PhantomJS](http://phantomjs.org/download.html)
* To launch Minium, just run one of the following executables:
  * `minium-app.exe` (in windows)
  * `bin\minium-app.bat` (also in windows, but this way you can see the stdout)
  * `bin\minium-app` (linux or mac)


## Build Minium

Building Minium is not complicated. Ensure that the following software is installed:

* [Java JDK 1.6+](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (required)
  * Don't forget to set `JAVA_HOME` environment variable 
* [Maven 3](http://maven.apache.org/download.cgi)

Then, just clone Minium git repository and use Maven to build it:

```bash
git clone git://github.com/viltgroup/minium.git
cd minium
mvn install -DskipTests
```

**Note:** if you really want to run the tests, then make sure you have [PhantomJS](http://phantomjs.org/download.html) installed and available in the `PATH` environment variable. Then just replace `mvn install -DskipTests` by `mvn install`.

## Example

```javascript
var keys = require('minium/keys');

browser.get("http://www.google.com/ncr");

searchbox = $(":text").withName("q");
searchbox.highlight();

searchbox.fill("minion");

searchbox.sendKeys( keys.ENTER );

wikipediaResult = $("h3 a").withText("Minion - Wikipedia, the free encyclopedia");
wikipediaResult.click();

firstParagraph = $("#mw-content-text p").first();
firstParagraph.highlight();
```

## Minium + Cucumber

Minium can be integrated with Cucumber (Behaviour Driven Development). You can write your tests using cucumber.

### Step 1 - writings scenarios

```gherkin
# language: en
# ------------------------------------------------------------------------------
Feature: Search results in Google

  Scenario: Search something in google
    Given I'm at http://www.google.com/ncr
    When I search for minium github
    Then a link for https://github.com/viltgroup/minium is displayed

  Scenario Outline: Search something in google (results in a JSON file)
    Given I'm at http://www.google.com/ncr
    When I search for 
    Then links corresponding to  are displayed

    Examples: 
      | search_query  |
      | Minium Github |
      | Selenium      |
```

### Step 2 - writing steps

The code to run the scenarios above.

```javascript
var $       = require("minium"),
browser = $.browser,
expect  = require("expect-webelements"),
_       = require("lodash");

Given(/^I'm at (.*)$/, function (url) {
  browser.get(url);
});

When(/^I search for (.*)$/, function (query) {
  var searchbox = $(":input").withName("q");
  var button    = $("button").withAttr("aria-label", "Google Search");
  
  searchbox.fill(query);
  button.click();
});

Then(/^a link for (.*) is displayed$/, function (linkUrl) {
  var links = $("a");
  var link  = links.withAttr("data-href", linkUrl).add(links.withAttr("href", linkUrl));
  expect(link).to.have.size(1);
});

Then(/^links corresponding to (.*) are displayed$/, function (query) {
  var links = $("a");
  var linkUrls = config.searches[query];

  expect(linkUrls).not.to.be.empty();
  
  _(linkUrls).forEach(function (linkUrl) {
    var link = links.withAttr("data-href", linkUrl).add(links.withAttr("href", linkUrl));
    expect(link).to.have.size(1);
  });
  
});
```

## Selector gadget
You can visually select the elements you want minium to interact with.

## Evaluate expressions

## Multiple Browsers

You can open multiple browsers where you can run your code.
