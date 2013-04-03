package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class DeselectInteraction.
 */
public class DeselectInteraction extends SelectionInteraction {

	private String text;

	/**
	 * Instantiates a new deselect interaction.
	 *
	 * @param source the source
	 * @param text the text
	 */
	DeselectInteraction(WebElements source, String text) {
		super(source);
		this.text = text;
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getSelectElement().deselectByVisibleText(text);
	}
}
