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
package minium.cucumber;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import minium.cucumber.data.MiniumFeatureBuilder;

import org.junit.Test;

import com.google.common.collect.Maps;

import cucumber.runtime.io.Resource;
import cucumber.runtime.model.CucumberFeature;

public class FeatureLineOffsetsTest {

    public static final List<Object> NO_FILTERS = emptyList();

    @Test
    public void testLinesOffSetsWithoutChanges() throws IOException {
        List<CucumberFeature> features = new ArrayList<CucumberFeature>();
        Map<Integer, Integer> lineOffset;
        try (MiniumFeatureBuilder builder = new MiniumFeatureBuilder(features, true)) {
            Resource resource = createResourceMock("example-without-comments.feature");
            builder.parse(resource, NO_FILTERS);
            lineOffset = builder.getLineOffset();
        }
        assertTrue(lineOffset.values().isEmpty());
        assertTrue(lineOffset.keySet().isEmpty());
    }

    @Test
    public void testLinesOffSetslWithExamples() throws IOException {
        Map<Integer, Integer> lineOffsetExpected = Maps.newTreeMap();
        lineOffsetExpected.put(21, 22);
        lineOffsetExpected.put(22, 24);
        lineOffsetExpected.put(23, 26);
        lineOffsetExpected.put(24, 28);
        lineOffsetExpected.put(25, 36);
        lineOffsetExpected.put(26, 37);
        lineOffsetExpected.put(27, 38);
        lineOffsetExpected.put(32, 47);
        lineOffsetExpected.put(33, 48);
        lineOffsetExpected.put(34, 49);
        List<CucumberFeature> features = new ArrayList<CucumberFeature>();
        Map<Integer, Integer> lineOffset;
        try (MiniumFeatureBuilder builder = new MiniumFeatureBuilder(features, true)) {
            Resource resource = createResourceMock("example-with-comments.feature");
            builder.parse(resource, NO_FILTERS);
            lineOffset = builder.getLineOffset();
        }
        assertTrue(lineOffsetExpected.equals(lineOffset));
    }

    @Test
    public void testLinesOffSetsWithDataTables() throws IOException {
        Map<Integer, Integer> lineOffsetExpected = Maps.newTreeMap();
        lineOffsetExpected.put(14, 16);
        lineOffsetExpected.put(15, 17);
        lineOffsetExpected.put(16, 18);
        lineOffsetExpected.put(22, 24);
        lineOffsetExpected.put(23, 25);
        lineOffsetExpected.put(24, 26);
        lineOffsetExpected.put(28, 30);
        lineOffsetExpected.put(29, 31);
        lineOffsetExpected.put(30, 32);
        List<CucumberFeature> features = new ArrayList<CucumberFeature>();
        Map<Integer, Integer> lineOffset;
        try (MiniumFeatureBuilder builder = new MiniumFeatureBuilder(features, true)) {
            Resource resource = createResourceMock("feature-to-test-offsets.feature");
            builder.parse(resource, NO_FILTERS);
            lineOffset = builder.getLineOffset();
        }

        assertTrue(lineOffsetExpected.equals(lineOffset));
    }

    private Resource createResourceMock(String featurePath) throws IOException {
        Resource resource = mock(Resource.class);
        when(resource.getPath()).thenReturn(featurePath);
        when(resource.getInputStream()).thenReturn(FeatureDecoratorTest.class.getClassLoader().getResourceAsStream(featurePath));
        return resource;
    }
}
