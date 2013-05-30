(function($, window) {
	
	var intersects = function(box, other) {
		var xmin1 = box  .left;
		var ymin1 = box  .top;
		var xmax1 = box  .right;
		var ymax1 = box  .bottom; 
		var xmin2 = other.left;
		var ymin2 = other.top;
		var xmax2 = other.right;
		var ymax2 = other.bottom;        
        
        return (xmin1 < xmax2 && xmax1 > xmin2 && ymin1 < ymax2 && ymax1 > ymin2);
	};
	
	var relativePosition = function(elems, fromElems, boxExtensionFn, distanceFn) {
		
		var elemsInfo = $.map(elems, function(elem) {
			return {
				element : elem,
				boundingBox : elem.getBoundingClientRect(),
				distance : Number.MAX_VALUE
			};
		});
		
		var extensionBoxes = $.map($(fromElems), boxExtensionFn);
		
		var filteredElemsInfo = $.grep(elemsInfo, function(elemInfo) {
			
			$.each(extensionBoxes, function(i, box) {
				if (intersects(elemInfo.boundingBox, box)) {
					elemInfo.distance = Math.min(elemInfo.distance, distanceFn(elemInfo.boundingBox, box));
				}
			});
			
			return elemInfo.distance !== Number.MAX_VALUE;
		});
		
		var sorted = filteredElemsInfo.sort(function(b1, b2) {
			return b1.distance - b2.distance;
		});
		
		return $($.map(sorted, function(elemInfo) { return elemInfo.element; }));
	};
	
	$.fn.leftOf = function(fromElems) {
		var boxExtensionFn = function(from) {
			var box = from.getBoundingClientRect();
			return {
				left   : Number.MIN_VALUE, 
				top    : box.top, 
				right  : box.left,
				bottom : box.bottom
			}
		};
		var distanceFn = function(b1, b2) {
			return Math.max(0, b2.right - b1.right);
		};
		return relativePosition(this, fromElems, boxExtensionFn, distanceFn);
	};

	$.fn.above = function(fromElems) {
		var boxExtensionFn = function(from) {
			var box = from.getBoundingClientRect();
			return {
				left   : box.left, 
				top    : Number.MIN_VALUE,
				right  : box.right,
				bottom : box.top
			}
		};
		var distanceFn = function(b1, b2) {
			return Math.max(0, b2.bottom - b1.bottom);
		};
		return relativePosition(this, fromElems, boxExtensionFn, distanceFn);
	};
	
	$.fn.rightOf = function(fromElems) {
		var boxExtensionFn = function(from) {
			var box = from.getBoundingClientRect();
			return {
				left   : box.right, 
				top    : box.top, 
				right  : Number.MAX_VALUE,
				bottom : box.bottom
			}
		};
		var distanceFn = function(b1, b2) {
			return Math.max(0, b1.left - b2.left);
		};
		return relativePosition(this, fromElems, boxExtensionFn, distanceFn);
	};
	
	$.fn.below = function(fromElems) {
		var boxExtensionFn = function(from) {
			var box = from.getBoundingClientRect();
			return {
				left   : box.left, 
				top    : box.bottom, 
				right  : box.right,
				bottom : Number.MAX_VALUE
			}
		};
		var distanceFn = function(b1, b2) {
			return Math.max(0, b1.top - b2.top);
		};
		return relativePosition(this, fromElems, boxExtensionFn, distanceFn);
	};
	
	$.fn.overlaps = function(fromElems) {
		var boxExtensionFn = function(from) {
			return from.getBoundingClientRect();
		};
		var distanceFn = function(b1, b2) {
			// we don't care for now about their distance
			return 0;
		};
		return relativePosition(this, fromElems, boxExtensionFn, distanceFn);
	};
}
)(jQuery, window);