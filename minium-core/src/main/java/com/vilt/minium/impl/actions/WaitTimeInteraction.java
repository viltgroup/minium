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

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.Sleeper;

import com.vilt.minium.Duration;
import com.vilt.minium.MiniumException;

/**
 * The Class WaitTimeInteraction.
 */
public class WaitTimeInteraction extends WaitInteraction {

	private Duration waitTime;

	/**
	 * Instantiates a new wait time interaction.
	 *
	 * @param waitTime the wait time
	 */
	public WaitTimeInteraction(Duration waitTime) {
		super(null);
		
		checkNotNull(waitTime);
		this.waitTime = waitTime;
	}

	/* (non-Javadoc)
	 * @see com.vilt.minium.actions.DefaultInteraction#doPerform()
	 */
	@Override
	protected void doPerform() {
		long time = waitTime.getTime();
		TimeUnit unit = waitTime.getUnit();

		org.openqa.selenium.support.ui.Duration duration = new org.openqa.selenium.support.ui.Duration(time, unit);
		try {
			Sleeper.SYSTEM_SLEEPER.sleep(duration);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new MiniumException(e);
		}
	}
}
