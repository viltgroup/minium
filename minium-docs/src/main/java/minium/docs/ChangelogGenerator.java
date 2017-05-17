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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ChangelogGenerator {

    public static class Ticket {

        private static final Pattern VERSION_REGEX = Pattern.compile("^\\s*(Version .* \\(.*\\))\\s*$");
        private static final Pattern TICKET_REGEX = Pattern.compile("^\\- (.*) \\#(\\d+): (.*)$");

        private String version;
        private String tracker;
        private String id;
        private String subject;
        private String url;

        public String getTracker() {
            return tracker;
        }

        public void setTracker(String tracker) {
            this.tracker = tracker;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public static Multimap<String, Ticket> readFromFile(File file, String urlFormat) throws IOException {
            Multimap<String, Ticket> tickets = LinkedHashMultimap.create();
            List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);

            List<Ticket> ticketsPerVersion = null;
            String currVersion = null;
            for (String line : lines) {
                Matcher versionMatcher = VERSION_REGEX.matcher(line);
                if (versionMatcher.matches()) {
                    if (ticketsPerVersion != null) tickets.putAll(currVersion, ticketsPerVersion);
                    ticketsPerVersion = Lists.newArrayList();
                    currVersion = versionMatcher.group(1);
                    continue;
                }
                Matcher matcher = TICKET_REGEX.matcher(line);
                if (!matcher.matches())
                    continue;

                Ticket ticket = new Ticket();
                ticket.setTracker(matcher.group(1));
                ticket.setId(matcher.group(2));
                ticket.setSubject(matcher.group(3));

                if (urlFormat != null) {
                    ticket.setUrl(urlFormat.replace(":id", ticket.getId()));
                }
                ticketsPerVersion.add(ticket);
            }
            if (ticketsPerVersion != null) tickets.putAll(currVersion, ticketsPerVersion);
            return tickets;
        }
    }

    @Option(name = "--ticketUrlFormat")
    private String urlFormat;

    @Option(name = "--templateFile")
    private File template;

    @Option(name = "--outputFile")
    private File outputFile;

    @Option(name = "--changelogFile")
    private File changelogFile;

    public void run() throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(template.getParentFile());

        Template temp = cfg.getTemplate(template.getName());

        Multimap<String, Ticket> tickets = Ticket.readFromFile(changelogFile, urlFormat);

        Map<String, Object> root = ImmutableMap.<String, Object>of("versions", tickets.keySet(), "tickets", tickets);

        // ensure parent folder exists
        outputFile.getParentFile().mkdirs();

        try (FileWriter writer = new FileWriter(outputFile)) {
            temp.process(root, writer);
        }
    }

    public static void main(String[] args) throws IOException, CmdLineException, TemplateException {
        ChangelogGenerator generator = new ChangelogGenerator();
        CmdLineParser parser = new CmdLineParser(generator);
        parser.parseArgument(args);
        generator.run();
    }
}
