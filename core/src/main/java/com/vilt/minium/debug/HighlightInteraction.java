package com.vilt.minium.debug;

import java.util.concurrent.TimeUnit;

import com.vilt.minium.Duration;
import com.vilt.minium.WebElements;
import com.vilt.minium.actions.AsyncTimeElapsedInteraction;

/**
 * The Class HighlightInteraction.
 */
public class HighlightInteraction extends AsyncTimeElapsedInteraction {

	/**
	 * Instantiates a new highlight interaction.
	 *
	 * @param elems the elems
	 */
	public HighlightInteraction(WebElements elems) {
		this(elems, new Duration(5, TimeUnit.SECONDS));
	}

	public HighlightInteraction(WebElements elems, Duration duration) {
		super(elems, duration);
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		super.doPerform();
		((DebugWebElements) getSource()).highlight();
	}
}
