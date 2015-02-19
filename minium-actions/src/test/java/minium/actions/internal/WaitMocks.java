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
