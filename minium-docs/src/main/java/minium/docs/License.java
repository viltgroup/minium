package minium.docs;

import static java.lang.String.format;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Lists;

public class License {

    private static final class DependencyComparator implements Comparator<Dependency> {
        @Override
        public int compare(Dependency o1, Dependency o2) {
            return format("%s:%s", o1.groupId, o1.artifactId).compareTo(format("%s:%s", o2.groupId, o2.artifactId));
        }
    }

    private static final Pattern LICENSE_REGEX = Pattern.compile("^\\s*\\((.*)\\) (.*) \\((.*):(.*):(.*) - (.*)\\)$");

    public static List<Dependency> readFromFile(File file) throws IOException {
        List<Dependency> dependencies = Lists.newArrayList();
        List<String> lines = FileUtils.readLines(file);
        for (String line : lines) {
            Matcher matcher = LICENSE_REGEX.matcher(line);
            if (!matcher.matches())
                continue;
            Dependency dependency = new Dependency();
            dependency.licenses = matcher.group(1).replaceAll("\\) \\(", " / ");
            dependency.name = matcher.group(2);
            dependency.groupId = matcher.group(3);
            dependency.artifactId = matcher.group(4);
            dependency.version = matcher.group(5);
            dependency.url = matcher.group(6);
            dependencies.add(dependency);
        }
        Collections.sort(dependencies, new DependencyComparator());
        return dependencies;
    }
}
