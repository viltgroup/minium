package minium.automator;

import minium.automator.config.AutomatorConfiguration;
import minium.script.rhinojs.RhinoConfiguration;
import minium.web.config.WebElementsConfiguration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({ WebElementsConfiguration.class, RhinoConfiguration.class, AutomatorConfiguration.class })
public class Application {

    public static void main(String[] args) {
        AutomatorConfiguration.readAutomationProperties(args);

        new SpringApplicationBuilder(Application.class)
            .showBanner(false)
            .build()
            .run(args);
    }

}
