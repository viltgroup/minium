package minium.cucumber;

import minium.cucumber.config.ConfigConfiguration;
import minium.cucumber.config.CucumberConfiguration;
import minium.script.js.JsVariable;
import minium.script.rhinojs.JsFunctionWebElements;
import minium.script.rhinojs.RhinoConfiguration;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.WebFinder;
import minium.web.config.WebElementsConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ ConfigConfiguration.class, CucumberConfiguration.class, WebElementsConfiguration.class, RhinoConfiguration.class })
public class MiniumConfiguration {

    @Autowired
    @Bean
    @JsVariable("by")
    public WebFinder<DefaultWebElements> by(DefaultWebElements root) {
        return new WebFinder<DefaultWebElements>(root, DefaultWebElements.class, JsFunctionWebElements.class);
    }

}
