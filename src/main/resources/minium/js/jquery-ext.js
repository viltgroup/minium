(function($, window) {
	
	$.fn.vals = function() {
		var result = $(this).val();
		if ($.isArray(result)) return result;
		return $.isEmptyObject(result) ? [] : [ result ]; 
	};
	
})(jQuery, window);