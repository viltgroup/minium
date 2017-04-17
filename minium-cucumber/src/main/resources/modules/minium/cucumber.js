var cucumber = {
    Given: Given,
    When: When,
    Then: Then,
    Before: Before,
    After: After,
    World: World,
    loadDefinitions: function () {
      for (var i = 0; i < arguments.length; i++) {
        var mod = arguments[i];
        if (typeof mod === 'string') {
          mod = require(mod);
        }
        mod.apply(cucumber);
      }
    }
}

module.exports = cucumber;
