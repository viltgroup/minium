(function($) {
    var selectorGadget = null;
    
    $.fn.activateSelectorGadget = function() {
        if (!selectorGadget || selectorGadget.unbound) {
            selectorGadget = new SelectorGadget();
            selectorGadget.makeInterface();
            selectorGadget.clearEverything();
            selectorGadget.setMode('interactive');
        }
    };
    
    $.fn.deactivateSelectorGadget = function() {
        if (selectorGadget && !selectorGadget.unbound) {
            selectorGadget.unbindAndRemoveInterface();
            selectorGadget = null;
        }
    };

    $.fn.getCssSelector = function() {
        var cssSelector = $("#selectorgadget_path_field").val();
        if (selectorGadget && !selectorGadget.unbound) {
            selectorGadget.unbindAndRemoveInterface();
            selectorGadget = null;
        }
        return cssSelector;
    };
})(jQuery);