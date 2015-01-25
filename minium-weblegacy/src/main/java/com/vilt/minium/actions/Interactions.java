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

import minium.Elements;
import minium.ElementsException;
import minium.Offsets.Offset;
import minium.actions.AsyncInteraction;
import minium.actions.Duration;
import minium.actions.Interaction;
import minium.actions.InteractionListener;
import minium.actions.InteractionPerformer;
import minium.actions.Keys;
import minium.actions.MouseInteractionPerformer;
import minium.web.internal.actions.RetryAfterWaitingWhileEmptyInteractionListener;
import minium.web.internal.actions.RetryOnExceptionInteractionListener;
import minium.web.internal.actions.SlowMotionInteractionListener;
import minium.web.internal.actions.TimeoutInteractionListener;
import minium.web.internal.actions.WaitingPresetInteractionListener;
import minium.web.internal.actions.WebInteractionPerformer;

import com.vilt.minium.DefaultWebElementsDriver;

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

    public static InteractionListener retryAfterWaitingWhileEmpty(Elements elems, String preset) {
        return new RetryAfterWaitingWhileEmptyInteractionListener(elems, preset);
    }

    public static InteractionListener slowMotion(long time, TimeUnit units) {
        return new SlowMotionInteractionListener(new Duration(time, units));
    }

    public static void get(Elements elements, String url) {
        defaultPerformer().get(elements, url);
    }

    public static void get(DefaultWebElementsDriver wd, String url) {
        defaultPerformer().get(wd.find(":root"), url);
    }

    public static void close(Elements elements) {
        defaultPerformer().close(elements);
    }

    public static void scrollIntoView(Elements elements) {
        defaultPerformer().scrollIntoView(elements);
    }

    // from org.openqa.selenium.WebElement
    /**
     * Clear.
     *
     * @param elements the elements
     */
    public static void clear(Elements elements) {
        defaultPerformer().clear(elements);
    }

    /**
     * Submit.
     *
     * @param elements the elements
     */
    public static void submit(Elements elements) {
        defaultPerformer().submit(elements);
    }

    // from org.openqa.selenium.interactions.Actions
    /**
     * Key down.
     *
     * @param elements the elements
     * @param keys the keys
     */
    public static void keyDown(Elements elements, Keys keys) {
        defaultPerformer().keyDown(elements, keys);
    }

    /**
     * Key up.
     *
     * @param elements the elements
     * @param keys the keys
     */
    public static void keyUp(Elements elements, Keys keys) {
        defaultPerformer().keyUp(elements, keys);
    }

    /**
     * Send keys.
     *
     * @param elements the elements
     * @param keys the keys
     */
    public static void sendKeys(Elements elements, CharSequence... keys) {
        defaultPerformer().sendKeys(elements, keys);
    }

    /**
     * Click and hold.
     *
     * @param elements the elements
     */
    public static void clickAndHold(Elements elements) {
        defaultPerformer().clickAndHold(elements);
    }

    public static void clickAndHold(Elements elements, Offset offset) {
        defaultPerformer().clickAndHold(elements, offset);
    }

    /**
     * Release.
     *
     * @param elements the elements
     */
    public static void release(Elements elements) {
        defaultPerformer().release(elements);
    }

    public static void release(Elements elements, Offset offset) {
        defaultPerformer().release(elements, offset);
    }

    /**
     * Click.
     *
     * @param elements the elements
     */
    public static void click(Elements elements) {
        defaultPerformer().click(elements);
    }

    /**
     * Double click.
     *
     * @param elements the elements
     */
    public static void doubleClick(Elements elements) {
        defaultPerformer().doubleClick(elements);
    }

    /**
     * Move to element.
     *
     * @param elements the elements
     */
    public static void moveTo(Elements elements) {
        defaultPerformer().moveTo(elements);
    }

    public static void moveTo(Elements elements, Offset offset) {
        defaultPerformer().moveTo(elements, offset);
    }

    /**
     * Context click.
     *
     * @param elements the elements
     */
    public static void contextClick(Elements elements) {
        defaultPerformer().contextClick(elements);
    }

    /**
     * Drag and drop.
     *
     * @param source the source
     * @param target the target
     */
    public static void dragAndDrop(Elements source, Elements target) {
        defaultPerformer().dragAndDrop(source, target);
    }

    // additional methods
    /**
     * Click all.
     *
     * @param elements the elements
     */
    public static void clickAll(Elements elements) {
        defaultPerformer().clickAll(elements);
    }

    /**
     * Type.
     *
     * @param elements the elements
     * @param text the text
     */
    public static void type(Elements elements, CharSequence text) {
        defaultPerformer().type(elements, text);
    }

    /**
     * Clear and type.
     *
     * @param elements the elements
     * @param text the text
     */
    public static void fill(Elements elements, CharSequence text) {
        defaultPerformer().fill(elements, text);
    }

    public static void check(Elements elements) {
        defaultPerformer().check(elements);
    }

    public static void uncheck(Elements elements) {
        defaultPerformer().uncheck(elements);
    }

    // select
    /**
     * Select.
     *
     * @param elems the elems
     * @param text the text
     */
    public static void select(Elements elems, String text) {
        defaultPerformer().select(elems, text);
    }

    /**
     * Deselect.
     *
     * @param elems the elems
     * @param text the text
     */
    public static void deselect(Elements elems, String text) {
        defaultPerformer().deselect(elems, text);
    }

    /**
     * Select val.
     *
     * @param elems the elems
     * @param val the val
     */
    public static void selectVal(Elements elems, String val) {
        defaultPerformer().selectVal(elems, val);
    }

    /**
     * Deselect val.
     *
     * @param elems the elems
     * @param val the val
     */
    public static void deselectVal(Elements elems, String val) {
        defaultPerformer().deselectVal(elems, val);
    }

    /**
     * Select all.
     *
     * @param elems the elems
     */
    public static void selectAll(Elements elems) {
        defaultPerformer().selectAll(elems);
    }

    /**
     * Deselect all.
     *
     * @param elems the elems
     */
    public static void deselectAll(Elements elems) {
        defaultPerformer().deselectAll(elems);
    }

    public static void waitWhileEmpty(Elements elems) throws ElementsException {
        defaultPerformer().waitWhileEmpty(elems);
    }

    public static void waitWhileNotEmpty(Elements elems) throws ElementsException {
        defaultPerformer().waitWhileNotEmpty(elems);
    }

    /**
     * Check not empty.
     *
     * @param elems the elems
     * @return true, if successful
     */
    public static boolean checkNotEmpty(Elements elems) {
        return defaultPerformer().checkNotEmpty(elems);
    }

    /**
     * Check empty.
     *
     * @param elems the elems
     * @return true, if successful
     */
    public static boolean checkEmpty(Elements elems) {
        return defaultPerformer().checkEmpty(elems);
    }

    /**
     * Wait until closed.
     *
     * @param elems the elems
     */
    public static void waitUntilClosed(Elements elems) throws ElementsException {
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
    public static MouseInteractionPerformer withoutWaiting() {
        return with(instantTimeout());
    }

    /**
     * With timeout.
     *
     * @param time the time
     * @param units the units
     * @return the interaction performer
     */
    public static MouseInteractionPerformer withTimeout(long time, TimeUnit units) {
        return with(timeout(time, units));
    }

    /**
     * With waiting preset.
     *
     * @param preset the waiting preset name
     * @return the interaction performer
     */
    public static MouseInteractionPerformer withWaitingPreset(String preset) {
        return with(waitingPreset(preset));
    }

    /**
     * With.
     *
     * @param listeners the listeners
     * @return the interaction performer
     */
    public static MouseInteractionPerformer with(InteractionListener... listeners) {
        return new WebInteractionPerformer(listeners);
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
        return new WebInteractionPerformer();
    }
}
