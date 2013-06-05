/*
 * Copyright (C) 2013 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vilt.minium.actions;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;

import com.vilt.minium.Duration;
import com.vilt.minium.TimeoutException;
import com.vilt.minium.WebElements;

/**
 * The Class Interactions.
 */
public class Interactions {
	
	/**
	 * Timeout.
	 *
	 * @param time the time
	 * @param units the units
	 * @return the interaction listener
	 */
	public static InteractionListener timeout(long time, TimeUnit units) {
		return new TimeoutInteractionListener(new Duration(time, units));
	}

	/**
	 * Instant timeout.
	 *
	 * @return the interaction listener
	 */
	public static InteractionListener instantTimeout() {
		return timeout(0, SECONDS);
	}

	public static void get(WebElements elements, String url) {
		defaultPerformer().get(elements, url);
	}
	
	public static void close(WebElements elements) {
		defaultPerformer().close(elements);
	}
	
	// from org.openqa.selenium.WebElement
	/**
	 * Clear.
	 *
	 * @param elements the elements
	 */
	public static void clear(WebElements elements) {
		defaultPerformer().clear(elements);
	}

	// from org.openqa.selenium.interactions.Actions
	/**
	 * Key down.
	 *
	 * @param elements the elements
	 * @param keys the keys
	 */
	public static void keyDown(WebElements elements, Keys keys) {
		defaultPerformer().keyDown(elements, keys);
	}

	/**
	 * Key up.
	 *
	 * @param elements the elements
	 * @param keys the keys
	 */
	public static void keyUp(WebElements elements, Keys keys) {
		defaultPerformer().keyUp(elements, keys);
	}

	/**
	 * Send keys.
	 *
	 * @param elements the elements
	 * @param keys the keys
	 */
	public static void sendKeys(WebElements elements, CharSequence... keys) {
		defaultPerformer().sendKeys(elements, keys);
	}

	/**
	 * Click and hold.
	 *
	 * @param elements the elements
	 */
	public static void clickAndHold(WebElements elements) {
		defaultPerformer().clickAndHold(elements);
	}

	/**
	 * Release.
	 *
	 * @param elements the elements
	 */
	public static void release(WebElements elements) {
		defaultPerformer().release(elements);
	}

	/**
	 * Click.
	 *
	 * @param elements the elements
	 */
	public static void click(WebElements elements) {
		defaultPerformer().click(elements);
	}

	/**
	 * Double click.
	 *
	 * @param elements the elements
	 */
	public static void doubleClick(WebElements elements) {
		defaultPerformer().doubleClick(elements);
	}

	/**
	 * Move to element.
	 *
	 * @param elements the elements
	 */
	public static void moveToElement(WebElements elements) {
		defaultPerformer().moveToElement(elements);
	}

	/**
	 * Move to element.
	 *
	 * @param elements the elements
	 * @param xOffset the x offset
	 * @param yOffset the y offset
	 */
	public static void moveToElement(WebElements elements, int xOffset, int yOffset) {
		defaultPerformer().moveToElement(elements, xOffset, yOffset);
	}

	/**
	 * Move by offset.
	 *
	 * @param elements the elements
	 * @param xOffset the x offset
	 * @param yOffset the y offset
	 */
	public static void moveByOffset(WebElements elements, int xOffset, int yOffset) {
		defaultPerformer().moveByOffset(elements, xOffset, yOffset);
	}

	/**
	 * Context click.
	 *
	 * @param elements the elements
	 */
	public static void contextClick(WebElements elements) {
		defaultPerformer().contextClick(elements);
	}

	/**
	 * Drag and drop.
	 *
	 * @param source the source
	 * @param target the target
	 */
	public static void dragAndDrop(WebElements source, WebElements target) {
		defaultPerformer().dragAndDrop(source, target);
	}

	/**
	 * Drag and drop by.
	 *
	 * @param source the source
	 * @param xOffset the x offset
	 * @param yOffset the y offset
	 */
	public static void dragAndDropBy(WebElements source, int xOffset, int yOffset) {
		defaultPerformer().dragAndDropBy(source, xOffset, yOffset);
	}

	// additional methods
	/**
	 * Click all.
	 *
	 * @param elements the elements
	 */
	public static void clickAll(WebElements elements) {
		defaultPerformer().clickAll(elements);
	}

