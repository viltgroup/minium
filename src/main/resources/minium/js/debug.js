(function($, window, document) {
	
	var unitMillis = {
			"NANOSECONDS"  : 0,
			"MICROSECONDS" : 0,
			"MILLISECONDS" : 1,
			"SECONDS"      : 1000,
			"MINUTES"      : 60 * 1000, 
			"HOURS"        : 60 * 60 * 1000,
			"DAYS"	       : 24 * 60 * 60 * 1000,
	};
	
	$.fn.highlight = function() {
		if (this.length == 1 && (this.get(0) instanceof HTMLDocument || this.get(0) instanceof Window)) return;
		
		var color = "red";
		var time = 5;
		var timeUnit = "SECONDS";
		
		var timeMillis = time * unitMillis[timeUnit];
			
		this.effect("highlight", { color : color }, timeMillis);
	};
	
	$.fn.highlightAndCount = function() {
		this.highlight();
		return this.length;
	};
	
})(jQuery, window, document);