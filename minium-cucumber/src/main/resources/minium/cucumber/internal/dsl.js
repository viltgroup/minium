var DataTable = function (cucumberDataTable) {
  this.cucumberDataTable = cucumberDataTable;
};

DataTable.prototype.raw = function() {
  var rawRows = this.cucumberDataTable.raw();
  var nativeRaw = [];

  for (var i = 0; i < rawRows.size(); i++) {
    var rawRow = rawRows.get(i);
    var nativeRawRow = [];
    for (var j = 0; j < rawRow.size(); j++) {
      nativeRawRow.push(String(rawRow.get(j)));
    }
    nativeRaw.push(nativeRawRow);
  }
  return nativeRaw;
};

DataTable.prototype.rows = function() {
  var raw = this.raw();
  var rows = [];

  for (var i = 1; i < raw.length; i++) {
    rows.push(raw[i]);
  }
  return rows;
};

DataTable.prototype.hashes = function() {
  var maps = this.cucumberDataTable.asMaps(Packages.java.lang.String, Packages.java.lang.String);
  var nativeMaps = [];

  for (var i = 0; i < maps.size(); i++) {
    var map = maps.get(i);
    var nativeMap = {};
    for (var iter = map.keySet().iterator(); iter.hasNext();) {
      var prop = String(iter.next());
      nativeMap[prop] = String(map.get(prop));
    }
    nativeMaps.push(nativeMap);
  }
  return nativeMaps;
};

DataTable.prototype.rowsHash = function() {
  var map = this.cucumberDataTable.asMap(Packages.java.lang.String, Packages.java.lang.String);

  var nativeMap = {};
  for (var iter = map.keySet().iterator(); iter.hasNext();) {
    var prop = String(iter.next());
    nativeMap[prop] = String(map.get(prop));
  }

  return nativeMap;
};


var registerStepDefinition = function(regexp, bodyFunc) {
    
    var isCucumberDataTable = function (arg) {
      // for some reason arg instanceof Packages.cucumber.api.DataTable doesn't seem to work...
      return arg instanceof Packages.cucumber.api.DataTable || (arg.getClass && arg.getClass() === Packages.cucumber.api.DataTable);
    }
    
    var convertArguments = function (args) {
      var convertedArgs = [];
      for (var i = 0; i < args.length; i++) {
        var arg = args[i];
        if (arg instanceof Packages.java.lang.String) {
          convertedArgs.push(String(arg));
        } else if (isCucumberDataTable(arg)) {
          convertedArgs.push(new DataTable(arg));
        } else {
          convertedArgs.push(arg);
        }
      }
      return convertedArgs;
    };
        
    // we need fn to have fn.length === bodyFunc.length
    // from http://stackoverflow.com/a/13271752
    var giveArity = function (f, n) {
      var argNames = [];
      for (var i = 0; i < n; i++) {
        argNames.push("arg" + i);
      }
      return eval("(function(" + argNames.join(", ") + ") { return f.apply(this, arguments); })");
    };
    
    var argumentsFromFunc = function(stepName) {
        var match = regexp.exec(stepName);
        if (match) {
            var args = new Packages.java.util.ArrayList();
            var s = match[0];
            for (var i = 1; i < match.length; i++) {
                var arg = match[i];
                var offset = s.indexOf(arg, offset);
                args.add(new Packages.gherkin.formatter.Argument(offset, arg));
            }
            return args;
        } else {
            return null;
        }
    };
    
    var fn = giveArity(function () {
      bodyFunc.apply(jsBackend, convertArguments(arguments));
    }, bodyFunc.length);

    jsBackend.addStepDefinition(jsBackend, regexp, fn, argumentsFromFunc);
};

var registerHookDefinition = function(addHookFn, fn, tags, opts) {
    if (tags) {
        // if tags is a string, convert it into an array
        if (typeof tags === "string") {
            tags = [ tags ];
        }
    } else {
        tags = [];
    }

    tags = tags instanceof Array ? tags : [];
    opts = opts || {};

    var order = opts.order || 1000;
    var timeout = opts.timeout || 0;
    addHookFn.call(jsBackend, fn, tags, order, timeout);
};

Before = function(fn, tags, opts) {
    registerHookDefinition(jsBackend.addBeforeHook, fn, tags, opts);
};

After = function(fn, tags, opts) {
    registerHookDefinition(jsBackend.addAfterHook, fn, tags, opts);
};

var Given = registerStepDefinition;
var When = registerStepDefinition;
var Then = registerStepDefinition;

var World = function(buildFn, disposeFn) {
    jsBackend.registerWorld(buildFn, disposeFn || null);
};
