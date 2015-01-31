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
package minium.web.internal.actions;

import java.util.concurrent.TimeUnit;

import minium.Elements;
import minium.ElementsException;
import minium.Offsets.Offset;
import minium.actions.Duration;
import minium.actions.InteractionListener;
import minium.actions.InteractionPerformer;
import minium.actions.Keys;
import minium.actions.internal.AbstractInteractionPerformer;

/**
 * The Class InteractionPerformer.
 */
public class WebInteractionPerformer extends AbstractInteractionPerformer implements InteractionPerformer {

    /**
     * Instantiates a new interaction performer.
     *
     * @param listeners the listeners
     */
    public WebInteractionPerformer(InteractionListener... listeners) {
        with(listeners);
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#get(minium.Elements, java.lang.String)
     */
    @Override
    public void get(Elements elements, String url) {
        perform(new GetInteraction(elements, url));
    }

    // from org.openqa.selenium.WebElement
    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#clear(minium.Elements)
     */
    @Override
    public void clear(Elements elements) {
        perform(new ClearInteraction(elements));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#submit(minium.Elements)
     */
    @Override
    public void submit(Elements elements) {
        perform(new SubmitInteraction(elements));
    }

    // from org.openqa.selenium.interactions.Actions
    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#keyDown(minium.Elements, org.openqa.selenium.Keys)
     */
    @Override
    public void keyDown(Elements elements, Keys keys) {
        perform(new KeyDownInteraction(elements, keys));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#keyUp(minium.Elements, org.openqa.selenium.Keys)
     */
    @Override
    public void keyUp(Elements elements, Keys keys) {
        perform(new KeyUpInteraction(elements, keys));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#sendKeys(minium.Elements, java.lang.CharSequence)
     */
    @Override
    public void sendKeys(Elements elements, CharSequence... keys) {
        perform(new SendKeysInteraction(elements, keys));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#clickAndHold(minium.Elements)
     */
    @Override
    public void clickAndHold(Elements elements) {
        clickAndHold(elements, null);
    }

    @Override
    public void clickAndHold(Elements elements, Offset offset) {
        perform(new ClickAndHoldInteraction(elements, offset));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#release(minium.Elements)
     */
    @Override
    public void release(Elements elements) {
        release(elements, null);
    }

    @Override
    public void release(Elements elements, Offset offset) {
        perform(new ButtonReleaseInteraction(elements, offset));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#click(minium.Elements)
     */
    @Override
    public void click(Elements elements) {
        click(elements, null);
    }

    @Override
    public void click(Elements elements, Offset offset) {
        perform(new ClickInteraction(elements, offset));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#doubleClick(minium.Elements)
     */
    @Override
    public void doubleClick(Elements elements) {
        doubleClick(elements, null);
    }

    @Override
    public void doubleClick(Elements elements, Offset offset) {
        perform(new DoubleClickInteraction(elements, offset));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#moveToElement(minium.Elements)
     */
    @Override
    public void moveTo(Elements elements) {
        moveTo(elements, null);
    }

    @Override
    public void moveTo(Elements elements, Offset offset) {
        perform(new MoveMouseInteraction(elements, offset));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#contextClick(minium.Elements)
     */
    @Override
    public void contextClick(Elements elements) {
        contextClick(elements, null);
    }

    @Override
    public void contextClick(Elements elements, Offset offset) {
        perform(new ContextClickInteraction(elements, offset));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#dragAndDrop(minium.Elements, minium.Elements)
     */
    @Override
    public void dragAndDrop(Elements source, Elements target) {
        perform(new DragAndDropInteraction(source, null, target, null));
    }

    // additional methods
    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#clickAll(minium.Elements)
     */
    @Override
    public void clickAll(Elements elements) {
        clickAll(elements, null);
    }

    @Override
    public void clickAll(Elements elements, Offset offset) {
        perform(new ClickAllInteraction(elements, offset));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#type(minium.Elements, java.lang.CharSequence)
     */
    @Override
    public void type(Elements elements, CharSequence text) {
        perform(new TypeInteraction(elements, text));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#fill(minium.Elements, java.lang.CharSequence)
     */
    @Override
    public void fill(Elements elements, CharSequence text) {
        perform(new FillInteraction(elements, text));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#check(minium.Elements)
     */
    @Override
    public void check(Elements elements) {
        perform(new CheckInteraction(elements));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#uncheck(minium.Elements)
     */
    @Override
    public void uncheck(Elements elements) {
        perform(new UncheckInteraction(elements));
    }

    // select
    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#select(minium.Elements, java.lang.String)
     */
    @Override
    public void select(Elements elems, String text) {
        perform(new SelectInteraction(elems, text));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#deselect(minium.Elements, java.lang.String)
     */
    @Override
    public void deselect(Elements elems, String text) {
        perform(new DeselectInteraction(elems, text));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#selectVal(minium.Elements, java.lang.String)
     */
    @Override
    public void selectVal(Elements elems, String val) {
        perform(new SelectValInteraction(elems, val));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#deselectVal(minium.Elements, java.lang.String)
     */
    @Override
    public void deselectVal(Elements elems, String val) {
        perform(new DeselectValInteraction(elems, val));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#selectAll(minium.Elements)
     */
    @Override
    public void selectAll(Elements elems) {
        perform(new SelectAllInteraction(elems));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#deselectAll(minium.Elements)
     */
    @Override
    public void deselectAll(Elements elems) {
        perform(new DeselectAllInteraction(elems));
    }

    @Override
    public void waitForExistence(Elements elems) throws ElementsException {
        waitForExistence(elems, null);
    }

    @Override
    public void waitForExistence(Elements elems, String waitingPreset) {
        perform(new WaitForExistenceInteraction(elems, waitingPreset));
    }

    @Override
    public void waitForUnexistence(Elements elems) throws ElementsException {
        waitForUnexistence(elems, null);
    }

    @Override
    public void waitForUnexistence(Elements elems, String waitingPreset) throws ElementsException {
        perform(new WaitForUnexistenceInteraction(elems, waitingPreset));
    }

    @Override
    public boolean checkForExistence(Elements elems) {
        return checkForExistence(elems, null);
    }

    @Override
    public boolean checkForExistence(Elements elems, String waitingPreset) {
        CheckForExistenceInteraction interaction = new CheckForExistenceInteraction(elems, waitingPreset);
        perform(interaction);
        return !interaction.isEmpty();
    }

    @Override
    public boolean checkForUnexistence(Elements elems) {
        return checkForUnexistence(elems, null);
    }

    @Override
    public boolean checkForUnexistence(Elements elems, String waitingPreset) {
        CheckForUnexistenceInteraction interaction = new CheckForUnexistenceInteraction(elems, waitingPreset);
        perform(interaction);
        return interaction.isEmpty();
    }

    /**
     * Wait until closed.
     *
     * @param elems the elems
     */
    @Override
    public void waitUntilClosed(Elements elems) throws ElementsException {
        perform(new WaitWindowClosedElementsInteraction(elems, null));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#waitTime(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public void waitTime(long time, TimeUnit unit) {
        perform(new WaitTimeInteraction(new Duration(time, unit)));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#close(minium.Elements)
     */
    @Override
    public void close(Elements elements) {
        perform(new CloseInteraction(elements));
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#scrollIntoView(minium.Elements)
     */
    @Override
    public void scrollIntoView(Elements elements) {
        perform(new ScrollIntoViewInteraction(elements));
    }
}
