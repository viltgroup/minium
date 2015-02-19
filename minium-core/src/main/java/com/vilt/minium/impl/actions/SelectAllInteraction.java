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
package com.vilt.minium.impl.actions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.vilt.minium.CoreWebElements;

/**
 * The Class SelectAllInteraction.
 */
public class SelectAllInteraction extends SelectionInteraction {

    /**
     * Instantiates a new select all interaction.
     *
     * @param source the source
     */
    public SelectAllInteraction(CoreWebElements<?> source) {
        super(source);
    }

    /* (non-Javadoc)
     * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
     */
    @Override
    protected void doPerform() {
        Select select = getSelectElement();
        if (!select.isMultiple()) {
            throw new UnsupportedOperationException("You may only deselect all options of a multi-select");
        }

        for (WebElement option : select.getOptions()) {
            if (!option.isSelected()) {
                option.click();
            }
        }
    }
}
