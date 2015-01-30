(function () {
  var DefaultWebElements = Packages.minium.web.CoreWebElements.DefaultWebElements;
  
  var minium = function () {
    return DefaultWebElements.by.cssSelector.apply(DefaultWebElements.by, arguments);
  };
  minium.by = DefaultWebElements.by;
  
  // export minium
  module.exports = minium;
  
})();