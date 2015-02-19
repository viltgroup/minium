(function () {
  var minium = {};
  var dollarFn = function (browser) {
    return function (selector) {
      return browser.locator().selector(selector);
    };
  };
  
  if (typeof __browser !== 'undefined') {
    var browser = __browser;
    var $       = dollarFn(browser);
    
    minium.browser   = __browser;
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
    browser.$ = dollarFn(browser);
    return browser;
  }
  
  // export minium
  module.exports = minium;
})();