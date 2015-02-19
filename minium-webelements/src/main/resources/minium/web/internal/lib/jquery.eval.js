(function($, window, document) {
    
    $.fn.eval = $.fn.evalWebElements = $.fn.evalAsync = function(script) {
        return eval("var $ = minium.jQuery; " + script);
    };

})(jQuery, window, document);