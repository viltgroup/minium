package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class SelectInteraction.
 */
public class SelectInteraction extends SelectionInteraction {

	private String text;

	/**
	 * Instantiates a new select interaction.
	 *
	 * @param source the source
	 * @param text the text
	 */
	SelectInteraction(WebElements source, String text) {
		super(source);
		this.text = text;
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getSelectElement().selectByVisibleText(text);
	}

}
