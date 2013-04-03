package com.vilt.minium.debug;

import java.io.OutputStream;

import com.vilt.minium.WebElements;
import com.vilt.minium.actions.DefaultInteraction;

/**
 * The Class ScreenshotInteraction.
 */
public abstract class ScreenshotInteraction extends DefaultInteraction {

	/** The stream. */
	protected OutputStream stream;

	/**
	 * Instantiates a new screenshot interaction.
	 *
	 * @param elems the elems
	 * @param stream the stream
	 */
	public ScreenshotInteraction(WebElements elems, OutputStream stream) {
		super(elems);
		this.stream = stream;
	}

}