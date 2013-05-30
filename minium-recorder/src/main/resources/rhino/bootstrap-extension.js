importPackage(com.vilt.minium.recorder);

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