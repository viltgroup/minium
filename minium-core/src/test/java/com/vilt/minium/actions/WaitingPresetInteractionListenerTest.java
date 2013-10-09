package com.vilt.minium.actions;

import static com.vilt.minium.Minium.$;
import static com.vilt.minium.actions.Interactions.withWaitingPreset;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.Assert.fail;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vilt.minium.MiniumBaseTest;
import com.vilt.minium.TimeoutException;

public class WaitingPresetInteractionListenerTest extends MiniumBaseTest {

    private static final double DELTA = 1.0;

    @BeforeMethod
    public void openPage() {
        get("minium/tests/jquery-test.html");
    }
    
    @Test()
    public void testPreset() {
        // given
        wd.configure().waitingPreset("fast").timeout(1, SECONDS).interval(500, MILLISECONDS);
        // just to force minium to load all the stuff before
        $(wd, "input").size();
        
        long start = System.currentTimeMillis();
        try {
            // when
            withWaitingPreset("fast").waitWhileEmpty($(wd, "#no-element"));
            fail();
        } catch (TimeoutException e) {
            // then
            double elapsed = (System.currentTimeMillis() - start) / 1000.0;
            assertThat(elapsed, allOf(greaterThan(1.0), lessThan(1.0 + DELTA)));
        }
    }
}
