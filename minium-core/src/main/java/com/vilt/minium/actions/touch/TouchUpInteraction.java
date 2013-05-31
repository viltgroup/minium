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

import com.vilt.minium.WebElements;

/**
 * The Class TouchUpInteraction.
 */
public class TouchUpInteraction extends TouchInteraction {

	/**
	 * Instantiates a new touch up interaction.
	 *
	 * @param elems the elems
	 * @param x the x
	 * @param y the y
	 */
	public TouchUpInteraction(WebElements elems, int x, int y) {
		super(elems);
	}

	@Override
	protected void doPerform() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
