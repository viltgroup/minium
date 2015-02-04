package minium.cucumber;

import minium.cucumber.config.ConfigConfiguration;
import minium.cucumber.config.CucumberConfiguration;
import minium.script.js.JsVariablePostProcessor;
import minium.web.config.WebDriverConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ ConfigConfiguration.class, CucumberConfiguration.class, WebDriverConfiguration.class })
public class MiniumConfiguration {

    @Bean
    public JsVariablePostProcessor jsVariablePostProcessor() {
        return new JsVariablePostProcessor();
    }

}
