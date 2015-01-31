(function () {
  var by = Packages.minium.script.dynjs.CoreDynJsWebElements.DefaultDynJsWebElements.by;
  
  var minium = function (selector) {
    return by.cssSelector(selector);
  };
  minium.by      = by;
  minium.browser = wd;
  
  // export minium
  module.exports = minium;
})();