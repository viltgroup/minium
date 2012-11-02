(function($, window) {
	
	var windowLink = null;
	
	$.fn.createHiddenAnchor = function (url, target, settings) {
		target = target || "_blank";
		windowLink = $("<a>", { href : url, target : target })
			.text("New window")
			.css({
				"position" : "absolute",
				"top"      : "10px",
				"left"     : "10px",
				"z-index"  : "2147483647"
			});
		
		if (settings) {
			windowLink.click(function() { window.open(url, target, settings); return false; });			
		}
		
		windowLink.appendTo("body");
		
		return windowLink;
	};
	
	$.fn.removeHiddenAnchor = function() {
		if (windowLink != null) windowLink.remove();
	};
	
})(jQuery, window);