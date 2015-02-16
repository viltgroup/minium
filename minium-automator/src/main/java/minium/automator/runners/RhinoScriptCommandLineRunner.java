package minium.automator.runners;

import minium.automator.config.AutomatorProperties;
import minium.script.js.JsVariablePostProcessor;
import minium.script.rhinojs.RhinoEngine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class RhinoScriptCommandLineRunner implements CommandLineRunner {

    @Autowired
    private RhinoEngine rhinoEngine;
    @Autowired
    private JsVariablePostProcessor jsVariablePostProcessor;
    @Autowired
    private AutomatorProperties properties;
    @Autowired
    private ApplicationContext context;

    private int exitCode;

    @Override
    public void run(String... args) throws Exception {
        try {
            jsVariablePostProcessor.populateEngine(rhinoEngine);
            if (properties.getScript() != null) {
                rhinoEngine.eval(properties.getScript(), 1);
            }
            if (properties.getFile() != null) {
                rhinoEngine.runScript(properties.getFile());
            }
            exitCode = 0;
        } catch (Exception e) {
            System.err.println(e);
            exitCode = 1;
        }

        SpringApplication.exit(context, new ExitCodeGenerator() {
            @Override
            public int getExitCode() {
                return exitCode;
            }
        });
    }

}
