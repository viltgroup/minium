(function($) {
    var selectorGadget = null;
    
    $.fn.activateSelectorGadget = function() {
        if (!selectorGadget) {
            selectorGadget = new SelectorGadget();
            selectorGadget.makeInterface();
            selectorGadget.clearEverything();
            selectorGadget.setMode('interactive');
        } else if (selectorGadget.unbound) {
            selectorGadget.rebindAndMakeInterface();
        }
    };
    
    $.fn.deactivateSelectorGadget = function() {
        if (selectorGadget && !selectorGadget.unbound) {
            selectorGadget.unbindAndRemoveInterface();
        }
    };

    $.fn.getCssSelector = function() {
        var cssSelector = $("#selectorgadget_path_field").val();
        if (selectorGadget && !selectorGadget.unbound) {
            selectorGadget.unbindAndRemoveInterface();
        }
        return cssSelector;
    };
})(jQuery);