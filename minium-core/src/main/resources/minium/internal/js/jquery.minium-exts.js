(function($, window) {

  // based on http://kjvarga.blogspot.pt/2009/06/jquery-plugin-to-escape-css-selector.html
  var escapeSelector = (function() {
    var specials = [ "'", ':', '"', '!', ';', ',' ];
    var regexSpecials = [ '.', '*', '+', '|', '[', ']', '(', ')', '/', '^', '$' ];
    var regex = new RegExp('(' + specials.join('|') + '|\\' + regexSpecials.join('|\\') + ')', 'g');

    return function(selector) {
      return selector.replace(regex, '\\$1');
    };
  })();

  var clearExtraWhitespaces = function(str) {
    return $.trim(str.replace(/(\s|\u00A0)+/g, " "));
  }

  $.fn.withText = function(text) {
    return $(this).filter(function() {
      return clearExtraWhitespaces($(this).text()) === clearExtraWhitespaces(text);
    });
  };

  $.fn.withVisibleText = function(text) {
    return $(this).filter(function() {
      return clearExtraWhitespaces($(this).visibleText()) === clearExtraWhitespaces(text);
    });
  };

  $.fn.matchingText = function(pattern) {
    return $(this).filter(function() {
      return new RegExp(pattern).test($(this).text());
    });
  };

  $.fn.matchingVisibleText = function(pattern) {
    return $(this).filter(function() {
      return new RegExp(pattern).test($(this).visibleText());
    });
  };

  $.fn.containingText = function(text) {
    return $(this).filter(function() {
      return clearExtraWhitespaces($(this).text()).indexOf(clearExtraWhitespaces(text)) !== -1;
    });
  };

  $.fn.containingVisibleText = function(text) {
    return $(this).filter(function() {
      return clearExtraWhitespaces($(this).visibleText()).indexOf(clearExtraWhitespaces(text)) !== -1;
    });
  };

  $.fn.withLabel = function(text) {
    var id = $("label[for]").withText(text).attr("for");

    if (!id) return $();

    return $(this).filter("#" + escapeSelector(id));
  };

  $.fn.withValue = function(value) {
    return $(this).filter(function() {
      return $.trim($(this).val()) === value;
    });
  };

  $.fn.withAttr = function(name, value) {
    return $(this).filter(function() { return value ? $(this).attr(name) === value : $(this).attr(name); });
  };
  
  $.fn.withCss = function(name, value) {
    return $(this).filter(function() { return value ? $(this).css(name) === value : $(this).css(name); });
  };
  
  $.fn.withName = function(value) {
    return $(this).withAttr("name", value);
  };

  $.fn.visible = function() {
    return $(this).filter(":visible");
  };

  $.fn.displayed = function() {
    return $(this).not(":hidden");
  };

  $.fn.selected = function() {
    return $(this).filter(":selected");
  };

  $.fn.attrs = function(name) {
    var result = $(this).map(function() { return $(this).attr(name); });
    if ($.isArray(result)) return result;
    return $.isEmptyObject(result) ? [] : [ result ];
  };

  $.fn.vals = function() {
    var result = $(this).map(function() { return $(this).val(); });
    if ($.isArray(result)) return result;
    return $.isEmptyObject(result) ? [] : [ result ];
  };
  
})(jQuery, window);