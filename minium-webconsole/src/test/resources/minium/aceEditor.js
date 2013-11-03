(function($) {
  
  $.fn.writeCode = function(code, selected) {
    var editor = ace.edit("editor");
    var session = editor.getSession();
    var position = editor.getCursorPosition();
    var selection = editor.getSelection(); 
    selection.clearSelection();
    session.insert(position, code);
    if (selected) {
      var Range = ace.require('ace/range').Range;
      var endPosition = editor.getCursorPosition();
      selection.addRange(new Range(
          position.row, 
          position.column,
          endPosition.row,
          endPosition.column
      ));
    } else {
      position = editor.getCursorPosition();
      selection.clearSelection();
      editor.moveCursorToPosition(position);
    }
  };
  
  $.fn.focus = function() {
    var editor = ace.edit("editor");
    editor.focus();
  };
  
})(jQuery);  