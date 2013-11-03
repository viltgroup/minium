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
package com.vilt.minium;

import static com.vilt.minium.Minium.$;
import static com.vilt.minium.impl.WaitPredicates.whileEmpty;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.Assert.fail;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Iterables;

public class WaitWebElementsTest extends MiniumBaseTest {

    private static final double DELTA = 1.0;

    @BeforeMethod
    public void openPage() {
        get("minium/tests/jquery-test.html");
        wd.configure().defaultInterval(200, MILLISECONDS);
    }

    @Test(expectedExceptions = TimeoutException.class)
    public void testUnexistingElement() {
        $(wd, "#no-element").wait(whileEmpty());
    }

    @Test
    public void testWaitWithTimeoutElement() {

        // just to force minium to load all the stuff before
        $(wd, "input").size();

        long start = System.currentTimeMillis();
        try {
            // when
            $(wd, "#no-element").wait(1, SECONDS, whileEmpty());
            fail();
        } catch (TimeoutException e) {
            // then
            double elapsed = (System.currentTimeMillis() - start) / 1000.0;
            assertThat(elapsed, allOf(greaterThan(1.0), lessThan(1.0 + DELTA)));
        }
    }

    @Test
    public void testExistingElement() {
        DefaultWebElements wait = $(wd, "input").wait(whileEmpty());
        Assert.assertTrue(Iterables.size(wait) > 0);
    }

    // @Test()
    public void testPreset() {
        // given
        wd.configure().waitingPreset("fast").timeout(1, SECONDS).interval(100, MILLISECONDS);

        // just to force minium to load all the stuff before
        $(wd, "input").size();

        long start = System.currentTimeMillis();
        try {
            // when
            $(wd, "#no-element").wait("fast", whileEmpty());
            fail();
        } catch (TimeoutException e) {
            // then
            double elapsed = (System.currentTimeMillis() - start) / 1000.0;
            assertThat(elapsed, allOf(greaterThan(1.0), lessThan(1.0 + DELTA)));
        }
    }
}
