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
            int count/*= 0*/;
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
