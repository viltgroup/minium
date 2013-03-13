package com.vilt.minium.actions;

import org.openqa.selenium.Keys;

import com.vilt.minium.WebElements;

public interface Interaction {

	// from org.openqa.selenium.WebElement
	public Interaction clear(WebElements elements);
	
	// from org.openqa.selenium.interactions.Actions
	public Interaction keyDown(WebElements elements, Keys keys);
	public Interaction keyUp(WebElements elements, Keys keys);
	public Interaction sendKeys(WebElements elements, CharSequence ... keys);
	public Interaction clickAndHold(WebElements elements);
	public Interaction release(WebElements elements);
	public Interaction click(WebElements elements);
	public Interaction doubleClick(WebElements elements);
	public Interaction moveToElement(WebElements elements);
	public Interaction moveToElement(WebElements elements, int xOffset, int yOffset);
	public Interaction moveByOffset(WebElements elements, int xOffset, int yOffset);
	public Interaction contextClick(WebElements elements);
	public Interaction dragAndDrop(WebElements source, WebElements target);
	public Interaction dragAndDropBy(WebElements source, int xOffset, int yOffset);
	
	// from org.openqa.selenium.interactions.touch.TouchActions
	public Interaction singleTap(WebElements onElement);
	public Interaction down(WebElements elements, int x, int y);
	public Interaction up(WebElements elements, int x, int y);
	public Interaction move(WebElements elements, int x, int y);
	public Interaction doubleTap(WebElements onElement);
	public Interaction longPress(WebElements onElement);
	public Interaction scroll(WebElements elements, int xOffset, int yOffset);
	public Interaction flick(WebElements elements, int xSpeed, int ySpeed);
	public Interaction flick(WebElements elements, int xOffset, int yOffset, int speed);
	
	// additional methods
	public Interaction clickAll(WebElements elements);
	public Interaction type(WebElements elements, CharSequence text);
		
	/**
	 * Clear and type
	 * 
	 * @param text
	 */
	public Interaction fill(WebElements elements, CharSequence text);
	
	// select
	public Interaction select(WebElements elems, String text);
	public Interaction deselect(WebElements elems, String text);
	public Interaction selectVal(WebElements elems, String val);
	public Interaction deselectVal(WebElements elems, String val);
	public Interaction selectAll(WebElements elems);
	public Interaction deselectAll(WebElements elems);
}
