(function($, window) {
	$.fn.visibleText = function() {
		return $.trim($(this).text());
	};
	
	$.fn.withText = function(text) {
		return $(this).filter(function() {
			return $(this).visibleText() === text;
		});
	};
	
	$.fn.matchingText = function(pattern) {
		return $(this).filter(function() {
			return new RegExp(pattern).test(visibleText(this));
		});
	};
	
	$.fn.containingText = function(text) {
		return $(this).filter(function() {
			return visibleText(this).indexOf(text) !== -1;
		});
	};
	
	$.fn.withLabel = function(text) {
		var id = $("label[for]:visible").withText(text).attr("for");
		
		if (!id) return $();
		
		return $(this).filter(function() {
			return $(this).attr("id") === id;
		});
	};
	
	$.fn.visible = function() {
		return $(this).filter(":visible");
	};
	
	$.fn.showTip = function(text, time) {
		// try to scroll to element
		try { this.scrollIntoView(); } catch(e) { /* I don't care */ }
		
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
			
		}).doTimeout(time, function() {
			$(this).trigger("timeout");
		});
	};
	
})(jQuery, window);