package com.vilt.minium.debug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vilt.minium.WebElements;
import com.vilt.minium.actions.InteractionListener;
import com.vilt.minium.actions.InteractionPerformer;

public class DebugInteractions {

	public static InteractionListener highlighter() {
		return new HighlightListener();
	}

	public static InteractionListener interactionLogger() {
		return new LogInteractionListener();
	}
	
	public static void highlight(WebElements webElements) {
		new InteractionPerformer().perform(new HighlightInteraction(webElements));
	}
	
	public static void takeScreenshot(WebElements webElements, File file) {
		new InteractionPerformer().perform(new ScreenshotInteraction(webElements, outputStreamFor(file)));
	}

	public static void takeScreenshot(WebElements webElements, OutputStream stream) {
		new InteractionPerformer().perform(new ScreenshotInteraction(webElements, stream));
	}
	
	public static void takeWindowScreenshot(WebElements webElements, File file) {
		new InteractionPerformer().perform(new WindowScreenshotInteraction(webElements, outputStreamFor(file)));
	}
	
	public static void takeWindowScreenshot(WebElements webElements, OutputStream stream) {
		new InteractionPerformer().perform(new WindowScreenshotInteraction(webElements, stream));
	}

	protected static FileOutputStream outputStreamFor(File file) {
		try {
			return new FileOutputStream(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
