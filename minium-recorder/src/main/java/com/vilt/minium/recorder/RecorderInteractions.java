package com.vilt.minium.recorder;

import java.io.File;

import org.monte.screenrecorder.ScreenRecorder.State;

import com.vilt.minium.WebElements;
import com.vilt.minium.actions.InteractionPerformer;
import com.vilt.minium.recorder.impl.WebElementsScreenRecorder;

public class RecorderInteractions {

	private static WebElementsScreenRecorder recorder;

	public static void startRecordingScreen(String path) {
		startRecordingScreen(new File(path));
	}
	
	public static void startRecordingScreen(File file) {
		try {
			if (recorder != null && recorder.getState() == State.RECORDING) throw new IllegalStateException("Recording already in progress");
			recorder = new WebElementsScreenRecorder(null);
			new InteractionPerformer().perform(new StartRecorderInteraction(null, recorder, file));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void startRecordingWindow(WebElements elems, String path) {
		startRecordingWindow(elems, new File(path));
	}

	public static void startRecordingWindow(WebElements elems, File file) {
		try {
			if (recorder != null && recorder.getState() == State.RECORDING) throw new IllegalStateException("Recording already in progress");
			recorder = new WebElementsScreenRecorder(null);
			new InteractionPerformer().perform(new StartRecorderInteraction(elems, recorder, file));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}	
	}

	public static void stopRecording() {
		try {
			new InteractionPerformer().perform(new StopRecorderInteraction(recorder));
		} finally {
			recorder = null;
		}
	}
}
