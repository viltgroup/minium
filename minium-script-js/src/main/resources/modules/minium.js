(function () {
  var minium;
  
  if (typeof __by !== 'undefined') {
    var by = __by;
    
    var minium = function (selector) {
      return by.selector(selector);
    };
    
    var HasBrowser = Packages.minium.web.actions.HasBrowser; 
    
    minium.by = by;

    if (by.root().is(HasBrowser)) {
      minium.browser = by.root().browser();
    }
  }
  
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
  
  // export minium
  module.exports = minium;
})();