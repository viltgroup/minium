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

  var Keys = Packages.minium.actions.Keys;
  
  minium.keys = Keys;
  
  var InteractionListeners    = Packages.minium.actions.InteractionListeners;
  var WebInteractionListeners = Packages.minium.web.actions.WebInteractionListeners;
  
  minium.interactionListeners = {
    waitOnTimeout  : InteractionListeners.waitOnTimeout,
    slowMotion     : InteractionListeners.slowMotion,
    retry          : InteractionListeners.retry,
    unhandledAlert : WebInteractionListeners.unhandledAlert
  };
  
  // offsets
  var HorizontalReference = Packages.minium.Offsets.HorizontalReference;
  var VerticalReference   = Packages.minium.Offsets.VerticalReference;
  
  minium.offsets = {
    at : Packages.minium.Offsets.at,
    horizontal : {
      left      : HorizontalReference.LEFT,
      center    : HorizontalReference.CENTER,
      right     : HorizontalReference.RIGHT,
      minusInf  : HorizontalReference.MINUS_INF,
      plusInf   : HorizontalReference.PLUS_INF,
    },
    vertical : {
      top       : VerticalReference.TOP,
      middle    : VerticalReference.MIDDLE,
      center    : VerticalReference.MIDDLE,
      bottom    : VerticalReference.BOTTOM,
      minusInf  : VerticalReference.MINUS_INF,
      plusInf   : VerticalReference.PLUS_INF,
    }
  };
  
  // time units
  var TimeUnit = Packages.java.util.concurrent.TimeUnit;
  
  minium.timeUnits = {
      nanoseconds  : TimeUnit.NANOSECONDS,
      microseconds : TimeUnit.MICROSECONDS,
      milliseconds : TimeUnit.MILLISECONDS,
      seconds      : TimeUnit.SECONDS,
      minutes      : TimeUnit.MINUTES,
      hours        : TimeUnit.HOURS,
      days         : TimeUnit.DAYS
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