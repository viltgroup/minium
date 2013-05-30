(function($, window, document) {
	
	var unitMillis = {
			"NANOSECONDS"  : 0,
			"MICROSECONDS" : 0,
			"MILLISECONDS" : 1,
			"SECONDS"      : 1000,
			"MINUTES"      : 60 * 1000, 
			"HOURS"        : 60 * 60 * 1000,
			"DAYS"	       : 24 * 60 * 60 * 1000
	};
	
	$.fn.highlight = function(color, time, timeUnit) {		
		color = color || "red"; 
		time = time || 5;
		timeUnit = timeUnit || "SECONDS";
		
		var timeMillis = time * unitMillis[timeUnit];
			
		this.effect("highlight", { color : color }, timeMillis);
	};
	
	$.fn.highlightAndCount = function() {
		this.highlight();
		return this.length;
	};
	
})(jQuery, window, document);