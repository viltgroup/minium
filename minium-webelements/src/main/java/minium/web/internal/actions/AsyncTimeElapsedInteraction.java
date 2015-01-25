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
package minium.web.internal.actions;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import minium.Elements;
import minium.actions.AsyncInteraction;
import minium.actions.Duration;

import org.openqa.selenium.support.ui.Sleeper;

public abstract class AsyncTimeElapsedInteraction extends AbstractWebInteraction implements AsyncInteraction {

    protected Duration duration;
    private long start = -1;

    public AsyncTimeElapsedInteraction(Elements elems, Duration duration) {
        super(elems);
        this.duration = duration;
    }

    @Override
    protected void doPerform() {
        start = System.currentTimeMillis();
    }

    @Override
    public boolean isComplete() {
        return start < 0;
    }

    @Override
    public void waitUntilCompleted() {
        try {
            long end = MILLISECONDS.convert(duration.getTime(), duration.getUnit());
            long time = (start + end) - System.currentTimeMillis();

            if (time > 0) {
                Sleeper.SYSTEM_SLEEPER.sleep(new org.openqa.selenium.support.ui.Duration(time, MILLISECONDS));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } finally {
            start = -1;
        }
    }

}
