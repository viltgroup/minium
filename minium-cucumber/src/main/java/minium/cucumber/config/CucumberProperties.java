package minium.cucumber.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import minium.cucumber.rest.RemoteBackend;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;

import cucumber.api.SnippetType;

public class CucumberProperties {

    public static class OptionsProperties {

        private static final String CUCUMBER_RUNTIME_MINIUM = "classpath:cucumber/runtime/minium";

        private boolean dryRun;
        private boolean strict = true;
        private List<String> features = Lists.newArrayList(Arrays.asList("src/test/resources/features"));
        private List<String> glue = Lists.newArrayList(Arrays.asList(CUCUMBER_RUNTIME_MINIUM, "src/test/resources/steps"));
        private List<String> tags = Lists.newArrayList();
        private List<String> format = Lists.newArrayList();
        private List<String> plugin = Lists.newArrayList();
        private boolean monochrome;
        private List<String> name = Lists.newArrayList();
        private SnippetType snippets = SnippetType.UNDERSCORE;

        public boolean isDryRun() {
            return dryRun;
        }

        public void setDryRun(boolean dryRun) {
            this.dryRun = dryRun;
        }

        public boolean isStrict() {
            return strict;
        }

        public void setStrict(boolean strict) {
            this.strict = strict;
        }

        public List<String> getFeatures() {
            return features;
        }

        public void setFeatures(List<String> features) {
            this.features = features;
        }

        public List<String> getGlue() {
            return glue;
        }

        public void setGlue(List<String> glue) {
            this.glue = glue;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public List<String> getFormat() {
            return format;
        }

        public void setFormat(List<String> format) {
            this.format = format;
        }

        public List<String> getPlugin() {
            return plugin;
        }

        public void setPlugin(List<String> plugin) {
            this.plugin = plugin;
        }

        public boolean isMonochrome() {
            return monochrome;
        }

        public void setMonochrome(boolean monochrome) {
            this.monochrome = monochrome;
        }

        public List<String> getName() {
            return name;
        }

        public void setName(List<String> name) {
            this.name = name;
        }

        public SnippetType getSnippets() {
            return snippets;
        }

        public void setSnippets(SnippetType snippets) {
            this.snippets = snippets;
        }

        public List<String> toArgs() {
            List<String> args = Lists.newArrayList();
            if (dryRun) args.add("--dry-run");
            if (strict) args.add("--strict");
            if (monochrome) args.add("--monochrome");

            // always ensure this one is included
            for (String gl : ImmutableSet.<String>builder().add(CUCUMBER_RUNTIME_MINIUM).addAll(glue).build()) {
                args.add("--glue");
                args.add(gl);
            }
            if (!plugin.isEmpty()) {
                for (String pl : ImmutableSet.copyOf(plugin)) {
                    args.add("--plugin");
                    args.add(pl);
                }
            } else {
                args.add("--plugin");
                args.add("null");
            }
            for (String t : ImmutableSet.copyOf(tags)) {
                args.add("--tags");
                args.add(t);
            }
            for (String nm : ImmutableSet.copyOf(name)) {
                args.add("--name");
                args.add(nm);
            }
            args.add("--snippets");
            args.add(snippets.toString());
            args.addAll(ImmutableSet.copyOf(features));

            return args;
        }
    }

    public static class CredentialsProperties {

        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof CredentialsProperties) {
                CredentialsProperties other = (CredentialsProperties) obj;
                return Objects.equal(this.username, other.username) &&
                        Objects.equal(this.password, other.password);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(username, password);
        }
    }

    public static class RemoteBackendProperties {

        private CredentialsProperties credentials;
        private String url;

        public CredentialsProperties getCredentials() {
            return credentials;
        }

        public void setCredentials(CredentialsProperties credentials) {
            this.credentials = credentials;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public RemoteBackend createRemoteBackend() {
            CloseableHttpClient httpClient;
            if (credentials != null) {
                CredentialsProvider credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(credentials.getUsername(), credentials.getPassword()));
                httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credsProvider).build();
            } else {
                httpClient = HttpClientBuilder.create().build();
            }

            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
            RestTemplate restTemplate = new RestTemplate(factory);
            return new RemoteBackend(url, restTemplate);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof RemoteBackendProperties) {
                RemoteBackendProperties other = (RemoteBackendProperties) obj;
                return Objects.equal(this.url, other.url) &&
                        Objects.equal(this.credentials, other.credentials);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(url, credentials);
        }
    }

    public static class SnippetProperties {
        private String name;
        private String content;
        private String trigger;
        private List<String> table = Lists.newArrayList();
        private List<String> tableColumns = Lists.newArrayList();
        private List<String> simpleTable = Lists.newArrayList();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            if (content == null) {
                List<String> parts = Splitter.on("...").splitToList(name);
                StringBuilder buf = new StringBuilder();
                int curr = 1;
                for (int i = 0; i < parts.size(); i++) {
                    buf.append(parts.get(i));
                    if (i != parts.size() - 1) {
                        buf.append("${" + (curr++) + ":value}");
                    }
                }

                if (!table.isEmpty()) {
                    buf.append("\n");
                    buf.append("  | " + Joiner.on(" | ").join(table) + " |\n");
                    buf.append("  |");
                    for (String col : table) {
                        buf.append(" ${" + (curr++) + ":" + col + "} ");
                        buf.append("|");
                    }
                }

                if (!tableColumns.isEmpty()) {

                	for (String col : tableColumns) {
                		buf.append("\n");
                		buf.append("  | " + col + "  | " );
                        buf.append(" ${" + (curr++) + ":" + col + "} ");
                        buf.append("|");
                    }

                }


                if (!simpleTable.isEmpty()) {

                	for (String col : simpleTable) {
                		buf.append("\n");
                		buf.append("|");
                        buf.append(" ${" + (curr++) + ":" + col + "} ");
                        buf.append("|");
                    }

                }
                return buf.toString();
            }

            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTrigger() {
            if (trigger == null) {
                return name.replaceAll("\\s+.*", "");
            }
            return trigger;
        }

        public void setTrigger(String trigger) {
            this.trigger = trigger;
        }

        public List<String> getTable() {
            return table;
        }

        public void setTable(List<String> table) {
            this.table = table;
        }

		public List<String> getTableColumns() {
			return tableColumns;
		}

		public void setTableColumns(List<String> tableColumns) {
			this.tableColumns = tableColumns;
		}

		public List<String> getSimpleTable() {
			return simpleTable;
		}

		public void setSimpleTable(List<String> simpleTable) {
			this.simpleTable = simpleTable;
		}
    }

    private OptionsProperties options = new OptionsProperties();
    private List<RemoteBackendProperties> remoteBackends = Lists.newArrayList();
    private List<SnippetProperties> snippets = Lists.newArrayList();

    public OptionsProperties getOptions() {
        return options;
    }

    public void setOptions(OptionsProperties options) {
        this.options = options;
    }

    public List<RemoteBackendProperties> getRemoteBackends() {
        return remoteBackends;
    }

    public void setRemoteBackends(Collection<RemoteBackendProperties> remoteBackends) {
        this.remoteBackends = Lists.newArrayList(remoteBackends);
    }

    public List<SnippetProperties> getSnippets() {
        return snippets;
    }

    public void setSnippets(List<SnippetProperties> snippets) {
        this.snippets = snippets;
    }
}
