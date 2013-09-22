function WebConsoleCtrl($scope, $http, $timeout, promiseTracker) {
  
  var baseServiceUrl = "/minium-webconsole/minium";
  
  // utilities functions
  var escapeHtml = function(str) {
      var div = document.createElement('div');
      div.appendChild(document.createTextNode(str));
      return div.innerHTML;
  };

  var service = {
    get : function(url, params) {
      var fn = function() {
        return $http.get(baseServiceUrl + url, { params : params });
      };
      return $scope.$$phase ? fn() : $scope.$apply(fn);
    }
  };

  
  // Modal Dialog stuff
  var ModalDialog = function(elem, options) {
    var dialog = this;
    dialog.shown = false;
    dialog.elem = $(elem)
      .on("hidden", function() { $scope.$apply(dialog.reset); }) 
      .on("hide", function() { dialog.shown = false; })
      .on("show", function() { dialog.shown = true; });

    dialog.options = options || {};
    if (options.trackerId) {
      dialog.tracker = promiseTracker(options.trackerId);
      dialog.tracker.on("start", function() { 
        if (dialog.shown) dialog.elem.modal("loading", function() { console.debug("Start", this.isLoading); }); 
      });
      dialog.tracker.on("done", function() { 
        if (dialog.shown) dialog.elem.modal("loading", function() { console.debug("Done", this.isLoading); }); 
      });
    }
    dialog.model = dialog.options.getInitialValue ? dialog.options.getInitialValue() : {}; 
    dialog.reset = function () {
      if (dialog.options.formName) {
        $scope[dialog.options.formName].$setPristine(); 
      }

      if (dialog.options.getInitialValue) {
        dialog.model = angular.copy(dialog.options.getInitialValue());
      }
      
      // cancel promise tracker
      if (dialog.tracker) dialog.tracker.cancel();
    };
    dialog.hide = function () {
      dialog.elem.modal("hide");
    };
    dialog.show = function () {
      dialog.elem.modal("show");
    };
  };

  // Editor stuff
  var loadEditorPreferences = function (defaults) {
    var editorPreferences = $.cookie("editorPreferences");
    editorPreferences = editorPreferences ? JSON.parse(editorPreferences) : {};
    return $.extend({}, defaults, editorPreferences);
  };

  var configureEditor = function (editor, editorPreferences) {
    editor.setTheme(editorPreferences.theme);
    editor.setFontSize(editorPreferences.fontSize + "px");
    editor.getSession().setTabSize(editorPreferences.tabSize);
    editor.getSession().setUseSoftTabs(editorPreferences.softTabs);
  };

  var initEditor = function (preferences) {
    var editor = ace.edit("editor");
    editor.setShowPrintMargin(false);
    editor.getSession().setMode("ace/mode/javascript");
    editor.getSession().getScreenLength();

    configureEditor(editor, preferences);
    
    // commands
    var evalutate = function(editor) {
      var range = editor.getSelectionRange();
      var session = editor.getSession();

      var line = range.start.row;
      var code = range.isEmpty() ? session.getLine(line) : session.getTextRange(range);
      
      var tracker = promiseTracker("evaluate");
      tracker.on("start", function() { $('body').modalmanager('loading'); });
      tracker.on("done", function() { $('body').modalmanager('loading'); });

      var request = service.get("/console/eval", { expr : code, lineno  : line + 1 }).success(function(data) {
        if (data.exceptionInfo) {
          console.error(data.exceptionInfo);
          $.bootstrapGrowl(data.exceptionInfo.message, { type: "danger" });
          var errors = [ { 
            row : data.lineNumber,
            column : 0,
            text: data.exceptionInfo.message,
            type: "error"
          } ];
          editor.getSession().setAnnotations(errors);
        }
        else if (data.size >= 0) {
          $.bootstrapGrowl(data.size + " matching web elements", { type: "success" });
        }
        else {
          $.bootstrapGrowl(data.value ? escapeHtml(data.value) : "No value", { type: "success" });
        }
      });

      tracker.addPromise(request);
    };

    var activateSelectorGadget = function(editor) {
      var range = editor.getSelectionRange();
      
      $scope.selectorGadgetDialog.range = range;
      $scope.selectorGadgetDialog.show();
    };

    editor.commands.addCommand({
      name: "evaluate",
      bindKey: { win: "Ctrl-Enter", mac: "Command-Enter" },
      exec: evalutate,
      readOnly: false // should not apply in readOnly mode
    });
    editor.commands.addCommand({
      name: '',
      bindKey: { win: "Ctrl-Space", mac: "Command-Space" },
      exec: activateSelectorGadget,
      readOnly: false // should not apply in readOnly mode
    });
    
    editor.focus();

    return editor;
  };

  $scope.editorPreferences = loadEditorPreferences({
    theme    : "ace/theme/twilight",
    fontSize : 12,
    tabSize  : 2,
    softTabs : true
  });
  $scope.aceEditor = initEditor($scope.editorPreferences);
  

  // Web drivers stuff
  $scope.webDriverTypes = [
    { id : "Chrome"           , displayName : "Chrome"            , shortDisplayName : "Chrome"    , glyphicon : "fontello-chrome"  },
    { id : "Firefox"          , displayName : "Firefox"           , shortDisplayName : "Firefox"   , glyphicon : "fontello-firefox" },
    { id : "Safari"           , displayName : "Safari"            , shortDisplayName : "Safari"    , glyphicon : "fontello-safari"  },
    { id : "InternetExplorer" , displayName : "Internet Explorer" , shortDisplayName : "IE"        , glyphicon : "fontello-ie"      },
    { id : "Opera"            , displayName : "Opera"             , shortDisplayName : "Opera"     , glyphicon : "fontello-opera"   },
    { id : "PhantomJS"        , displayName : "PhantomJS"         , shortDisplayName : "PhantomJS" , glyphicon : "fontello-browser" }
  ];

  $scope.webDriverVariables = [];
  $http.get("data/webDrivers.json").success(function(data) {
    $scope.webDriverVariables = data;
  });

  $scope.countWebDriversByType = function(typeId) {
    return $.grep($scope.webDriverVariables, function(webDriver) { return webDriver.type === typeId }).length;
  };

  $scope.removeWebDriverVariable = function(varName) {
    $scope.webDriverVariables = $.grep($scope.webDriverVariables, function(webDriver) { return webDriver.variableName !== varName; });
  };


  $scope.newWebDriverDialog = angular.extend(
    new ModalDialog("#newWebDriverDialog", { 
      trackerId : "newWebDriver",
      formName : "newWebDriverForm",
      getInitialValue : function() { return  { type : null, varName : "" }; }
    }), {

    submit : function() {
      var dialog = this;
      var varName = dialog.model.varName;
      var typeId = dialog.model.type.id;
      var request = service.get("/webDrivers/" + varName + "/create", { type : typeId }).success(function() {

        $scope.webDriverVariables.push({ variableName : varName, type : typeId, valid : true });
        // close modal
        dialog.hide();
        // reset model
        dialog.reset();

        $.bootstrapGrowl("Web driver <code>" + varName + "</code> created!", { type: "success" });
      });
      dialog.tracker.addPromise(request);
    }
  });

  $scope.editorPreferencesDialog = angular.extend(
    new ModalDialog("#editorPreferencesDialog", { 
      formName : "editorPreferencesForm",
      getInitialValue : function() { return $scope.editorPreferences; }
    }), {

    submit : function() {
      var dialog = this;
      // copy updated values to editorPreferences
      $scope.editorPreferences = angular.copy(dialog.model);
      configureEditor($scope.aceEditor, $scope.editorPreferences);
      $.cookie("editorPreferences", JSON.stringify($scope.editorPreferences), { expires : 365 * 5 /* 5 years */ });
      dialog.hide();

      $.bootstrapGrowl("Editor preferences updated!", { type: "success" });
    }
  });

  $scope.selectorGadgetDialog = angular.extend(
    new ModalDialog("#selectorGadgetDialog", { 
      formName : "selectorGadgetForm",
      trackerId : "selectorGadget",
      getInitialValue : function() {
        return {
          webDriver : $scope.webDriverVariables.length > 0 ? $scope.webDriverVariables[0].variableName : null
        };
      }
    }), {

    changedWebDriver : function() {      
      if (!this.model.webDriver) return;
      
      var dialog = this;
      var params = {};
      if (dialog.prevWebDriver && dialog.prevWebDriver !== dialog.model.webDriver) {
        params = { previousVar : dialog.prevWebDriver };
      }
      var request = service.get("/selectorGadget/" + dialog.model.webDriver + "/activate", params).success(function() {
        $.bootstrapGrowl("You can now select elements in <code>" + dialog.model.webDriver + "</code> window!", { type: "success" });
      });
      dialog.tracker.addPromise(request);
      dialog.prevWebDriver = dialog.model.webDriver;
    },

    acceptCssSelector : function() {
      var dialog = this;
      var varName = dialog.model.webDriver;
      var request = service.get("/selectorGadget/" + varName + "/cssSelector").success(function(data) {

        var session = $scope.aceEditor.getSession();
        var position = dialog.range.start;
        session.remove(dialog.range);
        session.insert(position, "$(" + varName + ", " + data + ")");
        // close modal
        dialog.hide();
        // reset model
        dialog.reset();

        $.bootstrapGrowl("Picked CSS selector is <code>" + data + "</code>!", { type: "success" });
      });
      dialog.tracker.addPromise(request);
    }
  });
  $scope.selectorGadgetDialog.elem.on("show", function() {
    $scope.selectorGadgetDialog.changedWebDriver();
  });
}