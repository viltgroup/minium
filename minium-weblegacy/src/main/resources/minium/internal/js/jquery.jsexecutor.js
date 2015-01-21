(function($, window, document) {
    
    $.fn.call = $.fn.callWebElements = $.fn.callAsync = $.fn.callWebElementsAsync = function(fn) {
        return fn.apply(this, Array.prototype.slice.call(arguments, 1));
    };

    $.fn.eval = $.fn.evalWebElements = $.fn.evalAsync = function(script) {
        return eval("var $ = minium.jQuery; " + script);
    };

})(jQuery, window, document);