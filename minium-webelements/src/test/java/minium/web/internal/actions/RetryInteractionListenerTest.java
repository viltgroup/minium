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
package minium.web.internal.actions;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import minium.AsIs;
import minium.BasicElements;
import minium.actions.Configuration;
import minium.actions.HasConfiguration;
import minium.actions.Interaction;
import minium.actions.InteractionListener;
import minium.actions.TimeoutException;
import minium.actions.internal.AfterFailInteractionEvent;
import minium.actions.internal.DefaultConfiguration;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.actions.WebInteractionListeners;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import platypus.AbstractMixinInitializer;
import platypus.Mixin;
import platypus.MixinClass;
import platypus.MixinClasses;

import com.google.common.reflect.AbstractInvocationHandler;

public class RetryInteractionListenerTest {

    class AsIsImpl extends Mixin.Impl implements AsIs { }

    @BeforeClass
    public static void setup() {
//        WaitMocks.mock();
    }

    @Test
    public void testOnTimeout() {
        final BasicElements<?> basicElements = mock(BasicElements.class);
        final Configuration configuration = new DefaultConfiguration();
        MixinClass<DefaultWebElements> webElemsClass = MixinClasses.builder(DefaultWebElements.class).addInterfaces(Mixin.class).build();
        DefaultWebElements loading = webElemsClass.newInstance(new AbstractMixinInitializer() {
            @Override
            protected void initialize() {
                implement(AsIs.class, Mixin.class).with(new AsIsImpl());
                implement(HasConfiguration.class).with(new HasConfiguration.Impl(configuration));
                implement(BasicElements.class).with(basicElements);
                implement(DefaultWebElements.class).with(new AbstractInvocationHandler() {
                    @Override
                    protected Object handleInvocation(Object arg0, Method arg1, Object[] arg2) throws Throwable {
                        return null;
                    }
                });
            }
        });
        Interaction interaction = mock(Interaction.class);

        InteractionListener retryOnTimeout = WebInteractionListeners
                .onTimeout()
                .when(loading)
                .waitForUnexistence(loading)
                .withWaitingPreset("very-slow")
                .thenRetry();
        when(basicElements.size()).then(new Answer<Integer>() {
            int next = 1;
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                return next--;
            }
        });
        AfterFailInteractionEvent event = new AfterFailInteractionEvent(loading, interaction, new TimeoutException());
        retryOnTimeout.onEvent(event);

        verify(basicElements, times(2)).size();
        Assert.assertThat(event.isRetry(), is(true));
    }
}
