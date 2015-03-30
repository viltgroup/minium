# Minium and Cucumber


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
    When I search for <search_query>
    Then links corresponding to <search_query> are displayed

    Examples:
      | search_query  |
      | Minium Github |
      | Selenium      |
```

### Step 2 - writing steps

```javascript
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

  expect(linkUrls).to.exist();

  linkUrls.forEach(function (linkUrl) {
    var link = links.withAttr("data-href", linkUrl).add(links.withAttr("href", linkUrl));
    expect(link).to.have.size(1);
  });

});
```
