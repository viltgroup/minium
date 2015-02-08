package minium.actions.internal;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import minium.BasicElements;
import minium.Elements;
import minium.actions.Configuration;
import minium.actions.Duration;
import minium.actions.HasConfiguration;
import minium.actions.TimeoutException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Waits.class)
public class DefaultWaitInteractionTest {

    @Mock
    private BasicElements<Elements> elems;

    @Before
    public void setup() {
        Configuration configuration = new DefaultConfiguration();
        when(elems.is(HasConfiguration.class)).thenReturn(true);
        when(elems.as(HasConfiguration.class)).thenReturn(new HasConfiguration.Impl(configuration));
        when(elems.as(BasicElements.class)).thenReturn(elems);
        when(elems.size()).thenReturn(0);
    }

    @Test
    public void testWaitForExistence() {
        WaitForExistenceInteraction interaction = new WaitForExistenceInteraction(elems , null);
        when(elems.size()).thenReturn(5);

        // when
        interaction.perform();

        // then
        verify(elems, times(1)).size();
    }

    @SuppressWarnings("unchecked")
    @Test(expected = TimeoutException.class)
    public void testWaitForExistenceTimeout() {
        PowerMockito.mockStatic(Waits.class);
        // when
        when(Waits.getRetryer(Mockito.any(Predicate.class),  Mockito.any(Duration.class), Mockito.any(Duration.class)))
            .thenReturn(createMockedRetrier(WaitPredicates.forExistence()));
        when(elems.size()).thenReturn(0);

        // then
        WaitForExistenceInteraction interaction = new WaitForExistenceInteraction(elems , null);
        interaction.perform();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testWaitForExistenceFailThenSuccess() {
        PowerMockito.mockStatic(Waits.class);

        // when
        when(Waits.getRetryer(WaitPredicates.forExistence()),  Mockito.any(Duration.class), Mockito.any(Duration.class)))
            .andReturn(createMockedRetrier(WaitPredicates.forExistence()));
        when(elems.size()).thenAnswer(new Answer<Integer>() {
            int count = 0;
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                return count++;
            }
        });

        // when
        WaitForExistenceInteraction interaction = new WaitForExistenceInteraction(elems, null);
        interaction.perform();

        // then
        verify(elems, times(2)).size();
    }

    @SuppressWarnings("unchecked")
    protected <T> Retryer<T> createMockedRetrier(Predicate<? super T> predicate) {
        return RetryerBuilder.<T> newBuilder()
                .retryIfResult(Predicates.<T>not((Predicate<T>) predicate))
                .retryIfRuntimeException()
                .withWaitStrategy(WaitStrategies.noWait())
                .withStopStrategy(StopStrategies.stopAfterAttempt(2))
                .build();
    }
}
