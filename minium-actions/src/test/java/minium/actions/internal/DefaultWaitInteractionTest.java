package minium.actions.internal;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import minium.BasicElements;
import minium.Elements;
import minium.actions.Configuration;
import minium.actions.HasConfiguration;
import minium.actions.TimeoutException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class DefaultWaitInteractionTest {

    @Mock
    private BasicElements<Elements> elems;

    @Before
    public void setup() {
        WaitMocks.mock();
        Configuration configuration = new DefaultConfiguration()
            .defaultTimeout(5, TimeUnit.SECONDS)
            .defaultInterval(1, TimeUnit.SECONDS);
        when(elems.is(HasConfiguration.class)).thenReturn(true);
        when(elems.as(HasConfiguration.class)).thenReturn(new HasConfiguration.Impl(configuration));
        when(elems.as(BasicElements.class)).thenReturn(elems);
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

    @Test
    public void testWaitForExistenceTimeout() {
        // when
        when(elems.size()).thenReturn(0);

        // then
        WaitForExistenceInteraction interaction = new WaitForExistenceInteraction(elems , null);
        try {
            interaction.perform();
            Assert.fail("TimeoutException expected");
        } catch (TimeoutException e) {
            verify(elems, times(5)).size();
        }
    }

    @Test
    public void testWaitForExistenceFailThenSuccess() {
        // when
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
}
