package minium.automator.config;

import java.io.File;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

public class AutomatorProperties {

    @Option(name = "-f", usage = "script file", metaVar = "FILE")
    private File file;

    @Option(name = "-b", metaVar = "BROWSER")
    private String browser = "chrome";

    @Option(name = "-m", metaVar = "MODULE_PATHS")
    private String[] modulePaths;

    @Argument(metaVar = "SCRIPT")
    private String script;

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String[] getModulePaths() {
        return modulePaths;
    }

    public void setModulePaths(String[] modulePaths) {
        this.modulePaths = modulePaths;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
