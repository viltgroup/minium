var app = angular
  .module("miniumWebConsole", [ "ajoslin.promise-tracker" ])
  .config([ "$routeProvider", function($routeProvider) {
    $routeProvider.
        // web drivers
        when("/webDrivers/create/:typeId", { templateUrl : "partials/dialog-webdriver-create.html"   , controller : WebDriverCreateCtrl   }).
        when("/webDrivers/list"          , { templateUrl : "partials/dialog-webdriver-list.html"     , controller : WebDriverListCtrl     }).

        // editor
        when("/editorPreferences"        , { templateUrl : "partials/dialog-editor-preferences.html" , controller : EditorPreferencesCtrl }).
        when("/selectorGadget/activate"  , { templateUrl : "partials/dialog-selector-gadget.html"    , controller : SelectorGadgetCtrl    }).

        // configuration
        when("/configuration"            , { templateUrl : "partials/dialog-configuration.html"      , controller : ConfigurationCtrl }).
        otherwise({redirectTo: ""});
  } ]);