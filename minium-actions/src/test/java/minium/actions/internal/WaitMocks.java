/*
 * Copyright (C) 2015 The Minium Authors
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
package minium.actions.internal;

import minium.actions.Duration;
import minium.actions.internal.Waits.Wait;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class WaitMocks {

    public static void mock() {
        Waits.setInstance(new Wait() {
            @Override
            public void time(Duration waitTime) {
                // don't wait
            }

            @Override
            @SuppressWarnings("unchecked")
            protected <T> Retryer<T> getRetryer(Predicate<? super T> predicate, Duration timeout, Duration interval) {
                int numRetries = (int) (timeout.getTime() / interval.getTime());
                // we emulate the number of retries
                return RetryerBuilder.<T> newBuilder()
                        .retryIfResult(Predicates.not((Predicate<T>) predicate))
                        .retryIfRuntimeException()
                        .withWaitStrategy(WaitStrategies.noWait())
                        .withStopStrategy(StopStrategies.stopAfterAttempt(numRetries))
                        .build();
            }
        });
    }
}
