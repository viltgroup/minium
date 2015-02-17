(function () {
  var minium = {};
  var dollarFn = function (locator) {
    return function (selector) {
      return locator.selector(selector);
    };
  };
  
  if (typeof __browser !== 'undefined') {
    var by = __browser.locator();
    var $  = dollarFn(by);
    
    minium.browser   = __browser;
    minium.by        = by;
    minium.$         = $;
    minium.browser.$ = $;
  }

  var InteractionListeners    = Packages.minium.actions.InteractionListeners;
  var WebInteractionListeners = Packages.minium.web.actions.WebInteractionListeners;
  
  minium.interactionListeners = {
    waitOnTimeout  : InteractionListeners.waitOnTimeout,
    slowMotion     : InteractionListeners.slowMotion,
    retry          : InteractionListeners.retry,
    unhandledAlert : WebInteractionListeners.unhandledAlert
  };

  // new browsers
  minium.newBrowser = function(config) {
    var browser = minium.__browserFactory.create(config || {});
    browser.$ = dollarFn(browser.locator());
    return browser;
  }
  
  // export minium
  module.exports = minium;
})();