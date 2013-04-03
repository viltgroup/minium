package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class TypeInteraction.
 */
public class TypeInteraction extends KeyboardInteraction {

	private CharSequence text;

	/**
	 * Instantiates a new type interaction.
	 *
	 * @param source the source
	 * @param text the text
	 */
	TypeInteraction(WebElements source, CharSequence text) {
		super(source);
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getFirstElement().sendKeys(text);
	}
}
