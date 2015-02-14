package minium.automator.config;

import minium.automator.runners.RhinoScriptCommandLineRunner;
import minium.script.rhinojs.RhinoProperties;
import minium.script.rhinojs.RhinoProperties.RequireProperties;
import minium.web.config.WebDriverProperties;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionHandlerFilter;
import org.openqa.selenium.remote.CapabilityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@Configuration
public class AutomatorConfiguration {

    private static AutomatorProperties automatorProperties = new AutomatorProperties();

    @Bean
    public AutomatorProperties automatorProperties() {
        return automatorProperties;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Autowired
    @Bean
    public RhinoScriptCommandLineRunner rhinoScriptCommandLineRunner() {
        return new RhinoScriptCommandLineRunner();
    }

    @Bean
    @Primary
    public RhinoProperties rhinoProperties(AutomatorProperties automator) {
        RhinoProperties rhinoProperties = new RhinoProperties();
        RequireProperties require = new RequireProperties();
        if (automator.getModulePaths() != null) require.getModulePaths().addAll(ImmutableList.copyOf(automator.getModulePaths()));
        rhinoProperties.setRequire(require);
        return rhinoProperties;
    }

    @Bean
    @Primary
    public WebDriverProperties webDriverProperties(AutomatorProperties automator) {
        WebDriverProperties webDriverProperties = new WebDriverProperties();
        ImmutableMap<String, Object> desiredCapabilities = ImmutableMap.<String, Object>of(CapabilityType.BROWSER_NAME, automator.getBrowser());
        webDriverProperties.setDesiredCapabilities(desiredCapabilities);
        return webDriverProperties;
    }

    public static void readAutomationProperties(String[] args) {
        // quick way to do this
        CmdLineParser parser = new CmdLineParser(automatorProperties);

        // parse the arguments.
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("minium-automator [options...] arguments...");
            parser.printUsage(System.err);
            System.err.println();
            System.err.println("  Example: minium-automator" + parser.printExample(OptionHandlerFilter.ALL));
            System.exit(1);
        }
    }
}
