module.exports = function ($) {
  return function(styles) {
    if (styles) {
      $("<style type='text/css'>\n" + styles + "</style>").appendTo("head");
    }
  };
};