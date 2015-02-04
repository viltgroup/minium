
(function () {
  var minium;
  
  if (typeof wd !== 'undefined') {
    var by = Packages.minium.script.rhinojs.CoreRhinoJsWebElements.DefaultRhinoJsWebElements.by;
    
    var minium = function (selector) {
      return by.cssSelector(selector);
    };
    minium.by = by;
    minium.browser = wd;
  }

  if (typeof screen !== 'undefined') {
    var by = Packages.minium.visual.CoreVisualElements.DefaultVisualElements.by;
    
    var minium = function (selector) {
      return by.image(selector);
    };
    minium.by = by;
    minium.screen = screen;
  }
  
  // common stuff
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
  
  // export minium
  module.exports = minium;
})();