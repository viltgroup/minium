package minium.cucumber;

import minium.cucumber.config.ConfigConfiguration;
import minium.cucumber.config.CucumberConfiguration;
import minium.script.rhinojs.RhinoConfiguration;
import minium.web.config.WebElementsConfiguration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ ConfigConfiguration.class, CucumberConfiguration.class, WebElementsConfiguration.class, RhinoConfiguration.class })
public class MiniumConfiguration {
}
