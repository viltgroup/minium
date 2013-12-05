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
package com.vilt.minium.impl;

import static com.vilt.minium.ExceptionHandlers.alwaysAcceptUnhandledAlerts;
import static com.vilt.minium.actions.Interactions.retry;
import static com.vilt.minium.actions.Interactions.slowMotion;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import com.vilt.minium.Configuration;
import com.vilt.minium.Duration;

public class ConfigurationTest {

    @Test
    public void testConfiguration() {
        Configuration configuration = new ConfigurationImpl();

        configuration
        .defaultInterval(1, TimeUnit.SECONDS)
        .defaultTimeout(2, TimeUnit.SECONDS)
        .waitingPreset("slow")
        .timeout(20, TimeUnit.SECONDS);

        assertThat(configuration.waitingPreset("slow").timeout(), equalTo(new Duration(20, TimeUnit.SECONDS)));
        assertThat(configuration.waitingPreset("slow").interval(), equalTo(new Duration(1, TimeUnit.SECONDS)));

        configuration
        .defaultInterval(100, TimeUnit.MILLISECONDS);

        assertThat(configuration.waitingPreset("slow").interval(), equalTo(new Duration(100, TimeUnit.MILLISECONDS)));
    }

    @Test
    public void testConfigurationFluentApi() {
        Configuration configuration = new ConfigurationImpl();

        configuration
        .defaultTimeout(2, TimeUnit.SECONDS)
        .defaultInterval(1, TimeUnit.SECONDS)
        .waitingPreset("slow")
        .timeout(20, TimeUnit.SECONDS)
        .interval(5, TimeUnit.SECONDS)
        .done()
        .waitingPreset("fast")
        .timeout(1, TimeUnit.SECONDS)
        .interval(200, TimeUnit.MILLISECONDS)
        .done()
        .interactionListeners()
        .add(slowMotion(2, TimeUnit.SECONDS))
        .add(retry())
        .done()
        .exceptionHandlers()
        .add(alwaysAcceptUnhandledAlerts())
        .done();
    }
}
