/*
 * Copyright (C) 2013 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vilt.minium.debug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.vilt.minium.WebElements;
import com.vilt.minium.actions.InteractionListener;
import com.vilt.minium.actions.InteractionPerformer;
import com.vilt.minium.impl.debug.ElementsScreenshotInteraction;
import com.vilt.minium.impl.debug.HighlightInteraction;
import com.vilt.minium.impl.debug.HighlightListener;
import com.vilt.minium.impl.debug.LogInteractionListener;
import com.vilt.minium.impl.debug.WindowScreenshotInteraction;

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
	public static void takeWindowScreenshot(WebElements webElements, String file) {
		new InteractionPerformer().perform(new WindowScreenshotInteraction(webElements, outputStreamFor(file)));
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

	/**
	 * Output stream for.
	 *
	 * @param file the file
	 * @return the file output stream
	 */
	protected static FileOutputStream outputStreamFor(String file) {
		try {
			return new FileOutputStream(file);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
