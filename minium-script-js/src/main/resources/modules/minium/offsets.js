(function () {
  // offsets
  var HorizontalReference = Packages.minium.Offsets.HorizontalReference;
  var VerticalReference   = Packages.minium.Offsets.VerticalReference;
  
  var offsets = {
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

  module.exports = offsets;
})();