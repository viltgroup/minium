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

import static com.vilt.minium.Minium.$;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Keys;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.Duration;
import com.vilt.minium.TimeoutException;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.impl.actions.RetryAfterWaitingWhileEmptyInteractionListener;
import com.vilt.minium.impl.actions.RetryOnExceptionInteractionListener;
import com.vilt.minium.impl.actions.SlowMotionInteractionListener;
import com.vilt.minium.impl.actions.TimeoutInteractionListener;
import com.vilt.minium.impl.actions.WaitingPresetInteractionListener;

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
     * Timeout.
     *
     * @param preset the waiting preset
     * @return the interaction listener
     */
    public static InteractionListener waitingPreset(String preset) {
        return new WaitingPresetInteractionListener(preset);
    }

    /**
     * Instant timeout.
     *
     * @return the interaction listener
     */
    public static InteractionListener instantTimeout() {
        return timeout(0, SECONDS);
    }

    public static InteractionListener retry() {
        return new RetryOnExceptionInteractionListener();
    }

    public static InteractionListener retryAfterWaitingWhileEmpty(CoreWebElements<?> elems, String preset) {
        return new RetryAfterWaitingWhileEmptyInteractionListener(elems, preset);
    }

    public static InteractionListener slowMotion(long time, TimeUnit units) {
        return new SlowMotionInteractionListener(new Duration(time, units));
    }

    public static void get(WebElementsDriver<?> wd, String url) {
        defaultPerformer().get($(wd), url);
    }

    public static void get(CoreWebElements<?> elements, String url) {
        defaultPerformer().get(elements, url);
    }

    public static void close(WebElementsDriver<?> wd) {
        defaultPerformer().close($(wd));
    }

    public static void close(CoreWebElements<?> elements) {
        defaultPerformer().close(elements);
    }

    public static void scrollIntoView(CoreWebElements<?> elements) {
        defaultPerformer().scrollIntoView(elements);
    }

    // from org.openqa.selenium.WebElement
    /**
     * Clear.
     *
     * @param elements the elements
     */
    public static void clear(CoreWebElements<?> elements) {
        defaultPerformer().clear(elements);
    }

    /**
     * Submit.
     *
     * @param elements the elements
     */
    public static void submit(CoreWebElements<?> elements) {
        defaultPerformer().submit(elements);
    }

    // from org.openqa.selenium.interactions.Actions
    /**
     * Key down.
     *
     * @param elements the elements
     * @param keys the keys
     */
    public static void keyDown(CoreWebElements<?> elements, Keys keys) {
        defaultPerformer().keyDown(elements, keys);
    }

    /**
     * Key up.
     *
     * @param elements the elements
     * @param keys the keys
     */
    public static void keyUp(CoreWebElements<?> elements, Keys keys) {
        defaultPerformer().keyUp(elements, keys);
    }

    /**
     * Send keys.
     *
     * @param elements the elements
     * @param keys the keys
     */
    public static void sendKeys(CoreWebElements<?> elements, CharSequence... keys) {
        defaultPerformer().sendKeys(elements, keys);
    }

    /**
     * Click and hold.
     *
     * @param elements the elements
     */
    public static void clickAndHold(CoreWebElements<?> elements) {
        defaultPerformer().clickAndHold(elements);
    }

    /**
     * Release.
     *
     * @param elements the elements
     */
    public static void release(CoreWebElements<?> elements) {
        defaultPerformer().release(elements);
    }

    /**
     * Click.
     *
     * @param elements the elements
     */
    public static void click(CoreWebElements<?> elements) {
        defaultPerformer().click(elements);
    }

    /**
     * Double click.
     *
     * @param elements the elements
     */
    public static void doubleClick(CoreWebElements<?> elements) {
        defaultPerformer().doubleClick(elements);
    }

    /**
     * Move to element.
     *
     * @param elements the elements
     */
    public static void moveToElement(CoreWebElements<?> elements) {
        defaultPerformer().moveToElement(elements);
    }

    /**
     * Move to element.
     *
     * @param elements the elements
     * @param xOffset the x offset
     * @param yOffset the y offset
     */
    public static void moveToElement(CoreWebElements<?> elements, int xOffset, int yOffset) {
        defaultPerformer().moveToElement(elements, xOffset, yOffset);
    }

    /**
     * Move by offset.
     *
     * @param elements the elements
     * @param xOffset the x offset
     * @param yOffset the y offset
     */
    public static void moveByOffset(CoreWebElements<?> elements, int xOffset, int yOffset) {
        defaultPerformer().moveByOffset(elements, xOffset, yOffset);
    }

    /**
     * Context click.
     *
     * @param elements the elements
     */
    public static void contextClick(CoreWebElements<?> elements) {
        defaultPerformer().contextClick(elements);
    }

    /**
     * Drag and drop.
     *
     * @param source the source
     * @param target the target
     */
    public static void dragAndDrop(CoreWebElements<?> source, CoreWebElements<?> target) {
        defaultPerformer().dragAndDrop(source, target);
    }

    /**
     * Drag and drop by.
     *
     * @param source the source
     * @param xOffset the x offset
     * @param yOffset the y offset
     */
    public static void dragAndDropBy(CoreWebElements<?> source, int xOffset, int yOffset) {
        defaultPerformer().dragAndDropBy(source, xOffset, yOffset);
    }

    // additional methods
    /**
     * Click all.
     *
     * @param elements the elements
     */
    public static void clickAll(CoreWebElements<?> elements) {
        defaultPerformer().clickAll(elements);
    }

    /**
     * Type.
     *
     * @param elements the elements
     * @param text the text
     */
    public static void type(CoreWebElements<?> elements, CharSequence text) {
        defaultPerformer().type(elements, text);
    }

    /**
     * Clear and type.
     *
     * @param elements the elements
     * @param text the text
     */
    public static void fill(CoreWebElements<?> elements, CharSequence text) {
        defaultPerformer().fill(elements, text);
    }

    public static void check(CoreWebElements<?> elements) {
        defaultPerformer().check(elements);
    }

    public static void uncheck(CoreWebElements<?> elements) {
        defaultPerformer().uncheck(elements);
    }

    // select
    /**
     * Select.
     *
     * @param elems the elems
     * @param text the text
     */
    public static void select(CoreWebElements<?> elems, String text) {
        defaultPerformer().select(elems, text);
    }

    /**
     * Deselect.
     *
     * @param elems the elems
     * @param text the text
     */
    public static void deselect(CoreWebElements<?> elems, String text) {
        defaultPerformer().deselect(elems, text);
    }

    /**
     * Select val.
     *
     * @param elems the elems
     * @param val the val
     */
    public static void selectVal(CoreWebElements<?> elems, String val) {
        defaultPerformer().selectVal(elems, val);
    }

    /**
     * Deselect val.
     *
     * @param elems the elems
     * @param val the val
     */
    public static void deselectVal(CoreWebElements<?> elems, String val) {
        defaultPerformer().deselectVal(elems, val);
    }

    /**
     * Select all.
     *
     * @param elems the elems
     */
    public static void selectAll(CoreWebElements<?> elems) {
        defaultPerformer().selectAll(elems);
    }

    /**
     * Deselect all.
     *
     * @param elems the elems
     */
    public static void deselectAll(CoreWebElements<?> elems) {
        defaultPerformer().deselectAll(elems);
    }

    public static void waitWhileEmpty(CoreWebElements<?> elems) throws TimeoutException {
        defaultPerformer().waitWhileEmpty(elems);
    }

    public static void waitWhileNotEmpty(CoreWebElements<?> elems) throws TimeoutException {
        defaultPerformer().waitWhileNotEmpty(elems);
    }

    /**
     * Check not empty.
     *
     * @param elems the elems
     * @return true, if successful
     */
    public static boolean checkNotEmpty(CoreWebElements<?> elems) {
        return defaultPerformer().checkNotEmpty(elems);
    }

    /**
     * Check empty.
     *
     * @param elems the elems
     * @return true, if successful
     */
    public static boolean checkEmpty(CoreWebElements<?> elems) {
        return defaultPerformer().checkEmpty(elems);
    }

    /**
     * Wait until closed.
     *
     * @param elems the elems
     */
    public static void waitUntilClosed(CoreWebElements<?> elems) throws TimeoutException {
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
     * With waiting preset.
     *
     * @param preset the waiting preset name
     * @return the interaction performer
     */
    public static InteractionPerformer withWaitingPreset(String preset) {
        return with(waitingPreset(preset));
    }

    /**
     * With.
     *
     * @param listeners the listeners
     * @return the interaction performer
     */
    public static InteractionPerformer with(InteractionListener... listeners) {
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

    /**
     * Perform.
     *
     * @param interaction the interaction
     */
    public static void performAndWait(AsyncInteraction interaction) {
        interaction.perform();
        interaction.waitUntilCompleted();
    }

    private static InteractionPerformer defaultPerformer() {
        return new InteractionPerformer();
    }
}
