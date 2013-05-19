importPackage(java.lang);
importPackage(java.util.concurrent);

importPackage(org.openqa.selenium);
importPackage(org.openqa.selenium.chrome);
importPackage(org.openqa.selenium.ie);
importPackage(org.openqa.selenium.firefox);
importPackage(org.openqa.selenium.remote);

importPackage(com.vilt.minium);
importPackage(com.vilt.minium.screen);

var MILLISECONDS = TimeUnit.MILLISECONDS;
var SECONDS      = TimeUnit.SECONDS;
var MINUTES      = TimeUnit.MINUTES;
var HOURS        = TimeUnit.HOURS;
var DAYS         = TimeUnit.DAYS;

var ENTER        = Keys.ENTER;

function createMethodsFn(obj) {
	for(var fn in obj) {
		if (typeof obj[fn] === 'function') {
			this[fn] = (function() {
				var method = obj[fn];
				return function() {
					return method.apply(obj, arguments);
				};
			})();
		}
	}
}

function importStatic(obj) {
	createMethodsFn(obj);
}

// all methods from _webDriverFactory
createMethodsFn(_webDriverFactory);
createMethodsFn(_controller);

// all static methods from Minium, Interactions and TouchInteractions
importStatic(com.vilt.minium.Minium);
importStatic(com.vilt.minium.actions.Interactions);
importStatic(com.vilt.minium.actions.touch.TouchInteractions);
importStatic(com.vilt.minium.debug.DebugInteractions);
importStatic(com.vilt.minium.tips.TipInteractions);
importStatic(com.vilt.minium.recorder.RecorderInteractions);

function recordScreen(file, fn) {
	startRecordingScreen(file);
	try {
		return fn();
	} finally {
		stopRecording();
	}
}

function recordWindow(elems, file, fn) {
	startRecordingWindow(elems, file);
	try {
		return fn();
	} finally {
		stopRecording();
	}
}