(function($, window, document) {
    
    $.fn.eval = $.fn.evalWebElements = $.fn.evalAsync = function(script) {
        return (new Function("$", script)).call(this, minium.$);
    };

})(jQuery, window, document);