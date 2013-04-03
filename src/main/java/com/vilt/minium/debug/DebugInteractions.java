package com.vilt.minium.debug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vilt.minium.WebElements;
import com.vilt.minium.actions.InteractionListener;
import com.vilt.minium.actions.InteractionPerformer;

/**
 * The Class DebugInteractions.
 */
public class DebugInteractions {

	/**
	 * Highlighter.
	 *
	 * @return the interaction listener
	 */
	public static InteractionListener highlighter() {
		return new HighlightListener();
	}

	/**
	 * Interaction logger.
	 *
	 * @return the interaction listener
	 */
	public static InteractionListener interactionLogger() {
		return new LogInteractionListener();
	}
	
	/**
	 * Highlight.
	 *
	 * @param webElements the web elements
	 */
	public static void highlight(WebElements webElements) {
		new InteractionPerformer().perform(new HighlightInteraction(webElements));
	}
	
	/**
	 * Take screenshot.
	 *
	 * @param webElements the web elements
	 * @param file the file
	 */
	public static void takeScreenshot(WebElements webElements, File file) {
		new InteractionPerformer().perform(new ElementsScreenshotInteraction(webElements, outputStreamFor(file)));
	}

	/**
	 * Take screenshot.
	 *
	 * @param webElements the web elements
	 * @param stream the stream
	 */
	public static void takeScreenshot(WebElements webElements, OutputStream stream) {
		new InteractionPerformer().perform(new ElementsScreenshotInteraction(webElements, stream));
	}
	
	/**
	 * Take window screenshot.
	 *
	 * @param webElements the web elements
	 * @param file the file
	 */
	public static void takeWindowScreenshot(WebElements webElements, File file) {
		new InteractionPerformer().perform(new WindowScreenshotInteraction(webElements, outputStreamFor(file)));
	}
	
	/**
	 * Take window screenshot.
	 *
	 * @param webElements the web elements
	 * @param stream the stream
	 */
	public static void takeWindowScreenshot(WebElements webElements, OutputStream stream) {
		new InteractionPerformer().perform(new WindowScreenshotInteraction(webElements, stream));
	}

	/**
	 * Output stream for.
	 *
	 * @param file the file
	 * @return the file output stream
	 */
	protected static FileOutputStream outputStreamFor(File file) {
		try {
			return new FileOutputStream(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
