(function($, window) {
	
	$.fn.asyncHelloWorld = function(name, callback) {
		setTimeout(function() {
			callback("Hello, " + name + "!");
		}, 1000);
	};
	
})(jQuery, window);