package minium.script.rhinojs;

import java.io.IOException;
import java.util.Set;

import minium.script.js.JsVariable;
import minium.script.js.JsVariablePostProcessor;
import minium.script.rhinojs.RhinoProperties.RequireProperties;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.WebLocator;
import minium.web.config.WebDriverFactory;
import minium.web.internal.WebElementsFactory;
import minium.web.internal.WebModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    @Autowired
    @Bean
    @JsVariable(value = "browsers")
    public RhinoWebDriverFactory rhinoWebDriverFactory(ObjectMapper mapper, RhinoEngine engine, WebDriverFactory webDriverFactory) {
        return new RhinoWebDriverFactory(mapper, engine, webDriverFactory);
    }

    @Autowired
    @Bean
    @JsVariable(
            value = "__by",
            expression = "$ = require('minium'); if (typeof $.browser !== 'undefined') browser = $.browser;",
            deleteAfterExpression = true)
    public WebLocator<DefaultWebElements> by(WebElementsFactory<DefaultWebElements> elementsFactory) {
        Set<Class<?>> providedInterfaces = elementsFactory.getProvidedInterfaces();
        Class<?>[] otherIntfs = providedInterfaces.toArray(new Class<?>[providedInterfaces.size()]);
        return new WebLocator<DefaultWebElements>(elementsFactory.createRoot(), DefaultWebElements.class, otherIntfs);
    }
}
