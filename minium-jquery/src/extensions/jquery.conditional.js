(function($, window, document) {
    
    $.fn.when = function(elems) {
        return $(elems).length == 0 ? $() : $(this);
    };

    $.fn.unless = function(elems) {
      return $(elems).length != 0 ? $() : $(this);
    };

    $.fn.then = function(elems) {
        return $(elems).when($(this));
    };
    
    $.fn.and = $.fn.filter;
    $.fn.or  = $.fn.add;

})(jQuery, window, document);