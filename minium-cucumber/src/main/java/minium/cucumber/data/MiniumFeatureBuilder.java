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
package minium.cucumber.data;

import gherkin.formatter.PrettyFormatter;
import gherkin.formatter.model.Background;
import gherkin.formatter.model.Comment;
import gherkin.formatter.model.DataTableRow;
import gherkin.formatter.model.Examples;
import gherkin.formatter.model.ExamplesTableRow;
import gherkin.formatter.model.Feature;
import gherkin.formatter.model.Scenario;
import gherkin.formatter.model.ScenarioOutline;
import gherkin.formatter.model.Step;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minium.cucumber.data.reader.DataDTO;
import minium.cucumber.data.reader.DataReader;
import minium.cucumber.data.reader.DataReaderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

import cucumber.runtime.FeatureBuilder;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.model.PathWithLines;

public class MiniumFeatureBuilder extends FeatureBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(MiniumFeatureBuilder.class);

    private PrettyFormatter prettyFormatter;
    private static final Pattern SOURCE_COMMENT_REGEX = Pattern.compile("#\\s*@source\\s*:(.*)");

    private File resourceDir;

    public MiniumFeatureBuilder(List<CucumberFeature> cucumberFeatures, PrettyFormatter prettyFormatter, File resourceDir) {
        super(cucumberFeatures);
        this.prettyFormatter = prettyFormatter;
        this.resourceDir = resourceDir;
    }

    public MiniumFeatureBuilder(List<CucumberFeature> cucumberFeatures) {
        super(cucumberFeatures);
        prettyFormatter = new PrettyFormatter(new StringWriter(), true, false);
        this.resourceDir = null;
    }

    @Override
    public void uri(String uri) {
        super.uri(uri);
        prettyFormatter.uri(uri);
    }

    @Override
    public void feature(Feature feature) {
        super.feature(feature);
        prettyFormatter.feature(feature);
    }

    @Override
    public void background(Background background) {
        super.background(background);
        prettyFormatter.background(background);
    }

    @Override
    public void scenario(Scenario scenario) {
        super.scenario(scenario);
        prettyFormatter.scenario(scenario);
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        super.scenarioOutline(scenarioOutline);
        prettyFormatter.scenarioOutline(scenarioOutline);
    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {
        super.startOfScenarioLifeCycle(scenario);
        prettyFormatter.startOfScenarioLifeCycle(scenario);
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        super.endOfScenarioLifeCycle(scenario);
        prettyFormatter.endOfScenarioLifeCycle(scenario);
    }

    @Override
    public void examples(Examples examples) {

        ExamplesTableRow cells = examples.getRows().isEmpty() ? null : examples.getRows().get(0);
        if (cells != null && !cells.getComments().isEmpty()) {
            Resource sourceResource = getSourceResource(cells.getComments().get(0).getValue());
            if (sourceResource != null) {
                List<ExamplesTableRow> rows = new ArrayList<ExamplesTableRow>();
                ExamplesTableRow tableRow = new ExamplesTableRow(cells.getComments(), cells.getCells(), cells.getLine(), cells.getId());
                rows.add(tableRow);

                DataReader dataReader;
                try {
                    InputStream inputStream = sourceResource.getInputStream();
                    dataReader = DataReaderFactory.create(sourceResource.getFilename());
                    DataDTO data = dataReader.readExamples(inputStream);

                    int j = 1;
                    for (Integer lineNum : data.getValues().keySet()) {
                        List<String> values = data.getValues().get(lineNum);
                        Comment comment = new Comment("#" + sourceResource.getFilename(), lineNum);
                        List<Comment> comments = Lists.newArrayList(comment);
                        tableRow = new ExamplesTableRow(comments, values, cells.getLine() + j++, cells.getId());
                        rows.add(tableRow);
                    }
                    examples.setRows(rows);
                } catch (IOException | InstantiationException | IllegalAccessException e) {
                    throw Throwables.propagate(e);
                }
            }
        }

        super.examples(examples);
        prettyFormatter.examples(examples);
    }

    @Override
    public void step(Step step) {
        if (step.getRows() != null && step.getRows().get(0) != null && !step.getRows().get(0).getComments().isEmpty()) {
            String commentValue = step.getRows().get(0).getComments().get(0).getValue();
            Resource sourceResource = getSourceResource(commentValue);

            if (sourceResource != null) {
                DataReader dataReader;
                try {
                    InputStream inputStream = sourceResource.getInputStream();
                    dataReader = DataReaderFactory.create(sourceResource.getFilename());
                    DataDTO data = dataReader.readTable(inputStream);

                    List<DataTableRow> rows = Lists.newArrayList();
                    for (Integer lineNum : data.getValues().keySet()) {
                        List<String> cells = Lists.newArrayList(data.getValues().get(lineNum));
                        Comment comment = new Comment("#" + sourceResource.getFilename(), lineNum);
                        List<Comment> comments = Lists.newArrayList(comment);
                        DataTableRow row = new DataTableRow(comments, cells, step.getLine());
                        rows.add(row);
                    }

                    step = new Step(step.getComments(), step.getKeyword(), step.getName(), step.getLine(), rows, step.getDocString());

                } catch (InstantiationException | IllegalAccessException | IOException e) {
                    throw Throwables.propagate(e);
                }
            }
        }
        super.step(step);
        prettyFormatter.step(step);
    }

    @Override
    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {
        super.syntaxError(state, event, legalEvents, uri, line);
        prettyFormatter.syntaxError(state, event, legalEvents, uri, line);
    }

    @Override
    public void done() {
        super.done();
        prettyFormatter.done();
    }

    @Override
    public void close() {
        super.close();
        prettyFormatter.close();
    }

    @Override
    public void eof() {
        super.eof();
        prettyFormatter.eof();
    }

    public PrettyFormatter getPrettyFormatter() {
        return prettyFormatter;
    }

    public void setPrettyFormatter(PrettyFormatter prettyFormatter) {
        this.prettyFormatter = prettyFormatter;
    }

    private Resource getSourceResource(String comment) {
        Matcher matcher = SOURCE_COMMENT_REGEX.matcher(comment);
        if (!matcher.matches())
            return null;
        String path = matcher.group(1).trim();
        File file = new File(path);
        if (!file.isAbsolute() && resourceDir != null) {
            return new FileSystemResource(new File(resourceDir, path));
        }
        return new FileSystemResource(file);
    }

    public static List<CucumberFeature> load(ResourceLoader resourceLoader, List<String> featurePaths, List<Object> filters, PrintStream out) {
        List<CucumberFeature> cucumberFeatures = load(resourceLoader, featurePaths, filters);
        if (cucumberFeatures.isEmpty()) {
            if (featurePaths.isEmpty())
                out.println(String.format("Got no path to feature directory or feature file", new Object[0]));
            else if (filters.isEmpty())
                out.println(String.format("No features found at %s", new Object[] { featurePaths }));
            else {
                out.println(String.format("None of the features at %s matched the filters: %s", new Object[] { featurePaths, filters }));
            }
        }
        return cucumberFeatures;
    }

    public static List<CucumberFeature> load(ResourceLoader resourceLoader, List<String> featurePaths, List<Object> filters) {
        List<CucumberFeature> cucumberFeatures = new ArrayList<CucumberFeature>();
        MiniumFeatureBuilder builder = new MiniumFeatureBuilder(cucumberFeatures);
        for (String featurePath : featurePaths) {
            if (featurePath.startsWith("@")) {
                loadFromRerunFile(builder, resourceLoader, featurePath.substring(1), filters);
            } else {
                loadFromFeaturePath(builder, resourceLoader, featurePath, filters, false);
            }
        }
        Collections.sort(cucumberFeatures, new CucumberFeatureUriComparator());
        return cucumberFeatures;
    }

    private static void loadFromFeaturePath(MiniumFeatureBuilder builder, ResourceLoader resourceLoader, String featurePath, List<Object> filters,
            boolean failOnNoResource) {
        PathWithLines pathWithLines = new PathWithLines(featurePath);
        List<Object> filtersForPath = new ArrayList<Object>(filters);
        filtersForPath.addAll(pathWithLines.lines);
        Iterable<cucumber.runtime.io.Resource> resources = resourceLoader.resources(pathWithLines.path, ".feature");

        for (cucumber.runtime.io.Resource resource : resources)
            builder.parse(resource, filtersForPath);
    }

    private static void loadFromRerunFile(MiniumFeatureBuilder builder, ResourceLoader resourceLoader, String rerunPath, List<Object> filters) {
        Iterable<cucumber.runtime.io.Resource> resources = resourceLoader.resources(rerunPath, null);
        for (cucumber.runtime.io.Resource resource : resources) {
            String source = builder.read(resource);
            if (!(source.isEmpty()))
                for (String featurePath : source.split(" "))
                    loadFromFileSystemOrClasspath(builder, resourceLoader, featurePath, filters);
        }
    }

    private static void loadFromFileSystemOrClasspath(MiniumFeatureBuilder builder, ResourceLoader resourceLoader, String featurePath, List<Object> filters) {
        try {
            loadFromFeaturePath(builder, resourceLoader, featurePath, filters, false);
        } catch (IllegalArgumentException originalException) {
            if ((!(featurePath.startsWith("classpath:"))) && (originalException.getMessage().contains("Not a file or directory"))) {
                try {
                    loadFromFeaturePath(builder, resourceLoader, "classpath:" + featurePath, filters, true);
                } catch (IllegalArgumentException secondException) {
                    if (secondException.getMessage().contains("No resource found for")) {
                        throw new IllegalArgumentException("Neither found on file system or on classpath: " + originalException.getMessage() + ", "
                                + secondException.getMessage());
                    }
                    throw secondException;
                }
            } else
                throw originalException;
        }
    }

    private static class CucumberFeatureUriComparator implements Comparator<CucumberFeature> {
        @Override
        public int compare(CucumberFeature a, CucumberFeature b) {
            return a.getPath().compareTo(b.getPath());
        }
    }
}
