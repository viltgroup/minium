(function($, window, document) {
    
    $.fn.ifNotEmpty = function(elems) {
        return $(elems).length == 0 ? $() : $(this);
    };

    $.fn.ifEmpty = function(elems) {
        return $(elems).length != 0 ? $() : $(this);
    };

    $.fn.notEmptyThen = function(elems) {
        return $(elems).ifNotEmpty($(this));
    };
    
    $.fn.emptyThen = function(elems) {
        return $(elems).ifEmpty($(this));
    };
    
})(jQuery, window, document);