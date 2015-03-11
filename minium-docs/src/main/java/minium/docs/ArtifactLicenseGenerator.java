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
package minium.docs;

import static java.lang.String.format;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ArtifactLicenseGenerator {

    public static class MavenArtifactLicense {

        private static final class ArtifactLicenseComparator implements Comparator<MavenArtifactLicense> {
            @Override
            public int compare(MavenArtifactLicense o1, MavenArtifactLicense o2) {
                return format("%s:%s", o1.groupId, o1.artifactId).compareTo(format("%s:%s", o2.groupId, o2.artifactId));
            }
        }

        private static final Pattern LICENSE_REGEX = Pattern.compile("^\\s*\\((.*)\\) (.*) \\((.*):(.*):(.*) - (.*)\\)$");

        String groupId;
        String artifactId;
        String version;
        String name;
        String licenses;
        String url;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getArtifactId() {
            return artifactId;
        }

        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }

        public String getName() {
            return name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getLicenses() {
            return licenses;
        }

        public void setLicenses(String license) {
            this.licenses = license;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static List<MavenArtifactLicense> readFromFile(File file) throws IOException {
            List<MavenArtifactLicense> artifactLicenses = Lists.newArrayList();
            if (file == null || !file.exists()) return artifactLicenses;

            List<String> lines = FileUtils.readLines(file);
            for (String line : lines) {
                Matcher matcher = LICENSE_REGEX.matcher(line);
                if (!matcher.matches())
                    continue;
                MavenArtifactLicense artifactLicense = new MavenArtifactLicense();
                artifactLicense.licenses = matcher.group(1).replaceAll("\\) \\(", " / ");
                artifactLicense.name = matcher.group(2);
                artifactLicense.groupId = matcher.group(3);
                artifactLicense.artifactId = matcher.group(4);
                artifactLicense.version = matcher.group(5);
                artifactLicense.url = matcher.group(6);
                artifactLicenses.add(artifactLicense);
            }
            Collections.sort(artifactLicenses, new ArtifactLicenseComparator());
            return artifactLicenses;
        }
    }

    public static class BowerLibLicense {

        private static final class BowerLibLicenseComparator implements Comparator<BowerLibLicense> {
            @Override
            public int compare(BowerLibLicense o1, BowerLibLicense o2) {
                return o1.name.compareTo(o2.name);
            }
        }

        String name;
        String version;
        String licenses;
        String homepage;
        String repository;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getLicenses() {
            return licenses;
        }

        public void setLicenses(String licenses) {
            this.licenses = licenses;
        }

        public String getHomepage() {
            return homepage;
        }

        public void setHomepage(String homepage) {
            this.homepage = homepage;
        }

        public String getRepository() {
            return repository;
        }

        public void setRepository(String repository) {
            this.repository = repository;
        }

        public static List<BowerLibLicense> readFromFile(File file) throws IOException {
            List<BowerLibLicense> libLicenses = Lists.newArrayList();
            if (file == null || !file.exists()) return libLicenses;

            String json = FileUtils.readFileToString(file);

            JSONObject jsonObject = new JSONObject(json);
            for (Object key : jsonObject.keySet()) {
                BowerLibLicense libLicense = new BowerLibLicense();
                Iterable<String> nameAndVersion = Splitter.on("@").trimResults().omitEmptyStrings().split(key.toString());
                libLicense.name = Iterables.get(nameAndVersion, 0);
                libLicense.version = Iterables.get(nameAndVersion, 1);
                JSONObject jsonLib = jsonObject.getJSONObject((String) key);
                JSONArray licensesArray = jsonLib.optJSONArray("licenses");
                List<String> licenses = Lists.newArrayList();
                if (licensesArray != null) {
                    for (int i = 0; i < licensesArray.length(); i++) {
                        String license = licensesArray.getString(i);
                        licenses.add(license);
                    }
                }
                libLicense.licenses = Joiner.on(" / ").join(licenses);
                libLicense.homepage = jsonLib.optString("homepage");
                libLicense.repository = jsonLib.optString("repository");
                libLicenses.add(libLicense);
            }

            Collections.sort(libLicenses, new BowerLibLicenseComparator());
            return libLicenses;
        }
    }

    @Option(name = "--templateFile")
    private File template;

    @Option(name = "--outputFile")
    private File outputFile;

    @Option(name = "--artifactLicensesFile")
    private File artifactLicensesFile;

    @Option(name = "--bowerLicensesFile")
    private File bowerLicensesFile;

    public void run() throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(template.getParentFile());

        Template temp = cfg.getTemplate(template.getName());

        List<MavenArtifactLicense> artifactLicenses = MavenArtifactLicense.readFromFile(artifactLicensesFile);
        List<BowerLibLicense> bowerLicenses = BowerLibLicense.readFromFile(bowerLicensesFile);

        Map<String, Object> root = ImmutableMap.<String, Object>of("artifactLicenses", artifactLicenses, "bowerLicenses", bowerLicenses);

        // ensure parent folder exists
        outputFile.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(outputFile)) {
            temp.process(root, writer);
        }
    }

    public static void main(String[] args) throws IOException, CmdLineException, TemplateException {
        ArtifactLicenseGenerator generator = new ArtifactLicenseGenerator();
        CmdLineParser parser = new CmdLineParser(generator);
        parser.parseArgument(args);
        generator.run();
    }
}
