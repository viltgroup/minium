---
title: API Reference

language_tabs:
  - minium

toc_footers:
  - <a href='http://minium.io'>minium.io</a>
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

* Java JDK 1.7+ (required)

* Google Chrome (required)

To install and run Minium, just follow these instructions:
  
```shell
git clone git://github.com/viltgroup/minium.git
cd minium
mvn install -DskipTests=true
```

## Give it a try

```javascript
browser.get("http://www.google.com/ncr");

searchbox = $(":text").withName("q");
searchbox.highlight();

searchbox.fill("minion");

searchbox.sendKeys([ Keys.ENTER ]);

wikipediaResult = $("h3 a").withText("Minion - Wikipedia, the free encyclopedia");
wikipediaResult.click();

firstParagraph = $("#mw-content-text p").first();
firstParagraph.highlight();
```

## Minium + Cucumber


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
