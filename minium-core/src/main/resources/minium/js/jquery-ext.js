(function($, window) {
	
	$.fn.attrs = function(name) {
		var result = $(this).map(function() { return $(this).attr(name); });
		if ($.isArray(result)) return result;
		return $.isEmptyObject(result) ? [] : [ result ]; 
	};
	
	$.fn.vals = function() {
		var result = $(this).val();
		if ($.isArray(result)) return result;
		return $.isEmptyObject(result) ? [] : [ result ]; 
	};

	$.fn.call = function(fnName) {
		var varargs = Array.prototype.slice.call(arguments, 1);
		var thisElems = $(this);
		return thisElems[fnName].apply(thisElems, varargs);
	};
	
})(jQuery, window);