	/**
	 * Type.
	 *
	 * @param elements the elements
	 * @param text the text
	 */
	public static void type(WebElements elements, CharSequence text) {
		defaultPerformer().type(elements, text);
	}

	/**
	 * Clear and type.
	 *
	 * @param elements the elements
	 * @param text the text
	 */
	public static void fill(WebElements elements, CharSequence text) {
		defaultPerformer().fill(elements, text);
	}

	// select
	/**
	 * Select.
	 *
	 * @param elems the elems
	 * @param text the text
	 */
	public static void select(WebElements elems, String text) {
		defaultPerformer().select(elems, text);
	}

	/**
	 * Deselect.
	 *
	 * @param elems the elems
	 * @param text the text
	 */
	public static void deselect(WebElements elems, String text) {
		defaultPerformer().deselect(elems, text);
	}

	/**
	 * Select val.
	 *
	 * @param elems the elems
	 * @param val the val
	 */
	public static void selectVal(WebElements elems, String val) {
		defaultPerformer().selectVal(elems, val);
	}

	/**
	 * Deselect val.
	 *
	 * @param elems the elems
	 * @param val the val
	 */
	public static void deselectVal(WebElements elems, String val) {
		defaultPerformer().deselectVal(elems, val);
	}

	/**
	 * Select all.
	 *
	 * @param elems the elems
	 */
	public static void selectAll(WebElements elems) {
		defaultPerformer().selectAll(elems);
	}

	/**
	 * Deselect all.
	 *
	 * @param elems the elems
	 */
	public static void deselectAll(WebElements elems) {
		defaultPerformer().deselectAll(elems);
	}

	/**
	 * Wait for elements.
	 *
	 * @param elems the elems
	 * @deprecated use {@link Interactions#waitWhileEmpty(WebElements)}
	 */
	@Deprecated
	public static void waitForElements(WebElements elems) throws TimeoutException {
		waitWhileEmpty(elems);
	}

	public static void waitWhileEmpty(WebElements elems) throws TimeoutException {
		defaultPerformer().waitWhileEmpty(elems);
	}
	
	/**
	 * Wait while elements.
	 *
	 * @param elems the elems
	 * @deprecated use {@link Interactions#waitWhileNotEmpty(WebElements)}
	 */
	@Deprecated
	public static void waitWhileElements(WebElements elems) throws TimeoutException {
		defaultPerformer().waitWhileElements(elems);
	}

	public static void waitWhileNotEmpty(WebElements elems) throws TimeoutException {
		defaultPerformer().waitWhileNotEmpty(elems);
	}
	
	/**
	 * Check not empty.
	 *
	 * @param elems the elems
	 * @return true, if successful
	 */
	public static boolean checkNotEmpty(WebElements elems) {
		return defaultPerformer().checkNotEmpty(elems);
	}
	
	/**
	 * Check empty.
	 *
	 * @param elems the elems
	 * @return true, if successful
	 */
	public static boolean checkEmpty(WebElements elems) {
		return defaultPerformer().checkEmpty(elems);
	}
	
	/**
	 * Wait until closed.
	 *
	 * @param elems the elems
	 */
	public static void waitUntilClosed(WebElements elems) throws TimeoutException {
		defaultPerformer().waitUntilClosed(elems);
	}
	
	/**
	 * Wait time.
	 *
	 * @param time the time
	 * @param unit the unit
	 */
	public static void waitTime(long time, TimeUnit unit) {
		defaultPerformer().waitTime(time, unit);
	}
	
	/**
	 * Without waiting.
	 *
	 * @return the interaction performer
	 */
	public static InteractionPerformer withoutWaiting() {
		return with(instantTimeout());
	}

	/**
	 * With timeout.
	 *
	 * @param time the time
	 * @param units the units
	 * @return the interaction performer
	 */
	public static InteractionPerformer withTimeout(long time, TimeUnit units) {
		return with(timeout(time, units));
	}

	/**
	 * With.
	 *
	 * @param listeners the listeners
	 * @return the interaction performer
	 */
	public static InteractionPerformer with(InteractionListener ... listeners) {
		return new InteractionPerformer(listeners);
	}
	
	/**
	 * Perform.
	 *
	 * @param interaction the interaction
	 */
	public static void perform(Interaction interaction) {
		interaction.perform();
	}

	private static InteractionPerformer defaultPerformer() {
		return new InteractionPerformer();
	}
}
