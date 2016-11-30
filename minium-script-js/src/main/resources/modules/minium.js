(function () {
  var minium = {};

  var wrap = function (obj, props) {
    var wrapper = {};
    props = props || Object.keys(props);
    props.forEach(function (prop) {
   	  var fn = obj[prop];
      if (typeof fn === 'function') {
        wrapper[prop] = function () {
          return fn.apply(obj, Array.prototype.slice.call(arguments));
        };
      }
    });
    return wrapper;
  };
  
  var wrapBrowser = function (wrapped) {
    var browser = wrap(wrapped, [ "root", "$", "get", "getCurrentUrl", "getTitle", "close", "quit", "navigate", "configure", "screenshot", "toString" ]);
    // for $ function with multiple arguments, we don't want to call it like $([ $("a"), $("input") ])
    browser.$ = function () {
        if (arguments.length === 1 && typeof arguments[0] === 'string') {
            return wrapped.$(arguments[0]);
        } else {
            return wrapped.$.call(wrapped, Array.prototype.slice.call(arguments));
        }
    };
    // we'll use $.fn for minium javascript plugins (like jQuery)
    browser.$.fn = {};
    return browser;
  };
  
  if (typeof __browser !== 'undefined') {
    var browser = wrapBrowser(__browser);
    minium.browser = browser;
    minium.$ = browser.$;
    minium.$.fn = __prototype;
  }

  var InteractionListeners = Packages.minium.actions.InteractionListeners;
  var WebInteractionListeners = Packages.minium.web.actions.WebInteractionListeners;
  
  minium.interactionListeners = {
    slowMotion : InteractionListeners.slowMotion,
    onTimeout : WebInteractionListeners.onTimeout,
    onUnhandledAlert : WebInteractionListeners.onUnhandledAlert,
    onStaleElementReference : WebInteractionListeners.onStaleElementReference,
    onException : WebInteractionListeners.onException,
    ensureExistence : WebInteractionListeners.ensureExistence,
    ensureUnexistence : WebInteractionListeners.ensureUnexistence
  };

  // new browsers
  minium.newBrowser = function(config) {
    var browser = minium.__browserFactory.create(config || {});
    return wrapBrowser(browser);
  }
  
  // export minium
  module.exports = minium;
})();