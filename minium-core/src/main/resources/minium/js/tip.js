(function($, window) {

	var unitConverter = {
		MILLISECONDS : 1,
		SECONDS      : 1000,
		MINUTES      : 60 * 1000,
		HOURS        : 60 * 60 * 1000,
		DAYS         : 24 * 60 * 60 * 1000
	}
	
	$.fn.showTip = function(text, time, timeunit) {
		
		// try to scroll to element
		try { this.scrollIntoView(); } catch(e) { /* I don't care */ }
		
		var multiplier = unitConverter[timeunit];
		
		if (multiplier === undefined) throw "Invalid time unit: " + timeunit + ". Please use only MILLISECONDS or higher.";
		
		$(this).qtip({
			content : { text : text },
			suppress : false,
			show : {
				event : 'ready',
				solo : true,
				ready : true
			},
			hide : {
				event : 'timeout',
				effect: function(offset) {
					$(this).fadeOut(500);
				}
			},
			position: {
//				target : $(window.top.body),
				viewport : $(window)
			},
			style : {
				tip : { corner : true }
			}
			
		}).doTimeout(time * multiplier, function() {
			$(this).trigger("timeout");
		});
	};

})(jQuery, window);