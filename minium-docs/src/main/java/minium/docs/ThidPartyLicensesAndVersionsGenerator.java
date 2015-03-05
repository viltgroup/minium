package minium.docs;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import com.google.common.base.Joiner;

public class ThidPartyLicensesAndVersionsGenerator {

    @Option(name = "--templateFile")
    private File template;
    @Option(name = "--outputFile")
    private File outputFile;
    @Option(name = "--licensesFile")
    private File licensesFile;

    public void run() throws IOException {
        List<Dependency> dependencies = License.readFromFile(licensesFile);

        StringWriter writer = new StringWriter();
        writer.append("Group ID | Artifact ID | Version | Name | License").append("\n");
        writer.append("-------- | ----------- | ------- | ---- | -------").append("\n");
        for (Dependency dependency : dependencies) {
            writer.append(Joiner.on(" | ").join(
                    dependency.groupId,
                    dependency.artifactId,
                    dependency.version,
                    String.format("[%s](%s)", dependency.name, dependency.url),
                    dependency.licenses)).append("\n");
        }

        String templateContent = FileUtils.readFileToString(template);
        String content = templateContent.replace("{{third-party-licenses}}", writer.toString());

        FileUtils.write(outputFile, content);
    }

    public static void main(String[] args) throws IOException, CmdLineException {
        ThidPartyLicensesAndVersionsGenerator generator = new ThidPartyLicensesAndVersionsGenerator();
        CmdLineParser parser = new CmdLineParser(generator);
        parser.parseArgument(args);
        generator.run();
    }
}
