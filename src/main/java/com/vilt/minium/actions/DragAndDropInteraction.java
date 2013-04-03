package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

/**
 * The Class DragAndDropInteraction.
 */
public class DragAndDropInteraction extends MouseInteraction {

	private WebElements target;

	/**
	 * Instantiates a new drag and drop interaction.
	 *
	 * @param source the source
	 * @param target the target
	 */
	DragAndDropInteraction(WebElements source, WebElements target) {
		super(source);
		this.target = target;
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getActions().dragAndDrop(getFirstElement(), getFirstElement(target)).perform();
	}
}
