(function ($) {
  
  "use strict";
  
  var offsetRegex = /(?:(left|right|center)(?:([+-]\d+(?:\.\d+)?)(%|px)?)?|(?:([+-]inf)))\s+(?:(top|bottom|center|middle)(?:([+-]?\d+(?:\.\d+)?)(%|px)?)?|(?:([+-]inf)))/;
  
  var horizontalReferences = {
    "left"   : function (width) { return 0 },
    "center" : function (width) { return width / 2 },
    "right"  : function (width) { return width },
    "-inf"   : function (width) { return Number.NEGATIVE_INFINITY },
    "+inf"   : function (width) { return Number.POSITIVE_INFINITY }
  };

  var verticalReferences = {
    "top"    : function (height) { return 0 },
    "center" : function (height) { return height / 2 },
    "bottom" : function (height) { return height },
    "-inf"   : function (height) { return Number.NEGATIVE_INFINITY },
    "+inf"   : function (height) { return Number.POSITIVE_INFINITY }
  };
  
  var Boxes, boxes;
  
  Boxes = boxes = function (arg) {
    if (this instanceof Boxes) return this.init(arg);
    if (arg instanceof Boxes) return arg;
    return new Boxes(arg);
  };
  
  Boxes.parseOffset = function (offsetStr) {
    if (typeof offsetStr === 'function') return offsetStr;
    
    var matches = offsetRegex.exec(offsetStr);
    if (matches == null) throw "Could not parse " + offsetStr;
    
    var horizontalOffset = function (width) {
      if (matches[4]) {
        return horizontalReferences[matches[4]](width);
      } else {
        var reference = horizontalReferences[matches[1]];
        var value     = matches[2] ? parseInt(matches[2]) : 0;
        var unit      = matches[3];
        return reference(width) + (unit === '%' ? value * width / 100 : value); 
      }
    };
    var verticalOffset = function (height) {
      if (matches[8]) {
        return verticalReferences[matches[8]](height);
      } else {
        var reference = verticalReferences[matches[5]];
        var value     = matches[6] ? parseInt(matches[6]) : 0;
        var unit      = matches[7];
        return reference(height) + (unit === '%' ? value * height / 100 : value); 
      }
    };
    
    return function (rect) {
      return { 
        x : (rect.left || 0) + horizontalOffset(rect.width),
        y : (rect.top  || 0) + verticalOffset(rect.height)
      };
    };
  };
  
  var createBox = function (tl, br) {
    return {
      left   : tl.x,
      top    : tl.y,
      right  : br.x,
      bottom : br.y,
      width  : br.x - tl.x,
      height : br.y - tl.y
    };
  };
  
  var intersect = function (b1, b2) {
    if (b2.left   > b1.right  || 
        b2.right  < b1.left   || 
        b2.top    > b1.bottom ||
        b2.bottom < b1.top) {
      return null;
    }
    
    var tx1 = b1.left;
    var ty1 = b1.top;
    var rx1 = b2.left;
    var ry1 = b2.top;
    
    var tx2 = b1.right;
    var ty2 = b1.bottom;
    var rx2 = b2.right;
    var ry2 = b2.bottom;
    
    if (tx1 < rx1) tx1 = rx1;
    if (ty1 < ry1) ty1 = ry1;
    if (tx2 > rx2) tx2 = rx2;
    if (ty2 > ry2) ty2 = ry2;
    
    var width  = tx2 - tx1;
    var height = ty2 - ty1;
    
    return {
      left   : tx1,
      top    : ty1,
      right  : tx1 + width,
      bottom : ty1 + height,
      width  : width,
      height : height
    };
  };
  
  var contains = function (b1, b2) {
    return (b2.left >= b1.left && b2.top >= b1.top && b2.right <= b1.right && b2.bottom <= b1.bottom);
  };
  
  var isEmpty = function (b) {
    return (b.width <= 0) || (b.height <= 0);
  }
  
  Boxes.fn = Boxes.prototype = {
    constructor: Boxes,
    
    items : [],
    
    length : 0,
    
    init : function (arg) {
      
      // Handle "", null, or undefined
      if ( !arg ) {
        return this;
      }
      
      // Handle DOMElement
      if ( arg.nodeType ) {
        return this.init([ arg ]);
      }
      
      // Handle jQuery
      if ( arg instanceof $) {
        // call it with DOMElements
        return this.init(arg.get());
      }
      
      // Handle Rectangles
      if ( $.isArray(arg) ) {
        // we assume all arguments are Rectangles or DOMElements
        this.pushAll($.map(arg, function (e) { return e.nodeType ? e.getBoundingClientRect() : e }));
        return this;
      }
    },
  
    pushAll : function (rects) {
      this.items = this.items.concat(rects.filter(function (r) { return !isEmpty(r) }));
      this.length = this.items.length;
      return this;
    },

    get : function (i) {
      return typeof i === 'number' ? this.items[i] : this.items.slice(0);
    },
    
    relative : function (topLeftOffset, bottomRightOffset) {
      topLeftOffset = Boxes.parseOffset(topLeftOffset);
      bottomRightOffset = Boxes.parseOffset(bottomRightOffset);
      return boxes($.map(this.items, function (item) {
        var tl = topLeftOffset(item);
        var bt = bottomRightOffset(item);
        return createBox(tl, bt);
      }));
    },
    
    left   : function () { return this.relative("-inf top"   , "left bottom") },
    right  : function () { return this.relative("right top"  , "+inf bottom") },
    top    : function () { return this.relative("left -inf"  , "right top"  ) },
    bottom : function () { return this.relative("left bottom", "right +inf" ) },

    intersect : function (others) {
      others = boxes(others);
      var res = [];
      for (var i = 0; i < this.items.length; i++) {
        var i1 = this.items[i];
        for (var j = 0; j < others.items.length; j++) {
          var i2 = others.items[j];
          var intersection = intersect(i1, i2);
          if (intersection) res.push(intersection);
        }
      }
      return boxes(res);
    },
    
    containedBy : function (containers) {
      containers = boxes(containers);
      var res = $.grep(this.items, function (item) {
        return containers.items.some(function (container) { return contains(container, item) });
      });
      return boxes(res);
    }
  };
  
  $.boxes = boxes;
  $.fn.boxes = function () {
    return $.boxes(this);
  };
  
  // extensions to jQuery
  $.fn.containedBy = function (containers) {
    containers = boxes(containers);
    return $(this).filter(function () {
      return $(this).boxes().containedBy(containers).length > 0;
    });
  };
  
})(jQuery);
