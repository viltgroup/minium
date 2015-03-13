After(function (scenario) {
  if (scenario.isFailed()) {
    if (typeof browser !== 'undefined') {
      scenario.embed(browser.screenshot().asBytes(), "image/png");     
    }
  }
});