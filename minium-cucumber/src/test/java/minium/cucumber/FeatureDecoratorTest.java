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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gherkin.formatter.PrettyFormatter;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import minium.cucumber.data.MiniumFeatureBuilder;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import cucumber.runtime.io.Resource;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.model.CucumberScenario;
import cucumber.runtime.model.CucumberScenarioOutline;

public class FeatureDecoratorTest {

    public static final List<Object> NO_FILTERS = emptyList();

    @Test
    public void testNumberOfFeaturesParsed() throws IOException, InstantiationException, IllegalAccessException {
        List<CucumberFeature> features = new ArrayList<CucumberFeature>();
        try (MiniumFeatureBuilder builder = new MiniumFeatureBuilder(features)) {
            Resource resource = createResourceMock("example.feature");
            builder.parse(resource, ImmutableList.of());
        }
        assertEquals(1, features.size());
    }

    @Test
    public void testLoadExternalWithExamples() throws IOException {
        List<CucumberFeature> features = new ArrayList<CucumberFeature>();
        try (MiniumFeatureBuilder builder = new MiniumFeatureBuilder(features)) {
            Resource resource = createResourceMock("example-with-comments.feature");
            builder.parse(resource, NO_FILTERS);
        }

        CucumberScenarioOutline cucumberScenarioOutline = (CucumberScenarioOutline) features.get(0).getFeatureElements().get(0);
        assertEquals(8, cucumberScenarioOutline.getCucumberExamplesList().get(0).getExamples().getRows().size());
    }

    @Test
    public void testLoadDataWithDataTable() throws IOException {
        List<CucumberFeature> features = new ArrayList<CucumberFeature>();
        try (MiniumFeatureBuilder builder = new MiniumFeatureBuilder(features)) {
            Resource resource = createResourceMock("example-with-data-table.feature");
            builder.parse(resource, NO_FILTERS);
        }

        CucumberScenario cucumberScenario = (CucumberScenario) features.get(0).getFeatureElements().get(0);
        assertEquals(4, cucumberScenario.getSteps().get(1).getRows().size());
    }

    @Test
    public void testNormalFeatureWithoutLoadData() throws IOException {
        List<CucumberFeature> features = new ArrayList<CucumberFeature>();
        try (MiniumFeatureBuilder builder = new MiniumFeatureBuilder(features)) {
            Resource resource = createResourceMock("example-without-comments.feature");
            builder.parse(resource, NO_FILTERS);
        }
        CucumberScenarioOutline cucumberScenarioOutline = (CucumberScenarioOutline) features.get(0).getFeatureElements().get(0);
        assertEquals(4, cucumberScenarioOutline.getCucumberExamplesList().get(0).getExamples().getRows().size());

        CucumberScenario cucumberScenario = (CucumberScenario) features.get(0).getFeatureElements().get(1);
        assertEquals(3, cucumberScenario.getSteps().get(1).getRows().size());
    }

    @Test
    public void testValidateFeatureSyntax() throws IOException {
        List<CucumberFeature> features = new ArrayList<CucumberFeature>();
        try (MiniumFeatureBuilder builder = new MiniumFeatureBuilder(features)) {
            Resource resource = createResourceMock("foo-feature-with-comment.feature");
            builder.parse(resource, NO_FILTERS);
        }
    }

    @Test
    public void testFeatureWithDataAndErrors() throws Exception {
        List<CucumberFeature> features = new ArrayList<CucumberFeature>();
        StringWriter writer = new StringWriter();
        final PrettyFormatter formatter = new PrettyFormatter(writer, true, false);
        try (MiniumFeatureBuilder builder = new MiniumFeatureBuilder(features,formatter,null)) {
            Resource resource = createResourceMock("feature-with-comment-and-errors.feature");
            try {
                builder.parse(resource, NO_FILTERS);
                fail("Feature should not have a valid format");
            } catch (Exception e) {
            }
        }
    }

    @Test
    public void featureLoadingDataFromFileThatDontExists() throws Exception {
        List<CucumberFeature> features = new ArrayList<CucumberFeature>();
        StringWriter writer = new StringWriter();
        final PrettyFormatter formatter = new PrettyFormatter(writer, true, false);
        try (MiniumFeatureBuilder builder = new MiniumFeatureBuilder(features,formatter,null)) {
            Resource resource = createResourceMock("feature-loading-file-that-dont-exists.feature");
            try {
                builder.parse(resource, NO_FILTERS);
                fail("Feature should not load the data- the csv path is invalid");
            } catch (Exception e) {
            }
        }
    }

    @Test
    public void testLoadDataInExample() throws IOException {
        List<CucumberFeature> cucumberFeatures = Lists.newArrayList();
        StringWriter writer = new StringWriter();
        final PrettyFormatter formatter = new PrettyFormatter(writer, true, false);
        MiniumFeatureBuilder builder = new MiniumFeatureBuilder(cucumberFeatures, formatter,null);
        Resource resource = createResourceMock("foo-feature-with-comment.feature");
        builder.parse(resource, Collections.emptyList());
        Assert.assertThat(writer.toString().replaceAll("\\s+", " "), Matchers.equalTo(
                Joiner.on("\n").join("Feature: Test 1",
                        "",
                        "  Scenario Outline: Hello world",
                        "    Given <foo> exists",
                        "    Then <bar> should occur",
                        "",
                        "    Examples: ",
                        "      #@source :data-foo.csv",
                        "     | foo2     | bar2      |",
                        "      # data-foo.csv:1",
                        "     | valChanged1 | valChanged2 |",
                        "      # data-foo.csv:2",
                        "     | valChanged3 | valChanged4 |",
                        "").replaceAll("\\s+", " ")));
    }

    private Resource createResourceMock(String featurePath) throws IOException {
        Resource resource = mock(Resource.class);
        when(resource.getPath()).thenReturn(featurePath);
        when(resource.getInputStream()).thenReturn(FeatureDecoratorTest.class.getClassLoader().getResourceAsStream(featurePath));
        return resource;
    }
}
