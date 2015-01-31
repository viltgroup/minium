(function () {
  var by = Packages.minium.script.rhinojs.CoreRhinoJsWebElements.DefaultRhinoJsWebElements.by;
  
  var minium = function (selector) {
    return by.cssSelector(selector);
  };
  minium.by = by;
  minium.browser = wd;
  
  // export minium
  module.exports = minium;
})();