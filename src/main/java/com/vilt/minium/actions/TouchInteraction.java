package com.vilt.minium.actions;

import com.vilt.minium.WebElements;

public interface TouchInteraction extends Interaction {
	
	// from org.openqa.selenium.WebElement
//	@Override public TouchInteraction addClear(WebElements elements);
//	
//	// from org.openqa.selenium.interactions.Actions
//	@Override public TouchInteraction addKeyDown(WebElements elements, Keys keys);
//	@Override public TouchInteraction addKeyUp(WebElements elements, Keys keys);
//	@Override public TouchInteraction addSendKeys(WebElements elements, CharSequence ... keys);
//	@Override public TouchInteraction addClickAndHold(WebElements elements);
//	@Override public TouchInteraction addRelease(WebElements elements);
//	@Override public TouchInteraction addClick(WebElements elements);
//	@Override public TouchInteraction addDoubleClick(WebElements elements);
//	@Override public TouchInteraction addMoveToElement(WebElements elements);
//	@Override public TouchInteraction addMoveToElement(WebElements elements, int xOffset, int yOffset);
//	@Override public TouchInteraction addMoveByOffset(WebElements elements, int xOffset, int yOffset);
//	@Override public TouchInteraction addContextClick(WebElements elements);
//	@Override public TouchInteraction addDragAndDrop(WebElements source, WebElements target);
//	@Override public TouchInteraction addDragAndDropBy(WebElements source, int xOffset, int yOffset);
//	
//	// additional methods
//	@Override public TouchInteraction addClickAll(WebElements elements);
//	@Override public TouchInteraction addType(WebElements elements, CharSequence text);
//		
//	/**
//	 * Clear and type
//	 * 
//	 * @param text
//	 */
//	@Override public TouchInteraction addFill(WebElements elements, CharSequence text);
//	
//	// select
//	@Override public TouchInteraction addSelect(WebElements elems, String text);
//	@Override public TouchInteraction addDeselect(WebElements elems, String text);
//	@Override public TouchInteraction addSelectVal(WebElements elems, String val);
//	@Override public TouchInteraction addDeselectVal(WebElements elems, String val);
//	@Override public TouchInteraction addSelectAll(WebElements elems);
//	@Override public TouchInteraction addDeselectAll(WebElements elems);
	
	// from org.openqa.selenium.interactions.touch.TouchActions
	public TouchInteraction addSingleTap(WebElements onElement);
	public TouchInteraction addDown(WebElements elements, int x, int y);
	public TouchInteraction addUp(WebElements elements, int x, int y);
	public TouchInteraction addMove(WebElements elements, int x, int y);
	public TouchInteraction addDoubleTap(WebElements onElement);
	public TouchInteraction addLongPress(WebElements onElement);
	public TouchInteraction addScroll(WebElements elements, int xOffset, int yOffset);
	public TouchInteraction addFlick(WebElements elements, int xSpeed, int ySpeed);
	public TouchInteraction addFlick(WebElements elements, int xOffset, int yOffset, int speed);
}
