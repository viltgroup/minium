(function($) {
	
	var tipDiv = $("<div></div>").hide().appendTo("body");
	
	var showTip = function(coords, text, error) {
		
		var title = error ? "Error" : "Value";
		var style = error ? "ui-tooltip-red" : "ui-tooltip-green";
		
		tipDiv.qtip({
			content : {
				text : text, 
				title : {
					text: title
				}
			},
			suppress : false,
			position : {
				target : [ coords.x, coords.yBot ],
				viewport : $(".CodeMirror-scroll")
			},
			show : {
				event: false,
				solo : true,
				ready: true
			},
			hide : {
				event: "unfocus"
			},
			style : {
				tip : { corner : true },
				classes : style
			}
		});
	};
	
	var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
		lineNumbers: true,
		theme: "lesser-dark",
		tabSize: 2,
		extraKeys: {
			"Ctrl-Enter" : function (cm) {
  				var selection = cm.getSelection();
				if (!selection.trim()) {
					var pos = cm.getCursor(false);
					selection = cm.getLine(pos.line).trim();
				}
				var coords = cm.cursorCoords(false, "page");
				console.debug(selection);
				$.ajax({
					dataType : "json",
					type     : "post",
					url      : "minium/console/eval",
					data     : { expr : selection },
					success  : function(data) {
						if (data.exceptionInfo) {
							showTip(coords, data.exceptionInfo.message, true);							
						}
						else if (data.size >= 0) {
							showTip(coords, "Matched " + data.size + " web elements", false);
						}
						else {
							showTip(coords, data.value ? data.value : "No value...", false);
						}
					}
				});
 			}
		}
	});
	
	var editbox = $(".editbox");
		
	editbox.bind("dragover", function() {
		$(this).addClass("hover");
		return false;
	});
	editbox.bind("dragend", function() {
		$(this).removeClass("hover");
		return false;
	});
	
	editbox.bind("drop", function(e) {
		$(this).removeClass("hover");
		e.preventDefault();

		var file = e.originalEvent.dataTransfer.files[0];
		var reader = new FileReader();
		
		reader.onload = function(event) {
			var code = event.target.result;
			editor.setValue(code);
		};
		reader.readAsText(file, "ISO-8859-1");

		return false;
	});

	$(window).unload(function() {
		$.ajax({
			type     : "post",
			url      : "minium/console/close"
		});
	});
	
})(jQuery);