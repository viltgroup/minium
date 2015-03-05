package minium.docs;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Lists;

public class Dependency {

    private static final Pattern DEPENDENCY_REGEX = Pattern.compile("^\\s*(.*):(.*):(.*):(.*):(.*)$");

    String groupId;
    String artifactId;
    String version;
    String type;
    String scope;
    String name;
    String licenses;
    String url;

    public Dependency() {
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Dependency))
            return false;
        Dependency other = (Dependency) obj;
        return Objects.equals(groupId, other.groupId) &&
                Objects.equals(artifactId, other.artifactId) &&
                Objects.equals(version, other.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, artifactId, version);
    }

    public static List<Dependency> readFromFile(File file) throws IOException {
        List<Dependency> dependencies = Lists.newArrayList();
        List<String> lines = FileUtils.readLines(file);
        for (String line : lines) {
            Matcher matcher = DEPENDENCY_REGEX.matcher(line);
            if (!matcher.matches())
                continue;
            Dependency dependency = new Dependency();
            dependency.groupId = matcher.group(1);
            dependency.artifactId = matcher.group(2);
            dependency.type = matcher.group(3);
            dependency.version = matcher.group(4);
            dependency.scope = matcher.group(5);
            dependencies.add(dependency);
        }
        return dependencies;
    }
}
