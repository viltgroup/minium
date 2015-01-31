(function($, window, document) {
    
    $.fn.apply = $.fn.applyWebElements = function(fn, args) {
        return fn.apply(this, args);
    };
    
    $.fn.call = $.fn.callWebElements = function(fn, args) {
        return fn.apply(this, Array.prototype.slice.call(arguments, 1));
    };

})(jQuery, window, document);