(function($, window, document) {
    
    $.fn.call = $.fn.callWebElements = $.fn.callAsync = function(fn) {
        return fn.apply(this, Array.prototype.slice.call(arguments, 1));
    };

    $.fn.eval = $.fn.evalWebElements = $.fn.evalAsync = function(script) {
        var scriptFn = eval("(function() { " + script + " });");
        return scriptFn.apply(this, Array.prototype.slice.call(arguments, 1));
    };

})(jQuery, window, document);