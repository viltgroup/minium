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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minium.cucumber.data.reader.DataDTO;
import minium.cucumber.data.reader.DataReader;
import minium.cucumber.data.reader.DataReaderFactory;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cucumber.runtime.FeatureBuilder;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.model.PathWithLines;

public class MiniumFeatureBuilder extends FeatureBuilder {

    private PrettyFormatter prettyFormatter;
    private static final Pattern SOURCE_COMMENT_REGEX = Pattern.compile("#\\s*@source\\s*:(.*)");

    private Map<Integer, Integer> featureLineOffset;

    private File resourceDir;
    private File baseDir = new File("src/test/resources");
    private Integer lineOffset = 0;
    private boolean isPreview;

    public MiniumFeatureBuilder(List<CucumberFeature> cucumberFeatures, PrettyFormatter prettyFormatter, File resourceDir) {
        super(cucumberFeatures);
        this.prettyFormatter = prettyFormatter;
        this.resourceDir = resourceDir;
        this.featureLineOffset = Maps.newTreeMap();
        this.isPreview = false;
    }

    public MiniumFeatureBuilder(List<CucumberFeature> cucumberFeatures, File resourceDir) {
        super(cucumberFeatures);
        this.prettyFormatter = new PrettyFormatter(new StringWriter(), true, false);
        this.resourceDir = resourceDir;
        this.featureLineOffset = Maps.newTreeMap();
        this.isPreview = false;
    }

    public MiniumFeatureBuilder(List<CucumberFeature> cucumberFeatures) {
        super(cucumberFeatures);
        prettyFormatter = new PrettyFormatter(new StringWriter(), true, false);
        this.resourceDir = null;
        this.featureLineOffset = Maps.newTreeMap();
        this.isPreview = false;
    }

    public MiniumFeatureBuilder(List<CucumberFeature> cucumberFeatures, boolean isPreview) {
        super(cucumberFeatures);
        prettyFormatter = new PrettyFormatter(new StringWriter(), true, false);
        this.resourceDir = null;
        this.isPreview = isPreview;
        this.featureLineOffset = Maps.newTreeMap();
    }

