(function($, window, document) {
	
	$.fn.when = function(elems) {
		return $(elems).length == 0 ? $() : $(this);
	};

	$.fn.unless = function(elems) {
		return $(elems).length != 0 ? $() : $(this);
	};
	
})(jQuery, window, document);