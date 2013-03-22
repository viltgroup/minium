package com.vilt.minium.actions;

import org.openqa.selenium.Keys;

import com.google.common.collect.Iterables;
import com.vilt.minium.WebElements;

public class InteractionPerformer {
	
	private InteractionListener[] listeners;

	public InteractionPerformer(InteractionListener ... listeners) {
		this.listeners = listeners;
	}
	
	public void perform(Interaction interaction) {
		for (InteractionListener listener : listeners) {
			interaction.registerListener(listener);
		}
		interaction.perform();
	}
	
	// from org.openqa.selenium.WebElement
	public void clear(WebElements elements) {
		perform(new ClearInteraction(elements));
	}
	
	// from org.openqa.selenium.interactions.Actions
	public void keyDown(WebElements elements, Keys keys) {
		perform(new KeyDownInteraction(elements, keys));
	}
	
	public void keyUp(WebElements elements, Keys keys) {
		perform(new KeyUpInteraction(elements, keys));
	}
	
	public void sendKeys(WebElements elements, CharSequence ... keys) {
		perform(new SendKeysInteraction(elements, keys));
	}
	
	public void clickAndHold(WebElements elements) {
		perform(new ClickAndHoldInteraction(elements));
	}
	
	public void release(WebElements elements) {
		perform(new ButtonReleaseInteraction(elements));
	}
	
	public void click(WebElements elements) {
		perform(new ClickInteraction(elements));		
	}
	
	public void doubleClick(WebElements elements) {
		perform(new DoubleClickInteraction(elements));		
	}
	
	public void moveToElement(WebElements elements) {
		perform(new MoveMouseInteraction(elements, 0, 0));
	}
	
	public void moveToElement(WebElements elements, int xOffset, int yOffset) {
		perform(new MoveMouseInteraction(elements, xOffset, yOffset));
	}
	
	public void moveByOffset(WebElements elements, int xOffset, int yOffset) {
		perform(new MoveByOffsetInteraction(elements, xOffset, yOffset));		
	}
	
	public void contextClick(WebElements elements) {
		perform(new ContextClickInteraction(elements));
	}
	
	public void dragAndDrop(WebElements source, WebElements target) {
		perform(new DragAndDropInteraction(source, target));
	}
	
	public void dragAndDropBy(WebElements source, int xOffset, int yOffset) {
		perform(new DragAndDropByInteraction(source, xOffset, yOffset));
	}
	
	// additional methods
	public void clickAll(WebElements elements) {
		perform(new ClickAllInteraction(elements));
	}
	
	public void type(WebElements elements, CharSequence text) {
		perform(new TypeInteraction(elements, text));
	}
		
	/**
	 * Clear and type
	 * 
	 * @param text
	 */
	public void fill(WebElements elements, CharSequence text) {
		perform(new FillInteraction(elements, text));
	}
	
	// select
	public void select(WebElements elems, String text) {
		perform(new SelectInteraction(elems, text));
	}
	
	public void deselect(WebElements elems, String text) {
		perform(new DeselectInteraction(elems, text));		
	}
	
	public void selectVal(WebElements elems, String val) {
		perform(new SelectValInteraction(elems, val));		
	}
	
	public void deselectVal(WebElements elems, String val) {
		perform(new DeselectValInteraction(elems, val));				
	}
	
	public void selectAll(WebElements elems) {
		perform(new SelectAllInteraction(elems));						
	}
	
	public void deselectAll(WebElements elems) {
		perform(new DeselectAllInteraction(elems));						
	}
	
	public void waitForElements(WebElements elems) {
		perform(new WaitFoElementsInteraction(elems));
	}
	
	public void waitWhileElements(WebElements elems) {
		perform(new WaitWhileElementsInteraction(elems));
	}
	
	public boolean checkNotEmpty(WebElements elems) {
		perform(new WaitOrTimeoutForElementsInteraction(elems));
		return !Iterables.isEmpty(elems);
	}
	
	public boolean checkEmpty(WebElements elems) {
		perform(new WaitOrTimeoutWhileElementsInteraction(elems));
		return Iterables.isEmpty(elems);
	}
	
	public void waitUntilClosed(WebElements elems) {
		perform(new WaitWindowClosedElementsInteraction(elems));
	}
}