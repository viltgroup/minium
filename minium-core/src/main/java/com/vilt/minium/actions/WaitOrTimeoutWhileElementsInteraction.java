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

import static com.vilt.minium.Minium.untilEmpty;

import com.vilt.minium.WebElements;

/**
 * The Class WaitOrTimeoutWhileElementsInteraction.
 */
public class WaitOrTimeoutWhileElementsInteraction extends WaitInteraction {

	/**
	 * Instantiates a new wait or timeout while elements interaction.
	 *
	 * @param elems the elems
	 */
	public WaitOrTimeoutWhileElementsInteraction(WebElements elems) {
		super(elems);
	}
	
	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getSource().waitOrTimeout(getTimeout(), untilEmpty());
	}
}
