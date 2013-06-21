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
package com.vilt.minium.impl.tips;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.TimeUnit;

import com.vilt.minium.Duration;
import com.vilt.minium.WebElements;
import com.vilt.minium.impl.actions.AsyncTimeElapsedInteraction;
import com.vilt.minium.tips.TipWebElements;

public class TipInteraction extends AsyncTimeElapsedInteraction {

	private String tip;

	public TipInteraction(WebElements elems, String tip, Duration duration) {
		super(elems, duration);
		this.tip = tip;
	}

	public TipInteraction(WebElements elems, String tip) {
		super(elems, new Duration(5, SECONDS));
		this.tip = tip;
	}

	public String getTip() {
		return tip;
	}
	
	@Override
	protected void doPerform() {
		super.doPerform();
		TipWebElements tipsElems = (TipWebElements) getSource();
		tipsElems.showTip(tip, duration.getTime(), duration.getUnit());
	}
}
