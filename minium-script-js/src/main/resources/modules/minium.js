(function () {
  var minium = {};
  var dollarFn = function (browser) {
    return function () {
      if (arguments.length === 1 && (typeof arguments[0] === 'string' || arguments[0] instanceof Packages.java.lang.String)) {
        return browser.root().find(arguments[0]);
      } else {
        return browser.of(Array.prototype.slice.call(arguments));
      }
    };
  };
  
  if (typeof __browser !== 'undefined') {
    var browser = __browser;
    var $ = dollarFn(browser);
    
    minium.browser = __browser;
    minium.$ = $;
    minium.browser.$ = $;
  }

  var InteractionListeners = Packages.minium.actions.InteractionListeners;
  var WebInteractionListeners = Packages.minium.web.actions.WebInteractionListeners;
  
  minium.interactionListeners = {
    slowMotion : InteractionListeners.slowMotion,
    onTimeout : WebInteractionListeners.onTimeout,
    onUnhandledAlert : WebInteractionListeners.onUnhandledAlert,
    onStaleElementReference : WebInteractionListeners.onStaleElementReference
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