(function($, window, document) {
    
    $.fn.eval = $.fn.evalWebElements = $.fn.evalAsync = function(fn) {
        return fn.apply(this, Array.prototype.slice.call(arguments, 1));
    };

})(jQuery, window, document);