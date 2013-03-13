package com.vilt.minium.actions;

import static com.vilt.minium.actions.Interactions.getFirstElement;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.internal.WrapsDriver;

import com.vilt.minium.WebElements;

public class TouchInteractions {
	
	// from org.openqa.selenium.interactions.touch.TouchActions
	public static void singleTap(WebElements elements) {
		WebElement elem = getFirstElement(elements);
		newTouchActions(elem).singleTap(elem).perform();
	}
	
	public static void down(WebElements elements, int x, int y) {
		WebElement elem = getFirstElement(elements);
		newTouchActions(elem).down(x, y).perform();
	}
	
	public static void up(WebElements elements, int x, int y) {
		WebElement elem = getFirstElement(elements);
		newTouchActions(elem).up(x, y).perform();
	}
	
	public static void move(WebElements elements, int x, int y) {
		WebElement elem = getFirstElement(elements);
		newTouchActions(elem).move(x, y).perform();
	}
	
	public static void doubleTap(WebElements elements) {
		WebElement elem = getFirstElement(elements);
		newTouchActions(elem).doubleTap(elem).perform();
	}
	
	public static void longPress(WebElements elements) {
		WebElement elem = getFirstElement(elements);
		newTouchActions(elem).longPress(elem).perform();
	}
	
	public static void scroll(WebElements elements, int xOffset, int yOffset) {
		WebElement elem = getFirstElement(elements);
		newTouchActions(elem).scroll(elem, xOffset, yOffset).perform();
	}
	
	public static void flick(WebElements elements, int xSpeed, int ySpeed) {
		WebElement elem = getFirstElement(elements);
		newTouchActions(elem).flick(xSpeed, ySpeed).perform();
	}
	
	public static void flick(WebElements elements, int xOffset, int yOffset, int speed) {
		WebElement elem = getFirstElement(elements);
		newTouchActions(elem).flick(elem, xOffset, yOffset, speed).perform();
	}
	
	private static TouchActions newTouchActions(WebElement elem) {
		return new TouchActions(((WrapsDriver) elem).getWrappedDriver());
	}
}
