package minium.script.rhinojs;

import java.io.IOException;

import minium.script.js.JsVariablePostProcessor;
import minium.script.rhinojs.RhinoProperties.RequireProperties;
import minium.web.WebModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RhinoProperties.class)
public class RhinoConfiguration {

    @Bean
    public JsVariablePostProcessor jsVariablePostProcessor() {
        return new JsVariablePostProcessor();
    }

    @Bean
    public WebModule rhinoWebModule() {
        return RhinoWebModules.rhinoModule();
    }

    @Bean
    @ConfigurationProperties(prefix = "minium.rhino")
    public RhinoProperties rhinoProperties() {
        RequireProperties require = new RequireProperties();
        RhinoProperties rhinoProperties = new RhinoProperties();
        rhinoProperties.setRequire(require);
        return rhinoProperties;
    }

    @Autowired(required = false)
    @Bean
    public RhinoEngine rhinoEngine(RhinoProperties rhinoProperties, JsVariablePostProcessor jsVariablePostProcessor) throws IOException {
        RhinoEngine engine = new RhinoEngine(rhinoProperties);
        jsVariablePostProcessor.populateEngine(engine);
        return engine;
    }
}
