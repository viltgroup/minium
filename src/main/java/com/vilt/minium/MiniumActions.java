package com.vilt.minium;

import java.util.concurrent.TimeUnit;

import com.vilt.minium.jquery.DefaultAction;
import com.vilt.minium.jquery.DefaultWebElements;

/**
 * The Class MiniumActions.
 */
public class MiniumActions {

	/**
	 * Click.
	 *
	 * @param elems the elems
	 */
	public static void click(DefaultWebElements elems) {
		new DefaultAction().click(elems);
	}
	

	/**
	 * Right click.
	 *
	 * @param elems the elems
	 */
	public static void rightClick(DefaultWebElements elems) {
		new DefaultAction().rightClick(elems);
	}
	
	/**
	 * Click all.
	 *
	 * @param elems the elems
	 */
	public static void clickAll(DefaultWebElements elems) {
		new DefaultAction().clickAll(elems);
	}
	
	
	/**
	 * Move to.
	 *
	 * @param elems the elems
	 */
	public static void moveTo(DefaultWebElements elems) {
		new DefaultAction().moveMouse(elems);
	}

	/**
	 * Move and click.
	 *
	 * @param elems the elems
	 */
	public static void moveAndClick(DefaultWebElements elems) {
		new DefaultAction().moveAndClick(elems);
	}
	
	/**
	 * Move to.
	 *
	 * @param from the from
	 * @param to the to
	 */
	public static void moveTo(DefaultWebElements from, DefaultWebElements to) {
//		new DefaultAction().moveMouse(from, to);
	}
	
	/**
	 * Send keys.
	 *
	 * @param elems the elems
	 * @param keys the keys
	 */
	public static void sendKeys(DefaultWebElements elems, CharSequence ... keys) {
		new DefaultAction().sendKeys(elems, keys);
	}
	
	/**
	 * Clear.
	 *
	 * @param elems the elems
	 */
	public static void clear(DefaultWebElements elems) {
		new DefaultAction().clear(elems);
	}
	
	/**
	 * Clear and type.
	 *
	 * @param elems the elems
	 * @param text the text
	 */
	public static void clearAndType(DefaultWebElements elems, String text) {
		new DefaultAction().clearAndType(elems, text);
	}
	
	/**
	 * Type.
	 *
	 * @param elems the elems
	 * @param text the text
	 */
	public static void type(DefaultWebElements elems, String text) {
		new DefaultAction().type(elems, text);
	}
	
	/**
	 * Select.
	 *
	 * @param elems the elems
	 * @param text the text
	 */
	public static void select(DefaultWebElements elems, String text) {
		new DefaultAction().select(elems, text);
	}
	
	/**
	 * Select.
	 *
	 * @param elems the elems
	 * @param text the text
	 */
	public static void deselect(DefaultWebElements elems, String text) {
		new DefaultAction().deselect(elems, text);
	}
	
	/**
	 * Wait for elements.
	 *
	 * @param elems the elems
	 */
	public static void waitForElements(DefaultWebElements elems) {
		new DefaultAction().forElements(elems);
	}
	
	/**
	 * Wait while elements.
	 *
	 * @param elems the elems
	 */
	public static void waitWhileElements(DefaultWebElements elems) {
		new DefaultAction().whileElements(elems);
	}

	/**
	 * Check not empty.
	 *
	 * @param elems the elems
	 * @return true, if successful
	 */
	public static boolean checkNotEmpty(DefaultWebElements elems) {
		return new DefaultAction().checkNotEmpty(elems);
	}

	/**
	 * Check empty.
	 *
	 * @param elems the elems
	 * @return true, if successful
	 */
	public static boolean checkEmpty(DefaultWebElements elems) {
		return new DefaultAction().checkEmpty(elems);
	}
	
	/**
	 * Wait until closed.
	 *
	 * @param elems the elems
	 */
	public static void waitUntilClosed(DefaultWebElements elems) {
		new DefaultAction().waitUntilClosed(elems);
	}
	
	public static void highlight(DefaultWebElements elems) {
		new DefaultAction().highlight(elems);
	}


	/**
	 * Wait time.
	 *
	 * @param time the time
	 * @param unit the unit
	 */
	public static void waitTime(long time, TimeUnit unit) {
		new DefaultAction().waitTime(time, unit);
	}
	
	/**
	 * Without waiting.
	 *
	 * @return the default action
	 */
	public static DefaultAction withoutWaiting() {
		return withWaitTimeout(0, TimeUnit.SECONDS);
	}

	/**
	 * With wait timeout.
	 *
	 * @param time the time
	 * @param units the units
	 * @return the default action
	 */
	public static DefaultAction withWaitTimeout(long time, TimeUnit units) {
		return new DefaultAction(time, units);
	}
	
	/**
	 * With tip.
	 *
	 * @param text the text
	 * @return the default action
	 */
	public static DefaultAction withTip(String text) {
		return new DefaultAction().withTip(text);
	}
	
	/**
	 * With tip.
	 *
	 * @param text the text
	 * @param time the time
	 * @param unit the unit
	 * @return the default action
	 */
	public static DefaultAction withTip(String text, long time, TimeUnit unit) {
		return new DefaultAction().withTip(text, time, unit);
	}
	
	
}
