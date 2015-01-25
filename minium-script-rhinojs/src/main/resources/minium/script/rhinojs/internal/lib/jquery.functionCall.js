(function($, window, document) {
    
    $.fn.call = $.fn.callWebElements = function(fn) {
        return fn.apply(this, Array.prototype.slice.call(arguments, 1));
    };

})(jQuery, window, document);