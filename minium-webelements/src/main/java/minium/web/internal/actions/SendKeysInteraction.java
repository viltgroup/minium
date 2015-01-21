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

import minium.Elements;

/**
 * The Class SendKeysInteraction.
 */
public class SendKeysInteraction extends KeyboardInteraction {

    private CharSequence[] keys;

    /**
     * Instantiates a new send keys interaction.
     *
     * @param source the source
     * @param keys the keys
     */
    public SendKeysInteraction(Elements source, CharSequence... keys) {
        super(source);
        this.keys = keys;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
     */
    @Override
    protected void doPerform() {
        if (isSourceDocumentRoot()) {
            keyboard().sendKeys(keys);
        } else {
            getFirstElement().sendKeys(keys);
        }
    }
}
