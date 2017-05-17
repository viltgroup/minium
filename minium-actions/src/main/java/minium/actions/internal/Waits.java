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

import static com.github.rholder.retry.StopStrategies.stopAfterDelay;
import static com.github.rholder.retry.WaitStrategies.fixedWait;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import minium.Elements;
import minium.ElementsException;
import minium.actions.Configuration;
import minium.actions.Configuration.WaitingPreset;
import minium.actions.Duration;
import minium.actions.HasConfiguration;
import minium.actions.TimeoutException;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import minium.internal.Throwables;
import com.google.common.util.concurrent.Callables;

public class Waits {

    public static class Wait {

        public void forPredicate(Elements elements, String preset, Predicate<? super Elements> predicate) {
            Configuration configuration = elements.as(HasConfiguration.class).configure();
            WaitingPreset waitingPreset = configuration.waitingPreset(preset);
            forPredicate(elements, waitingPreset.timeout(), waitingPreset.interval(), predicate);
        }

        public void forPredicate(Elements elements, Duration timeout, Duration interval, Predicate<? super Elements> predicate) {
            Configuration configuration = elements.as(HasConfiguration.class).configure();
            if (timeout == null) {
                timeout = configuration.defaultTimeout();
            }
            if (interval == null) {
                interval = configuration.defaultInterval();
            }

            Retryer<Elements> retrier = getRetryer(predicate, timeout, interval);

            try {
                retrier.call(Callables.returning(elements));
            } catch (RetryException e) {
                // if interrupted, we need to propagate it with the thread marked as interrupted
                if (Thread.interrupted()) {
                    Thread.currentThread().interrupt();
                    throw Throwables.propagate(e);
                }
                throw new TimeoutException(predicate, elements, e.getNumberOfFailedAttempts());
            } catch (ExecutionException e) {
                throw Throwables.propagate(e.getCause());
            }
        }

        public boolean forPredicateOrTimeout(Elements elements, String preset, Predicate<? super Elements> predicate) {
            Configuration configuration = elements.as(HasConfiguration.class).configure();
            WaitingPreset waitingPreset = configuration.waitingPreset(preset);
            return forPredicateOrTimeout(elements, waitingPreset.timeout(), waitingPreset.interval(), predicate);
        }

        public boolean forPredicateOrTimeout(Elements elements, Duration timeout, Duration interval, Predicate<? super Elements> predicate) {
            Configuration configuration =  elements.as(HasConfiguration.class).configure();
            if (timeout == null) {
                timeout = configuration.defaultTimeout();
            }
            if (interval == null) {
                interval = configuration.defaultInterval();
            }

            Retryer<Elements> retrier = getRetryer(predicate, timeout, interval);

            try {
                retrier.call(Callables.returning(elements));
                return true;
            } catch (RetryException e) {
                // if interrupted, we need to propagate it with the thread marked as interrupted
                if (Thread.interrupted()) {
                    Thread.currentThread().interrupt();
                    throw Throwables.propagate(e);
                }
                return false;
            } catch (ExecutionException e) {
                throw Throwables.propagate(e.getCause());
            }
        }

        public void time(Duration waitTime) {
            long time = waitTime.getTime();
            TimeUnit unit = waitTime.getUnit();

            try {
                Thread.sleep(unit.toMillis(time));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new ElementsException(e);
            }
        }

        @SuppressWarnings("unchecked")
        protected <T> Retryer<T> getRetryer(Predicate<? super T> predicate, Duration timeout, Duration interval) {
            return RetryerBuilder.<T> newBuilder()
                    .retryIfResult(Predicates.not((Predicate<T>) predicate))
                    .retryIfRuntimeException()
                    .withWaitStrategy(fixedWait(interval.getTime(), interval.getUnit()))
                    .withStopStrategy(stopAfterDelay(timeout.getTime(), timeout.getUnit()))
                    .build();
        }
    }

    private static Wait wait = new Wait();

    public static void setInstance(Wait wait) {
        Waits.wait = wait;
    }

    public static void waitForPredicate(Elements elements, String preset, Predicate<? super Elements> predicate) {
        wait.forPredicate(elements, preset, predicate);
    }

    public static void waitForPredicate(Elements elements, Duration timeout, Duration interval, Predicate<? super Elements> predicate) {
        wait.forPredicate(elements, timeout, interval, predicate);
    }

    public static boolean waitForPredicateOrTimeout(Elements elements, String preset, Predicate<? super Elements> predicate) {
        return wait.forPredicateOrTimeout(elements, preset, predicate);
    }

    public static boolean waitForPredicateOrTimeout(Elements elements, Duration timeout, Duration interval, Predicate<? super Elements> predicate) {
        return wait.forPredicateOrTimeout(elements, timeout, interval, predicate);
    }

    public static void waitTime(Duration duration) {
        wait.time(duration);
    }
}
