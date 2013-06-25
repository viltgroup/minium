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
package com.vilt.minium.actions.touch;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.internal.WrapsDriver;

import com.vilt.minium.CoreWebElements;
import com.vilt.minium.impl.actions.DefaultInteraction;

/**
 * The Class TouchInteraction.
 */
public abstract class TouchInteraction extends DefaultInteraction {

	/**
	 * Instantiates a new touch interaction.
	 *
	 * @param elems the elems
	 */
	public TouchInteraction(CoreWebElements<?> elems) {
		super(elems);
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#getActions()
	 */
	@Override
	protected TouchActions getActions() {
		return newActions(getFirstElement(getSource()));
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#newActions(org.openqa.selenium.WebElement)
	 */
	@Override
	protected TouchActions newActions(WebElement elem) {
		return new TouchActions(((WrapsDriver) elem).getWrappedDriver());
	}
}
