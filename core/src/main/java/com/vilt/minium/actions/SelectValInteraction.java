package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class SelectValInteraction.
 */
public class SelectValInteraction extends SelectionInteraction {

	private String val;

	/**
	 * Instantiates a new select val interaction.
	 *
	 * @param source the source
	 * @param val the val
	 */
	SelectValInteraction(WebElements source, String val) {
		super(source);
		this.val = val;
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getSelectElement().selectByValue(val);
	}
}
