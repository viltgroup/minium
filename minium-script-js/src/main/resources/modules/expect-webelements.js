(function (global, module) {

  var expect = require('expect');
  
  var exports = module.exports;

  /**
   * Exports.
   */

  module.exports = expect;
  
  // let's extend expect.js
  
  expect.Assertion.prototype._assertElements = function (obj, msg, err, expected) {
    obj = obj || this.obj;
    var truth = this.flags.not ? !obj.checkForUnexistence() : obj.checkForExistence();
    this.assert(
      truth, 
      msg || function(){ return 'expected ' + obj + ' to have elements' },
      err || function(){ return 'expected ' + obj + ' to not have elements' },
      expected);
    return this;
  };
  
  expect.Assertion.prototype.elements = function () {
    return this._assertElements(this.obj);
  };
  
  var _expect = function (that, obj) {
    return that.flags.not ? expect(obj).not : expect(obj);
  };
  
  expect.Assertion.prototype.size = function (n) {
    return this._assertElements(this.obj.applyWebElements(function (n) { return $(this).length == n ? $(':root') : $() }, [ n ]),
      function(){ return 'expected ' + this.obj + ' to have size ' + n + ' but got ' + this.obj.size() },
      function(){ return 'expected ' + this.obj + ' to not have size ' + n }
    );
  };
  
  expect.Assertion.prototype.val = function (expected) {
    return this._assertElements(this.obj.withValue(expected),
      function(){ return 'expected ' + this.obj + ' to have val ' + expected + '" but got ' + this.obj.val() + '"' },
      function(){ return 'expected ' + this.obj + ' to not have val' + expected + '"' }
    );
  };

  expect.Assertion.prototype.attr = function (name, expected) {
    return this._assertElements(this.obj.withAttr(name, expected),
        function(){ return 'expected ' + this.obj + ' to have attr ' + name + '="' + expected + '" but got ' + name + '="' + this.obj.attr(name) + '"' },
        function(){ return 'expected ' + this.obj + ' to not have attr ' + name + '="' + expected + '"' }
    );
  };

  expect.Assertion.prototype.css = function (name, expected) {
    return this._assertElements(this.obj.withCss(name, expected),
      function(){ return 'expected ' + this.obj + ' to have css ' + name + '="' + expected + '" but got ' + name + '="' + this.obj.css(name) + '"' },
      function(){ return 'expected ' + this.obj + ' to not have css ' + name + '="' + expected + '"' }
    );
  };
  
  expect.Assertion.prototype.text = function (expected) {
    return this._assertElements(this.obj.withText(expected),
      function(){ return 'expected ' + this.obj + ' to have text "' + expected + '" but got "' + this.obj.text() + '"' },
      function(){ return 'expected ' + this.obj + ' to not have text "' + expected + '"' }
    );
  };
  
  var _empty = expect.Assertion.prototype.empty;
  expect.Assertion.prototype.empty = function () {
    if (this.obj instanceof Packages.minium.Elements) {
      var truth = this.flags.not ? !this.obj.checkForExistence() : this.obj.checkForUnexistence();
      this.assert(
          truth
        , function(){ return 'expected ' + this.obj + ' to be empty' }
        , function(){ return 'expected ' + this.obj + ' not to be empty' });
      return this;
    } else {
      return _empty.apply(this, arguments);
    }
  };

})(this, 'undefined' != typeof module ? module : {exports: {}});