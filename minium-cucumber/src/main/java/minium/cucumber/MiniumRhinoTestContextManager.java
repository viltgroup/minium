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
package minium.cucumber;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.google.common.base.Throwables;

public class MiniumRhinoTestContextManager extends TestContextManager {

    public MiniumRhinoTestContextManager(Class<?> testClass) {
        super(testClass);
        this.registerTestExecutionListeners(new DependencyInjectionTestExecutionListener());
    }

    public ConfigurableListableBeanFactory getBeanFactory() {
        return getContext().getBeanFactory();
    }

    public Class<?> getTestClass() {
        return getTestContext().getTestClass();
    }

    public ConfigurableApplicationContext getContext() {
        return (ConfigurableApplicationContext) getTestContext().getApplicationContext();
    }

    public Object newInstance() {
        try {
            Object instance = this.getTestClass().newInstance();
            this.prepareTestInstance(instance);
            return instance;
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}