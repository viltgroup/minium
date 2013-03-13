package com.vilt.minium.actions;

import static com.vilt.minium.Minium.untilNotEmpty;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.ui.Select;

import com.vilt.minium.WebElements;
import com.vilt.minium.jquery.CoreWebElements;

public class Interactions {

	// from org.openqa.selenium.WebElement
	public static void clear(WebElements elements) {
		getFirstElement(elements).clear();
	}

	// from org.openqa.selenium.interactions.Actions
	public static void keyDown(WebElements elements, Keys keys) {
		WebElement elem = getFirstElement(elements);
		newActions(elem).keyDown(elem, keys).perform();
	}

	public static void keyUp(WebElements elements, Keys keys) {
		WebElement elem = getFirstElement(elements);
		newActions(elem).keyUp(elem, keys).perform();
	}

	public static void sendKeys(WebElements elements, CharSequence... keys) {
		WebElement elem = getFirstElement(elements);
		newActions(elem).sendKeys(elem, keys).perform();
	}

	public static void clickAndHold(WebElements elements) {
		WebElement elem = getFirstElement(elements);
		newActions(elem).clickAndHold(elem).perform();
	}

	public static void release(WebElements elements) {
		WebElement elem = getFirstElement(elements);
		newActions(elem).release(elem).perform();
	}

	public static void click(WebElements elements) {
		WebElement elem = getFirstElement(elements);
		newActions(elem).click(elem).perform();
	}

	public static void doubleClick(WebElements elements) {
		WebElement elem = getFirstElement(elements);
		newActions(elem).doubleClick(elem).perform();
	}

	public static void moveToElement(WebElements elements) {
		WebElement elem = getFirstElement(elements);
		newActions(elem).moveToElement(elem).perform();
	}

	public static void moveToElement(WebElements elements, int xOffset, int yOffset) {
		WebElement elem = getFirstElement(elements);
		newActions(elem).moveToElement(elem, xOffset, yOffset).perform();
	}

	public static void moveByOffset(WebElements elements, int xOffset, int yOffset) {
		WebElement elem = getFirstElement(elements);
		newActions(elem).moveByOffset(xOffset, yOffset).perform();
	}

	public static void contextClick(WebElements elements) {
		WebElement elem = getFirstElement(elements);
		newActions(elem).contextClick(elem).perform();
	}

	public static void dragAndDrop(WebElements source, WebElements target) {
		WebElement sourceElem = getFirstElement(source);
		WebElement targetElem = getFirstElement(target);
		newActions(sourceElem).dragAndDrop(sourceElem, targetElem).perform();
	}

	public static void dragAndDropBy(WebElements source, int xOffset, int yOffset) {
		WebElement elem = getFirstElement(source);
		newActions(elem).dragAndDropBy(elem, xOffset, yOffset).perform();
	}

	// additional methods
	public static void clickAll(WebElements elements) {
	}

	public static void type(WebElements elements, CharSequence text) {
		sendKeys(elements, text);
	}

	/**
	 * Clear and type
	 * 
	 * @param text
	 */
	public static void fill(WebElements elements, CharSequence text) {
		WebElement elem = getFirstElement(elements);
		elem.clear();
		elem.sendKeys(text);
	}

	// select
	public static void select(WebElements elems, String text) {
		WebElement elem = getFirstElement(elems);
		new Select(elem).selectByVisibleText(text);
	}

	public static void deselect(WebElements elems, String text) {
		WebElement elem = getFirstElement(elems);
		new Select(elem).deselectByVisibleText(text);
	}

	public static void selectVal(WebElements elems, String val) {
		WebElement elem = getFirstElement(elems);
		new Select(elem).selectByValue(val);
	}

	public static void deselectVal(WebElements elems, String val) {
		WebElement elem = getFirstElement(elems);
		new Select(elem).deselectByValue(val);
	}

	public static void selectAll(WebElements elems) {
		WebElement elem = getFirstElement(elems);
		Select select = new Select(elem);
		if (!select.isMultiple()) {
			throw new UnsupportedOperationException("You may only select all options of a multi-select");
		}

		for (WebElement option : select.getOptions()) {
			if (option.isSelected()) {
				option.click();
			}
		}
	}

	public static void deselectAll(WebElements elems) {
		WebElement elem = getFirstElement(elems);
		new Select(elem).deselectAll();
	}

	// ////
	// public static void interact(Interaction interaction);

	protected static WebElements getFirst(WebElements elems) {
		WebElements first = ((CoreWebElements<?>) elems).visible().first();
		((CoreWebElements<?>) first).wait(untilNotEmpty());
		return first;
	}

	protected static WebElement getFirstElement(WebElements elems) {
		WebElements first = getFirst(elems);
		return first.get(0);
	}

	private static Actions newActions(WebElement elem) {
		return new Actions(((WrapsDriver) elem).getWrappedDriver());
	}
}
