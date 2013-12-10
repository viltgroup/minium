(function($, window) {

  $.fn.attrs = function(name) {
    var result = $(this).map(function() { return $(this).attr(name); });
    if ($.isArray(result)) return result;
    return $.isEmptyObject(result) ? [] : [ result ];
  };

  $.fn.vals = function() {
    var result = $(this).val();
    if ($.isArray(result)) return result;
    return $.isEmptyObject(result) ? [] : [ result ];
  };

})(jQuery, window);