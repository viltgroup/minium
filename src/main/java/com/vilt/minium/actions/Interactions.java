package com.vilt.minium.actions;

import static com.vilt.minium.Minium.untilNotEmpty;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import com.vilt.minium.WebElements;
import com.vilt.minium.jquery.CoreWebElements;

public class Interactions {
	
	// from org.openqa.selenium.WebElement
	public static void clear(WebElements elements) {
		defaultPerformer().clear(elements);
	}

	// from org.openqa.selenium.interactions.Actions
	public static void keyDown(WebElements elements, Keys keys) {
		defaultPerformer().keyDown(elements, keys);
	}

	public static void keyUp(WebElements elements, Keys keys) {
		defaultPerformer().keyUp(elements, keys);
	}

	public static void sendKeys(WebElements elements, CharSequence... keys) {
		defaultPerformer().sendKeys(elements, keys);
	}

	public static void clickAndHold(WebElements elements) {
		defaultPerformer().clickAndHold(elements);
	}

	public static void release(WebElements elements) {
		defaultPerformer().release(elements);
	}

	public static void click(WebElements elements) {
		defaultPerformer().click(elements);
	}

	public static void doubleClick(WebElements elements) {
		defaultPerformer().doubleClick(elements);
	}

	public static void moveToElement(WebElements elements) {
		defaultPerformer().moveToElement(elements);
	}

	public static void moveToElement(WebElements elements, int xOffset, int yOffset) {
		defaultPerformer().moveToElement(elements, xOffset, yOffset);
	}

	public static void moveByOffset(WebElements elements, int xOffset, int yOffset) {
		defaultPerformer().moveByOffset(elements, xOffset, yOffset);
	}

	public static void contextClick(WebElements elements) {
		defaultPerformer().contextClick(elements);
	}

	public static void dragAndDrop(WebElements source, WebElements target) {
		defaultPerformer().dragAndDrop(source, target);
	}

	public static void dragAndDropBy(WebElements source, int xOffset, int yOffset) {
		defaultPerformer().dragAndDropBy(source, xOffset, yOffset);
	}

	// additional methods
	public static void clickAll(WebElements elements) {
		defaultPerformer().clickAll(elements);
	}

	public static void type(WebElements elements, CharSequence text) {
		defaultPerformer().type(elements, text);
	}

	/**
	 * Clear and type
	 * 
	 * @param text
	 */
	public static void fill(WebElements elements, CharSequence text) {
		defaultPerformer().fill(elements, text);
	}

	// select
	public static void select(WebElements elems, String text) {
		defaultPerformer().select(elems, text);
	}

	public static void deselect(WebElements elems, String text) {
		defaultPerformer().deselect(elems, text);
	}

	public static void selectVal(WebElements elems, String val) {
		defaultPerformer().selectVal(elems, val);
	}

	public static void deselectVal(WebElements elems, String val) {
		defaultPerformer().deselectVal(elems, val);
	}

	public static void selectAll(WebElements elems) {
		defaultPerformer().selectAll(elems);
	}

	public static void deselectAll(WebElements elems) {
		defaultPerformer().deselectAll(elems);
	}

	public void waitForElements(WebElements elems) {
		defaultPerformer().waitForElements(elems);
	}
	
	public void waitWhileElements(WebElements elems) {
		defaultPerformer().waitWhileElements(elems);
	}
	
	public boolean checkNotEmpty(WebElements elems) {
		return defaultPerformer().checkNotEmpty(elems);
	}
	
	public boolean checkEmpty(WebElements elems) {
		return defaultPerformer().checkEmpty(elems);
	}
	
	public void waitUntilClosed(WebElements elems) {
		defaultPerformer().waitUntilClosed(elems);
	}
	
	public static InteractionPerformer with(InteractionListener ... listeners) {
		return new InteractionPerformer(listeners);
	}
	
	public static void perform(Interaction interaction) {
		interaction.perform();
	}

	protected static WebElements getFirst(WebElements elems) {
		WebElements first = ((CoreWebElements<?>) elems).visible().first();
		((CoreWebElements<?>) first).wait(untilNotEmpty());
		return first;
	}

	protected static WebElement getFirstElement(WebElements elems) {
		WebElements first = getFirst(elems);
		return first.get(0);
	}
	
	private static InteractionPerformer defaultPerformer() {
		return new InteractionPerformer();
	}
}
