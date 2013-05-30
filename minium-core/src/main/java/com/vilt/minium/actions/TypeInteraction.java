/*
 * Copyright (C) 2013 VILT Group
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

import com.vilt.minium.WebElements;

/**
 * The Class TypeInteraction.
 */
public class TypeInteraction extends KeyboardInteraction {

	private CharSequence text;

	/**
	 * Instantiates a new type interaction.
	 *
	 * @param source the source
	 * @param text the text
	 */
	TypeInteraction(WebElements source, CharSequence text) {
		super(source);
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		getFirstElement().sendKeys(text);
	}
}
