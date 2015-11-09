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
/*
 * Copyright (c) 2008-2014 The Cucumber Organisation
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package minium.cucumber;

import java.io.File;
import java.io.IOException;
import java.util.List;

import minium.cucumber.internal.MiniumActiveProfilesResolver;
import minium.cucumber.internal.MiniumProfileRunner;
import minium.cucumber.internal.MiniumProfileRunner.MiniumCucumberTest;
import minium.cucumber.internal.MiniumRhinoTestContextManager;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import cucumber.runtime.junit.Assertions;

public class MiniumCucumber extends ParentRunner<MiniumProfileRunner> {

    static {
        // disable Spring boot banner
        System.setProperty("spring.main.show_banner", "false");
        System.setProperty("minium.resources.dir", new File("src/test/resources").getAbsolutePath());
    }

    private List<MiniumProfileRunner> children = Lists.newArrayList();

    public MiniumCucumber(Class<?> clazz) throws InitializationError, IOException {
        super(clazz);
        Assertions.assertNoCucumberAnnotatedMethods(clazz);

        for (String[] profiles : getProfilesMatrix()) {
            MiniumActiveProfilesResolver.setActiveProfiles(profiles);
            try {
                MiniumRhinoTestContextManager testContextManager = new MiniumRhinoTestContextManager(MiniumCucumberTest.class);
                MiniumProfileRunner profileRunner = testContextManager.getBeanFactory().createBean(MiniumProfileRunner.class);
                profileRunner.setTestContextManager(testContextManager);
                children.add(profileRunner);
            } finally {
                MiniumActiveProfilesResolver.setActiveProfiles();
            }
        }
    }

    private List<String[]> getProfilesMatrix() {
        String property = System.getProperty("minium.cucumber.matrix", "");
        List<String> profilesMatrixLines = Splitter.on(";").trimResults().omitEmptyStrings().splitToList(property);
        List<String[]> profilesMatrix = FluentIterable.from(profilesMatrixLines).transform(new Function<String, String[]>() {
            @Override
            public String[] apply(String profiles) {
                return Iterables.toArray(Splitter.on(",").trimResults().omitEmptyStrings().split(profiles), String.class);
            }
        }).toList();

        if (profilesMatrix.isEmpty()) {
            String activeProfiles = System.getProperty("spring.profiles.active", null);
            profilesMatrix = ImmutableList.of(activeProfiles == null ? new String[0] : new String[] { activeProfiles });
        }

        return profilesMatrix;
    }

    @Override
    public List<MiniumProfileRunner> getChildren() {
        return children;
    }

    @Override
    protected Description describeChild(MiniumProfileRunner child) {
        return child.getDescription();
    }

    @Override
    protected void runChild(MiniumProfileRunner child, RunNotifier notifier) {
        child.run(notifier);
    }
}
