package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class DeselectValInteraction.
 */
public class DeselectValInteraction extends SelectionInteraction {

	private String val;

	/**
	 * Instantiates a new deselect val interaction.
	 *
	 * @param source the source
	 * @param val the val
	 */
	DeselectValInteraction(WebElements source, String val) {
		super(source);
		this.val = val;
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getSelectElement().deselectByValue(val);
	}

}
