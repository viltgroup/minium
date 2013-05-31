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
package com.vilt.minium.debug;

import java.io.OutputStream;

import com.vilt.minium.WebElements;
import com.vilt.minium.actions.DefaultInteraction;

/**
 * The Class ScreenshotInteraction.
 */
public abstract class ScreenshotInteraction extends DefaultInteraction {

	/** The stream. */
	protected OutputStream stream;

	/**
	 * Instantiates a new screenshot interaction.
	 *
	 * @param elems the elems
	 * @param stream the stream
	 */
	public ScreenshotInteraction(WebElements elems, OutputStream stream) {
		super(elems);
		this.stream = stream;
	}

}
