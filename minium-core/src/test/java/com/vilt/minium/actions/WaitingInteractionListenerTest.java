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

import static com.vilt.minium.Minium.$;
import static com.vilt.minium.actions.Interactions.checkEmpty;
import static com.vilt.minium.actions.Interactions.checkNotEmpty;
import static com.vilt.minium.actions.Interactions.retryOnTimeout;
import static com.vilt.minium.actions.Interactions.waitWhileEmpty;
import static com.vilt.minium.actions.Interactions.withWaitingPreset;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.Assert.fail;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vilt.minium.DefaultWebElements;
import com.vilt.minium.MiniumBaseTest;
import com.vilt.minium.TimeoutException;

public class WaitingInteractionListenerTest extends MiniumBaseTest {

    private static final double DELTA = 1.0;

    @BeforeMethod
    public void openPage() {
        get("minium/tests/jquery-test.html");

        // given
        wd.configure()
            .defaultTimeout(1, SECONDS)
            .waitingPreset("fast").timeout(200, MILLISECONDS).interval(100, MILLISECONDS).done()
            .waitingPreset("slow").timeout(2, SECONDS).interval(100, MILLISECONDS).done()
            .interactionListeners().clear().done();
        // just to force minium to load all the stuff before
        waitWhileEmpty($(wd, "input"));
    }

    @Test
    public void testPreset() {
        long start = System.currentTimeMillis();
        try {
            // when
            withWaitingPreset("fast").waitWhileEmpty($(wd, "#no-element"));
            fail();
        } catch (TimeoutException e) {
            // then
            double elapsed = (System.currentTimeMillis() - start) / 1000.0;
            assertThat(elapsed, allOf(greaterThan(0.2), lessThan(0.2 + DELTA)));
        }
    }

    @Test
    public void testCheckNotEmpty() {
        long start = System.currentTimeMillis();

        // when
        boolean notEmpty = checkNotEmpty($(wd, "#no-element"));
        double elapsed = (System.currentTimeMillis() - start) / 1000.0;

        // then
        assertThat(notEmpty, is(false));
        assertThat(elapsed, allOf(greaterThan(1.0), lessThan(1.0 + DELTA)));
    }

    @Test
    public void testCheckEmpty() {
        long start = System.currentTimeMillis();

        // when
        boolean empty = checkEmpty($(wd, "input"));
        double elapsed = (System.currentTimeMillis() - start) / 1000.0;

        // then
        assertThat(empty, is(false));
        assertThat(elapsed, allOf(greaterThan(1.0), lessThan(1.0 + DELTA)));
    }

    @Test(timeOut = 4000)
    public void testCheckNotEmptyWithRetry() {
        DefaultWebElements base = $(wd, "#no-element");

        wd.configure().interactionListeners().add(retryOnTimeout().withWaitingPreset("slow").whenEmpty(base));

        long start = System.currentTimeMillis();

        // when
        boolean notEmpty = checkNotEmpty(base.find("input"));
        double elapsed = (System.currentTimeMillis() - start) / 1000.0;

        // then
        assertThat(notEmpty, is(false));
        assertThat(elapsed, allOf(greaterThan(3.0), lessThan(3.0 + DELTA)));
    }
}
