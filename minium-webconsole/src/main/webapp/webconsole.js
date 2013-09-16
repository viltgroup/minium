(function($) {
    // https://gist.github.com/BMintern/1795519
    var escapeHtml = function(str) {
        var div = document.createElement('div');
        div.appendChild(document.createTextNode(str));
        return div.innerHTML;
    };
    
    var error = function(options) {
        $.growl.error($.extend({ title : "", duration : 6400}, options));
    };

    var notice = function(options) {
        $.growl.notice($.extend({ title : "", duration : 6400}, options));
    };
    
    var Overlay = function() {
        this.elem = $("#overlay");
        var opts = {
                lines: 13, // The number of lines to draw
                length: 20, // The length of each line
                width: 10, // The line thickness
                radius: 30, // The radius of the inner circle
                corners: 1, // Corner roundness (0..1)
                rotate: 0, // The rotation offset
                direction: 1, // 1: clockwise, -1: counterclockwise
                color: '#ffffff', // #rgb or #rrggbb or array of colors
                speed: 0.9, // Rounds per second
                trail: 60, // Afterglow percentage
                shadow: false, // Whether to render a shadow
                hwaccel: false, // Whether to use hardware acceleration
                className: 'spinner', // The CSS class to assign to the spinner
                zIndex: 2e9, // The z-index (defaults to 2000000000)
                top: 'auto', // Top position relative to parent in px
                left: 'auto' // Left position relative to parent in px
        };
        this.spinner = new Spinner(opts);
    };
    Overlay.prototype.on = function() {
        this.elem.on.apply(this.elem, arguments);
    };
    Overlay.prototype.off = function() {
        this.elem.off.apply(this.elem, arguments);
    };
    Overlay.prototype.show = function() {
        this.elem.show();
        this.spinner.spin(this.elem);
    };
    Overlay.prototype.hide = function() {
        this.spinner.stop();
        this.elem.hide();
    };
    
    var overlay = new Overlay();
	
	var evaluate = function (cm) {
        var cursor = cm.getCursor("start");
        var selection = cm.getSelection();
        if (!selection.trim()) {
            selection = cm.getLine(cursor.line).trim();
        }
        console.debug(selection);
        overlay.show();
        $.ajax({
            dataType : "json",
            type     : "post",
            url      : "minium/console/eval",
            data     : { expr : selection, lineno : cursor.line },
            complete : function() {
                overlay.hide();
            },
            success  : function(data) {
                if (data.exceptionInfo) {
                    var title = "Line " + (data.lineNumber + 1);
                    error({ title : title, message: data.exceptionInfo.message });
                }
                else if (data.size >= 0) {
                    notice({ message: "Matched " + data.size + " web elements" });
                }
                else {
                    notice({ message: data.value ? escapeHtml(data.value) : "No value" });
                }
            }
        });
    };
    
    var activateSelectorGadget = function (cm) {
        $.ajax({
            dataType : "json",
            type     : "post",
            url      : "minium/console/activateSelectorGadget",
            success  : function(data) {
                overlay.on("click", function() {
                    getCssSelector(cm);
                });
                overlay.show();
            },
            error    : function() { 
                overlay.hide(); 
            }
        });
    };

    var getCssSelector = function (cm) {
        $.ajax({
            dataType : "json",
            type     : "post",
            url      : "minium/console/getCssSelector",
            success  : function(data) {
                cm.replaceSelection("\"" + data + "\"");
            },
            complete : function() {
                overlay.off("click");
                overlay.hide(); 
            }
        });
    };
	
	var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
		lineNumbers: true,
		theme: "lesser-dark",
		tabSize: 2,
		extraKeys: {
			"Ctrl-Enter" : evaluate,
	        "Ctrl-Space" : activateSelectorGadget
		}
	});
	
	// let's add a container for tips
	$("<div />", { id : "tipcontainer" }).hide().appendTo("body");

	$(window).unload(function() {
		$.ajax({
			type     : "post",
			url      : "minium/console/close"
		});
	});
	
})(jQuery);