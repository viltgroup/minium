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
  
  if (typeof __browser !== 'undefined') {
  	var wrapped = __browser;
    var browser = wrap(wrapped, [ "root", "$", "get", "getCurrentUrl", "getTitle", "close", "quit", "navigate", "configure", "screenshot", "toString" ]);
    // for $ function with multiple arguments, we don't want to call it like $([ $("a"), $("input") ])
    browser.$ = function () {
    	if (arguments.length === 1 && typeof arguments[0] === 'string') {
    		return wrapped.$(arguments[0]);
    	} else {
    		return wrapped.$.call(wrapped, Array.prototype.slice.call(arguments));
    	}
    };
    minium.browser = browser;
    minium.$ = browser.$;
  }

  var InteractionListeners = Packages.minium.actions.InteractionListeners;
  var WebInteractionListeners = Packages.minium.web.actions.WebInteractionListeners;
  
  minium.interactionListeners = {
    slowMotion : InteractionListeners.slowMotion,
    onTimeout : WebInteractionListeners.onTimeout,
    onUnhandledAlert : WebInteractionListeners.onUnhandledAlert,
    onStaleElementReference : WebInteractionListeners.onStaleElementReference,
    onException : WebInteractionListeners.onException
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