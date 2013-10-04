origSpeak = speak;
speak = function() {};

importStatic(com.vilt.minium.JavascriptFunctions);

function debug(arg) {
  $(wd).eval(parse("function() { console.debug(arguments[0]); }"), arg);
}

function call(fn) {
  var args = Array.prototype.slice.call(arguments);
  args[0] = parse(fn.toString());
  return $(wd).call.apply($(wd), args);
}

function getCode(fn) {
  var match = /^function\s*\(\s*\)\s*\{((?:.*\s*)*)\}$/mg.exec(fn.toString());
  if (match) {
    var code = match[1];
    var lines = code.split(/\r?\n/);
    var indentation;
    for (var i = 0; i < lines.length; i++) {
      var indentMatch = /^(\s+)\S.*/.exec(lines[i]);
      if (indentMatch) {
        indentation = indentMatch[1];
        break;
      }
    }
    if (!indentation) return code;
	
    for (var i = 0; i < lines.length; i++) {
      var tabs = "";
      var firstTabIgnored = false;
      while (lines[i].indexOf(indentation) === 0) {
        lines[i] = lines[i].substr(indentation.length);
        if (firstTabIgnored) {
          tabs += "  ";
        } else {
          firstTabIgnored = true;
        }
      }
      lines[i] = tabs + lines[i];
    }
    return lines.join("\n");
  }
}

function writeCode(fn) {
  var code = getCode(fn);
  if (code) {
    call(function(code) {
      var editor = ace.edit("editor");
      var session = editor.getSession();
      var position = editor.getSelectionRange().start;
      session.insert(position, code);
    }, code);
  }
}

function press() {
  sendKeys($(wd), arguments.length == 1 ? arguments[0] : Keys.chord(Array.prototype.slice.call(arguments)));
}

speak("Hi, I'm Minium. Let me show the features of my new web console.");

var navbar = $(wd, ".navbar-nav");
var webdriversDropdown = $(wd, ".dropdown-toggle").withText("Web Drivers");
var preferencesDropdown = $(wd, ".dropdown-toggle").withText("Preferences");
var editorPreferencesOption = $(wd, ".dropdown-menu a").withText("Editor...");

// editor fields
var fontSizeFld = $(wd, "input").withLabel("Font size");
var themeFld = $(wd, "select").withLabel("Theme");
var tabSizeFld = $(wd, "input").withLabel("Tab size");
var softTabsFld = $(wd, ":checkbox").withName("softTabs");
var saveBtn = $(wd, ".btn").withText("Save changes");
var closeBtn = $(wd, ".btn").withText("Close");

// web drivers
var newChromeOption = $(wd, "#browsers li a").containingText("Chrome");
var variableFld = $(wd, "input").withLabel("Variable");
var createBtn = $(wd, ".btn").withText("Create");

get(wd, "http://localhost:8080/minium-webconsole/");
showTip(navbar, "There is a navigation bar at the top, where you can...");

click(webdriversDropdown);
showTip(webdriversDropdown, "... manage your web drivers");
click(preferencesDropdown);
showTip(preferencesDropdown, "... and the console preferences");

withTip("Let's start with the editor preferences.").click(editorPreferencesOption);
withTip("Here you can change the font size...").fill(fontSizeFld, "14");
withTip("...the theme...").select(themeFld, "Twilight");
withTip("...the tab size...").fill(tabSizeFld, "4");
withTip("...and whether if tabs are replaced with spaces (soft tabs)...").uncheck(softTabsFld);

withTip("Let's save the changes").click(saveBtn);

click(webdriversDropdown);
click(newChromeOption);
fill(variableFld, "demowd");
click(createBtn);

writeCode(function() {
  get(newwd, "http://www.google.com");
  
  speak("Hello, I'm Minium, and I'm alive");
  speak("Let me highlight google search box");
  
  searchbox = $(newwd, ":text").withName("q");
  highlight(searchbox);
  
  speak("Minium = Minion + Selenium. Let's find out what is a Minion.");
  
  fill(searchbox, "Minion Wikipedia");
  sendKeys(searchbox, Keys.ENTER);
  
  firstResult = $(newwd, "h3 a").first();
  click(firstResult);
  
  firstParagraph = $(newwd, "#mw-content-text p").first();
  highlight(firstParagraph);
  
  speak("Wikipedia says: " + firstParagraph.text());
});