    public MiniumFeatureBuilder(List<CucumberFeature> cucumberFeatures, PrettyFormatter prettyFormatter, File resourceDir, boolean isPreview) {
        super(cucumberFeatures);
        this.prettyFormatter = prettyFormatter;
        this.resourceDir = resourceDir;
        this.isPreview = isPreview;
        this.featureLineOffset = Maps.newTreeMap();
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
        if (lineOffset != 0 && isPreview) {
            int newLine = scenario.getLine() + lineOffset;
            featureLineOffset.put(scenario.getLine(), newLine);
        }
        super.scenario(scenario);
        prettyFormatter.scenario(scenario);
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {
        if (lineOffset != 0 && isPreview) {
            int newLine = scenarioOutline.getLine() + lineOffset;
            featureLineOffset.put(scenarioOutline.getLine(), newLine);
        }
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
        if (cells != null && lineOffset != 0 && isPreview && cells.getComments().isEmpty()) {
            int newLine = cells.getLine() + lineOffset.intValue();
            featureLineOffset.put(cells.getLine(), newLine);
            List<ExamplesTableRow> rows = examples.getRows();
            List<ExamplesTableRow> newRows = new ArrayList<ExamplesTableRow>();
            for (ExamplesTableRow examplesTableRow : rows) {
                featureLineOffset.put(examplesTableRow.getLine(), examplesTableRow.getLine() + lineOffset);
                ExamplesTableRow tableRow = new ExamplesTableRow(examplesTableRow.getComments(), examplesTableRow.getCells(), examplesTableRow.getLine()
                        + lineOffset, examplesTableRow.getId());
                newRows.add(tableRow);
            }
            examples.setRows(newRows);
        }
        if (cells != null && !cells.getComments().isEmpty()) {
            String filePathFromComment = getExtractFilePathFromComment(cells.getComments().get(0).getValue());
            Resource sourceResource = getSourceResource(filePathFromComment);

            if (sourceResource != null) {
                loadAndReplaceExamples(examples, cells, filePathFromComment, sourceResource);
            }
        }

        super.examples(examples);
        prettyFormatter.examples(examples);
    }

    private void loadAndReplaceExamples(Examples examples, ExamplesTableRow cells, String filePathFromComment, Resource sourceResource) {
        List<ExamplesTableRow> rows = new ArrayList<ExamplesTableRow>();
        List<ExamplesTableRow> oldrows = examples.getRows();
        ExamplesTableRow tableRowHeader = oldrows.get(0);
        rows.add(tableRowHeader);

        DataReader dataReader;
        try {
            InputStream inputStream = sourceResource.getInputStream();
            dataReader = DataReaderFactory.create(sourceResource.getFilename());
            DataDTO data = dataReader.readExamples(inputStream);
            ExamplesTableRow tableRow;
            int lineNumOffset = 1;
            for (Integer lineNumInResource : data.getValues().keySet()) {
                List<String> values = data.getValues().get(lineNumInResource);
                String valueComment = String.format("# %s:%d", filePathFromComment, lineNumInResource + 1);
                Comment comment = new Comment(valueComment, lineNumInResource);
                List<Comment> comments = Lists.newArrayList(comment);
                int newLineNum = tableRowHeader.getLine() + lineNumOffset;
                if (isPreview) {
                    newLineNum += lineOffset;
                }
                tableRow = new ExamplesTableRow(comments, values, newLineNum, cells.getId());
                rows.add(tableRow);

                // newLine + 1 added because of the comment that is inserted
                featureLineOffset.put(tableRowHeader.getLine() + lineNumOffset, newLineNum + 1);

                lineOffset += 1;
                lineNumOffset++;
            }

            // re-calculate lineOffset
            lineOffset = (lineOffset + lineNumOffset) - examples.getRows().size();
            examples.setRows(rows);
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public void step(Step step) {
        if (lineOffset != 0 && isPreview) {
            int newLine = step.getLine().intValue() + lineOffset.intValue();
            featureLineOffset.put(step.getLine().intValue(), newLine);
            step = new Step(step.getComments(), step.getKeyword(), step.getName(), newLine, step.getRows(), step.getDocString());
        }

        if (step.getRows() != null && step.getRows().get(0) != null && !step.getRows().get(0).getComments().isEmpty()) {
            String filePathFromComment = getExtractFilePathFromComment(step.getRows().get(0).getComments().get(0).getValue());
            Resource sourceResource = getSourceResource(filePathFromComment);

            if (sourceResource != null) {
                step = loadAndReplaceSteps(step, filePathFromComment, sourceResource);
            }
        }
        super.step(step);
        prettyFormatter.step(step);
    }

    private Step loadAndReplaceSteps(Step step, String filePathFromComment, Resource sourceResource) {
        DataReader dataReader;
        try {
            InputStream inputStream = sourceResource.getInputStream();
            dataReader = DataReaderFactory.create(sourceResource.getFilename());
            DataDTO data = dataReader.readTable(inputStream);

            List<DataTableRow> rows = Lists.newArrayList();
            for (Integer lineNum : data.getValues().keySet()) {
                List<String> cells = Lists.newArrayList(data.getValues().get(lineNum));
                String valueComment = String.format("# %s:%d", filePathFromComment, lineNum + 1);
                Comment comment = new Comment(valueComment, lineNum);
                List<Comment> comments = Lists.newArrayList(comment);
                int newLineNum = step.getLine() + 2;
                if (isPreview) {
                    newLineNum += lineOffset;
                }
                DataTableRow row = new DataTableRow(comments, cells, newLineNum + 1);
                rows.add(row);
                lineOffset += 2;
            }
            // re-calculate lineOffset
            lineOffset = lineOffset - step.getRows().size() - 1;
            step = new Step(step.getComments(), step.getKeyword(), step.getName(), step.getLine(), rows, step.getDocString());

        } catch (InstantiationException | IllegalAccessException | IOException e) {
            throw Throwables.propagate(e);
        }
        return step;
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

    private String getExtractFilePathFromComment(String comment) {
        Matcher matcher = SOURCE_COMMENT_REGEX.matcher(comment);
        return matcher.matches() ? matcher.group(1).trim() : null;
    }

    private Resource getSourceResource(String path) {
        if (path == null) {
            return null;
        }

        File file = new File(path);
        if (!file.isAbsolute() && resourceDir != null) {
            return new FileSystemResource(new File(resourceDir, path));
        }
        if (!file.isAbsolute()) {
            String classLoaderPath = new File("").getAbsolutePath();
            File basePath = new File(baseDir, path);
            File resourcePath = new File(classLoaderPath, basePath.getPath());
            FileSystemResource classPathResource = new FileSystemResource(resourcePath.getPath());
            return classPathResource;
        }
        return new FileSystemResource(file);
    }

    public static List<CucumberFeature> load(ResourceLoader resourceLoader, List<String> featurePaths, List<Object> filters, PrintStream out) {
        List<CucumberFeature> cucumberFeatures = load(resourceLoader, featurePaths, filters, null, null);
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

    public static List<CucumberFeature> load(ResourceLoader resourceLoader, List<String> featurePaths, List<Object> filters, PrintStream out, File resourceDir) {

        List<CucumberFeature> cucumberFeatures = load(resourceLoader, featurePaths, filters, resourceDir);
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

    public static List<CucumberFeature> load(ResourceLoader resourceLoader, List<String> featurePaths, List<Object> filters, File resourceDir) {
        List<CucumberFeature> cucumberFeatures = new ArrayList<CucumberFeature>();
        MiniumFeatureBuilder builder = new MiniumFeatureBuilder(cucumberFeatures, resourceDir);
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
                    loadFromFeaturePath(builder, resourceLoader, "classpath: " + featurePath, filters, true);
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

    public Map<Integer, Integer> getLineOffset() {
        return featureLineOffset;
    }

    public void setLineOffset(Map<Integer, Integer> lineOffset) {
        this.featureLineOffset = lineOffset;
    }

}
