/*
 * Copyright (C) 2015 The Minium Authors
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
package minium.actions.internal;

import java.util.concurrent.TimeUnit;

import minium.ElementsException;
import minium.actions.Duration;
import minium.actions.Interactable;
import minium.actions.WaitInteractable;

public class DefaultWaitInteractable<T extends Interactable<?>> extends AbstractInteractable<T> implements WaitInteractable<T> {

    public DefaultWaitInteractable() {
    }

    @Override
    public T waitForExistence() throws ElementsException {
        return waitForExistence(null);
    }

    @Override
    public T waitForExistence(String waitingPreset) {
        return perform(new WaitForExistenceInteraction(getSource(), waitingPreset));
    }

    @Override
    public T waitForUnexistence() throws ElementsException {
        return waitForUnexistence(null);
    }

    @Override
    public T waitForUnexistence(String waitingPreset) throws ElementsException {
        return perform(new WaitForUnexistenceInteraction(getSource(), waitingPreset));
    }

    @Override
    public boolean checkForExistence() {
        return checkForExistence(null);
    }

    @Override
    public boolean checkForExistence(String waitingPreset) {
        CheckForExistenceInteraction interaction = new CheckForExistenceInteraction(getSource(), waitingPreset);
        perform(interaction);
        return !interaction.isEmpty();
    }

    @Override
    public boolean checkForUnexistence() {
        return checkForUnexistence(null);
    }

    @Override
    public boolean checkForUnexistence(String waitingPreset) {
        CheckForUnexistenceInteraction interaction = new CheckForUnexistenceInteraction(getSource(), waitingPreset);
        perform(interaction);
        return interaction.isEmpty();
    }

    /* (non-Javadoc)
     * @see minium.actions.InteractionPerformer#waitTime(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public T waitTime(long time, TimeUnit unit) {
        return perform(new WaitTimeInteraction(new Duration(time, unit)));
    }
}
