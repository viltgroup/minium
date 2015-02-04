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
