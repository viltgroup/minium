(function($, window) {

	$.fn.scrollIntoView = function(alignWithTop) {
		if ($(this).length === 0) return;
		var elem = $(this)[0];
		elem.scrollIntoView(alignWithTop);
	};
	
})(jQuery, window);