package com.vilt.minium.recorder;

import static com.vilt.minium.actions.Interactions.perform;

import java.io.File;
import java.util.Locale;

import org.monte.screenrecorder.ScreenRecorder.State;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.WebElements;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.actions.InteractionListener;
import com.vilt.minium.actions.InteractionPerformer;
import com.vilt.minium.recorder.impl.MiniumScreenRecorder;

public class RecorderInteractions {

	private static MiniumScreenRecorder recorder;
	private static WebElementsDriver<CoreWebElements<?>> speechWebDriver;

	public static InteractionListener speechForTips() {
		return speechForTips(speechWebDriver, null);
	}

	public static InteractionListener speechForTips(WebElementsDriver<CoreWebElements<?>> wd) {
		return speechForTips(wd, null);
	}

	public static InteractionListener speechForTips(WebElementsDriver<CoreWebElements<?>> wd, Locale locale) {
		return new GoogleSpeechTipInteractionListener(wd, locale);
	}
	
	public static void speak(Locale locale, String text) {
		GoogleSpeechInteraction interaction = speechWebDriver == null ? 
			new ServerSideGoogleSpeechInteraction(locale, text) : new ClientSideGoogleSpeechInteraction(speechWebDriver, locale, text);
		perform(interaction);
		interaction.waitUntilCompleted();
	}

	public static void speak(String text) {
		speak(null, text);
	}

	public static void speakWith(WebElementsDriver<CoreWebElements<?>> wd) {
		speechWebDriver = wd;
	}

	public static void startRecordingScreen(String path) {
		startRecordingScreen(new File(path));
	}
	
	public static void startRecordingScreen(File file) {
		try {
			if (recorder != null && recorder.getState() == State.RECORDING) throw new IllegalStateException("Recording already in progress");
			recorder = new MiniumScreenRecorder(null);
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
			recorder = new MiniumScreenRecorder(null);
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
