(function () {
  var DefaultWebElements = Packages.minium.web.DefaultWebElements;
  
  exports.$ = DefaultWebElements.by.cssSelector;
  exports.by = DefaultWebElements.by;
})();